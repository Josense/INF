package com.josense.inf.ui.topic.uirender.custom_compose

import android.util.Log
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout


@Composable
fun AnimatedCircle(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition()
    val color by transition.animateColor(
        initialValue = Color.Red,
        targetValue = Color.Green,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        )
    )
    // View: Measurement -> Layout -> Drawing
    // Compose: Composition -> (Measurement + Layout) -> Drawing
    Box(
        modifier = modifier
            .layout { measurable, constraints ->
                Log.d("AnimatedCircle", "constraints: $constraints")
                // 1. 测量：调用 measurable.measure 测量当前绑定该 Modifier 的组件
                //    你可以传入修改后的 constraints 来改变它的大小
                val targetWidth = (constraints.maxWidth / 2).coerceAtMost(1000)
                val targetHeight = (constraints.maxHeight / 2).coerceAtMost(1000)
                val placeable = measurable.measure(constraints.copy(
                    minWidth = targetWidth,
                    maxWidth = targetWidth,
                    minHeight = targetHeight,
                    maxHeight = targetHeight
                ))

                Log.d("AnimatedCircle", "width:${placeable.width}, height:${placeable.height}")
                // 2. 决定该组件最终在屏幕上占据的宽高空间
                val currentWidth = placeable.width
                val currentHeight = placeable.height

                // 3. 布局：调用 layout()，并在内部决定当前组件应该被摆放在哪个偏移位置
                layout(currentWidth, currentHeight) {
                    // 在这里指定 (x, y) 坐标，让该组件相对于其父布局原本给它的位置进行偏移
                    placeable.placeRelative(x = 0, y = 0)
                }
            }
            .drawBehind {
                drawOval(
                    color = color,
                    topLeft = Offset(x = 0f, y = 0f),
                    size = Size(width = size.width, height = size.height)
                )
            }
    )
}