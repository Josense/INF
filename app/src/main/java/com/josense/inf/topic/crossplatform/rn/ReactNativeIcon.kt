package com.josense.inf.topic.crossplatform.rn

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val ReactNativeIcon: ImageVector by lazy {
    val reactBlue = SolidColor(Color(0xFF61DAFB))

    ImageVector.Builder(
        name = "ReactNative",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        // 将 SVG 的负坐标视口平移到 Android 的正坐标视口。
        group(
            translationX = 12f,
            translationY = 12f
        ) {
            // 中心圆
            path(fill = reactBlue) {
                moveTo(-2.05f, 0f)
                arcTo(2.05f, 2.05f, 0f, false, true, 2.05f, 0f)
                arcTo(2.05f, 2.05f, 0f, false, true, -2.05f, 0f)
                close()
            }

            // 三条轨道，分别旋转 0°、60°、120°。
            listOf(0f, 60f, 120f).forEach { angle ->
                group(rotate = angle) {
                    path(
                        fill = null,
                        stroke = reactBlue,
                        strokeLineWidth = 1f
                    ) {
                        moveTo(-11f, 0f)
                        arcTo(11f, 4.2f, 0f, false, true, 11f, 0f)
                        arcTo(11f, 4.2f, 0f, false, true, -11f, 0f)
                        close()
                    }
                }
            }
        }
    }.build()
}