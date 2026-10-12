package com.josense.inf.tools.activitydetect

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.josense.inf.R
import com.josense.inf.ui.components.BrandIcon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.milliseconds

class FloatingWindowService: LifecycleService(), ViewModelStoreOwner, SavedStateRegistryOwner {
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private var windowManager: WindowManager? = null
    private var floatingView: ComposeView? = null
    private val floatingViewText = mutableStateOf("")
    private val expanded = mutableStateOf(true)
    private lateinit var windowParams: WindowManager.LayoutParams

    private val localViewModelStore = ViewModelStore()
    override val viewModelStore: ViewModelStore
        get() = localViewModelStore

    private val savedStateRegistryController = SavedStateRegistryController.create(this)
    override val savedStateRegistry
        get() = savedStateRegistryController.savedStateRegistry

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    override fun onCreate() {
        savedStateRegistryController.performRestore(null)
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        floatingViewText.value = getString(R.string.detecting_activity)
        showFloatingWindow()
        serviceScope.launch {
            while (isActive) {
                val activity = withContext(Dispatchers.IO) {
                    getLatestResumedActivity(this@FloatingWindowService)
                }
                floatingViewText.value = activity?.let {
                    getString(R.string.detecting_result, activity.packageName, activity.className)
                } ?: getString(R.string.detecting_error)
                delay(500.milliseconds)
            }
        }
    }

    private fun showFloatingWindow() {
        val windowType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_PHONE
        }

        windowParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            windowType,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 24
            y = 160
        }

        floatingView = ComposeView(this).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
            setViewTreeLifecycleOwner(this@FloatingWindowService)
            setViewTreeViewModelStoreOwner(this@FloatingWindowService)
            setViewTreeSavedStateRegistryOwner(this@FloatingWindowService)

            setContent {
                val interactionSource = remember { MutableInteractionSource() }
                Surface(
                    color = Color(0x11000000),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()

                            // dragAmount 的单位是像素；LayoutParams.x/y 也是像素
                            windowParams.x += dragAmount.x.roundToInt()
                            windowParams.y += dragAmount.y.roundToInt()

                            windowManager?.updateViewLayout(this@apply, windowParams)
                        }
                    }.combinedClickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {
                            expanded.value = expanded.value.not()
                        },
                        onLongClick = {
                            val clipboard =
                                context.getSystemService(ClipboardManager::class.java)

                            clipboard.setPrimaryClip(
                                ClipData.newPlainText(
                                    getString(R.string.current_activity_info),
                                    floatingViewText.value
                                )
                            )
                        }
                    )
                ) {
                    if (expanded.value) {
                        Text(
                            text = floatingViewText.value,
                            modifier = Modifier
                                .padding(16.dp),
                            color = Color(0xDD202124)
                        )
                    } else {
                        BrandIcon()
                    }
                }
            }
        }

        windowManager?.addView(floatingView ?: return, windowParams)
    }

    override fun onDestroy() {
        if (floatingView != null && floatingView?.isAttachedToWindow == true) {
            windowManager?.removeView(floatingView)
        }
        floatingView = null
        localViewModelStore.clear()
        super.onDestroy()
    }
}