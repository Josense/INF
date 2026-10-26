package com.josense.inf.topic.thirdpartylibrary.enrichedmarkdown

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.dp

val EnrichedMarkdown: ImageVector by lazy {
    ImageVector.Builder(
        name = "EnrichedMarkdown",
        defaultWidth = 36.dp,
        defaultHeight = 36.dp,
        viewportWidth = 48f,
        viewportHeight = 48f,
    ).apply {
        addGroup(translationX = 2.2222f, translationY = 4f)
        addPath(
            pathData = PathParser().parsePathString(
                "M0 8C0 5.05448 2.38781 2.66667 5.33333 2.66667H29.3333C32.2789 2.66667 34.6667 5.05448 34.6667 8V32C34.6667 34.9455 32.2789 37.3333 29.3333 37.3333H5.33333C2.38781 37.3333 0 34.9455 0 32V8Z"
            ).toNodes(),
            fill = SolidColor(Color(0xFF85D6A5)),
        )
        addPath(
            pathData = PathParser().parsePathString(
                "M29.3333 0C32.1069 0 34.5501 1.41182 35.9852 3.55556H36.0148C37.4499 1.41182 39.8931 0 42.6667 0H43.5556V2.66667H42.6667C39.7211 2.66667 37.3333 5.05448 37.3333 8V18.6667H40V21.3333H37.3333V32C37.3333 34.9455 39.7211 37.3333 42.6667 37.3333H43.5556V40H42.6667C39.8931 40 37.4499 38.5882 36.0148 36.4444H35.9852C34.5501 38.5882 32.1069 40 29.3333 40H28.4444V37.3333H29.3333C32.2789 37.3333 34.6667 34.9455 34.6667 32V21.3333H32V18.6667H34.6667V8C34.6667 5.05448 32.2789 2.66667 29.3333 2.66667H28.4444V0H29.3333Z"
            ).toNodes(),
            fill = SolidColor(Color(0xFF001A72)),
        )
        addPath(
            pathData = PathParser().parsePathString(
                "M27.3333 14.6667H30.8889L26 20.4444L21.1111 14.6667H24.6528V8H27.3333V14.6667Z"
            ).toNodes(),
            fill = SolidColor(Color(0xFF001A72)),
        )
        addPath(
            pathData = PathParser().parsePathString(
                "M8 8.00434L12 12.8889L16 8.00434V8H18.6667V20.4444H16V12.2899L12 17.1111L8 12.2899V20.4444H5.33333V8H8V8.00434Z"
            ).toNodes(),
            fill = SolidColor(Color(0xFF001A72)),
        )
        clearGroup()
    }.build()
}