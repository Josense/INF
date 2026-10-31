package com.josense.inf.topic.concurrency.coroutine

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val KotlinIcon: ImageVector by lazy {
    ImageVector.Builder(
        name = "Kotlin",
        defaultWidth = 36.dp,
        defaultHeight = 36.dp,
        viewportWidth = 64f,
        viewportHeight = 64f,
    ).apply {
        path(
            fill = Brush.radialGradient(
                0.00343514f to Color(0xFFEF4857),
                0.4689f to Color(0xFFD211EC),
                1f to Color(0xFF7F52FF),
                center = Offset(61.8732f, 2.63097f),
                radius = 73.3111f,
            ),
        ) {
            moveTo(64f, 64f)
            horizontalLineTo(0f)
            verticalLineTo(0f)
            horizontalLineTo(64f)
            lineTo(31.3373f, 31.5369f)
            lineTo(64f, 64f)
            close()
        }
    }.build()
}