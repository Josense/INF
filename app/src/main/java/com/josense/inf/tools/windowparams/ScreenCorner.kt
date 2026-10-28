package com.josense.inf.tools.windowparams

import android.os.Build
import android.view.RoundedCorner
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat


data class ScreenCorners(
    val topLeft: Dp = 0.dp,
    val topRight: Dp = 0.dp,
    val bottomLeft: Dp = 0.dp,
    val bottomRight: Dp = 0.dp
)


@Composable
fun rememberScreenCornersSafe(): ScreenCorners {
    val density = LocalDensity.current
    val view = LocalView.current

    // 💡 核心安全点：直接引用 Compose 已经帮我们捕获好的内置 systemBars
    // 这样不需要再去覆盖 View.setOnApplyWindowInsetsListener
    val composeSystemBars = WindowInsets.systemBars

    return remember(composeSystemBars, density, view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // 通过根 View 的 rootWindowInsets 拿到当前的系统原生实例
            val rootInsets = view.rootWindowInsets
            if (rootInsets != null) {
                val tl = rootInsets.getRoundedCorner(RoundedCorner.POSITION_TOP_LEFT)
                val tr = rootInsets.getRoundedCorner(RoundedCorner.POSITION_TOP_RIGHT)
                val bl = rootInsets.getRoundedCorner(RoundedCorner.POSITION_BOTTOM_LEFT)
                val br = rootInsets.getRoundedCorner(RoundedCorner.POSITION_BOTTOM_RIGHT)

                with(density) {
                    ScreenCorners(
                        topLeft = (tl?.radius ?: 0).toDp(),
                        topRight = (tr?.radius ?: 0).toDp(),
                        bottomLeft = (bl?.radius ?: 0).toDp(),
                        bottomRight = (br?.radius ?: 0).toDp()
                    )
                }
            } else {
                ScreenCorners()
            }
        } else {
            ScreenCorners()
        }
    }
}