package com.josense.inf.tools.activitydetect

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.josense.inf.R
import com.josense.inf.ui.components.LargeChipButtonComponent
import com.josense.inf.ui.components.LifecycleEventObserver
import com.josense.inf.ui.components.StatusEnableCheckButton

@Composable
fun ActivityDetectPage() {
    val context = LocalContext.current
    var floatingWindowEnabled by remember {
        mutableStateOf(Settings.canDrawOverlays(context))
    }
    var usageAccessEnabled by remember {
        mutableStateOf(hasUsageAccess(context))
    }
    var showFloatingWindow by remember {
        mutableStateOf(false)
    }

    LifecycleEventObserver { event ->
        if (event == Lifecycle.Event.ON_RESUME) {
            floatingWindowEnabled = Settings.canDrawOverlays(context)
            usageAccessEnabled = hasUsageAccess(context)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StatusEnableCheckButton(
            checked = floatingWindowEnabled,
            checkedText = stringResource(R.string.floating_window_permission_granted),
            uncheckedText = stringResource(R.string.floating_window_permission_not_granted),
        ) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                "package:${context.packageName}".toUri()
            )
            context.startActivity(intent)
        }

        StatusEnableCheckButton(
            checked = usageAccessEnabled,
            checkedText = stringResource(R.string.usage_access_permission_granted),
            uncheckedText = stringResource(R.string.usage_access_permission_not_granted),
        ) {
            context.startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }


        LargeChipButtonComponent(
            name = if (showFloatingWindow) {
                stringResource(R.string.close_floating_window)
            } else {
                stringResource(R.string.enable_floating_window)
            },
            checked = showFloatingWindow,
            enabled = floatingWindowEnabled && usageAccessEnabled,
        ) { check ->
            showFloatingWindow = !showFloatingWindow
            if (showFloatingWindow) {
                context.startService(Intent(context, FloatingWindowService::class.java))
            } else {
                context.stopService(Intent(context, FloatingWindowService::class.java))
            }
        }
    }
}


