package com.josense.inf.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

/** Static vector redrawn from the supplied Compose logo screenshot. */
val ComposeLogoIcon: ImageVector by lazy {
    ImageVector.Builder(
        name = "ComposeLogo",
        defaultWidth = 36.dp,
        defaultHeight = 36.dp,
        viewportWidth = 256f,
        viewportHeight = 256f,
    ).apply {
        // Rounded outer silhouette and left face.
        path(fill = SolidColor(Color(0xFF002D3E))) {
            moveTo(111f, 11f)
            curveTo(122f, 4f, 134f, 4f, 145f, 11f)
            lineTo(225f, 57f)
            curveTo(234f, 62f, 238f, 69f, 238f, 80f)
            lineTo(238f, 178f)
            curveTo(238f, 188f, 235f, 193f, 226f, 198f)
            lineTo(145f, 245f)
            curveTo(134f, 252f, 124f, 252f, 113f, 246f)
            lineTo(30f, 198f)
            curveTo(21f, 193f, 18f, 189f, 18f, 178f)
            lineTo(18f, 80f)
            curveTo(18f, 70f, 21f, 63f, 30f, 58f)
            close()
        }

        // Right face.
        path(fill = SolidColor(Color(0xFF367CF7))) {
            moveTo(128f, 129f)
            lineTo(234f, 66f)
            curveTo(237f, 70f, 238f, 74f, 238f, 80f)
            lineTo(238f, 178f)
            curveTo(238f, 188f, 235f, 193f, 226f, 198f)
            lineTo(145f, 245f)
            curveTo(139f, 249f, 134f, 251f, 128f, 250f)
            close()
        }

        // Green upper face.
        path(fill = SolidColor(Color(0xFF29C486))) {
            moveTo(21f, 67f)
            curveTo(23f, 63f, 26f, 60f, 30f, 58f)
            lineTo(111f, 11f)
            curveTo(122f, 4f, 134f, 4f, 145f, 11f)
            lineTo(225f, 57f)
            curveTo(229f, 60f, 232f, 62f, 234f, 66f)
            lineTo(128f, 129f)
            close()
        }

        // Recessed hexagonal rim; the face colors remain visible beneath it.
        path(fill = SolidColor(Color.Black), fillAlpha = 0.18f) {
            moveTo(118f, 56f)
            curveTo(124f, 52f, 130f, 52f, 137f, 56f)
            lineTo(187f, 85f)
            curveTo(193f, 89f, 195f, 93f, 195f, 100f)
            lineTo(195f, 155f)
            curveTo(195f, 162f, 193f, 166f, 187f, 170f)
            lineTo(137f, 199f)
            curveTo(131f, 203f, 126f, 203f, 119f, 199f)
            lineTo(69f, 170f)
            curveTo(63f, 166f, 61f, 163f, 61f, 156f)
            lineTo(61f, 101f)
            curveTo(61f, 94f, 63f, 90f, 69f, 86f)
            close()
        }

        // Darker inner left wall.
        path(fill = SolidColor(Color(0xFF001C27)), fillAlpha = 0.7f) {
            moveTo(61f, 91f)
            lineTo(128f, 129f)
            lineTo(128f, 202f)
            curveTo(125f, 202f, 122f, 201f, 119f, 199f)
            lineTo(69f, 170f)
            curveTo(63f, 166f, 61f, 163f, 61f, 156f)
            close()
        }

        // Light-blue central hexagon, with rounded corners.
        path(fill = SolidColor(Color(0xFFCDEEFF))) {
            moveTo(122f, 80f)
            curveTo(126f, 77f, 130f, 77f, 134f, 80f)
            lineTo(168f, 99f)
            curveTo(172f, 101f, 173f, 104f, 173f, 108f)
            lineTo(173f, 148f)
            curveTo(173f, 153f, 172f, 155f, 168f, 157f)
            lineTo(134f, 177f)
            curveTo(130f, 180f, 127f, 180f, 122f, 177f)
            lineTo(88f, 157f)
            curveTo(84f, 155f, 83f, 153f, 83f, 148f)
            lineTo(83f, 108f)
            curveTo(83f, 104f, 84f, 101f, 88f, 99f)
            close()
        }
    }.build()
}
