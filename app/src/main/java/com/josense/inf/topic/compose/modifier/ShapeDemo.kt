package com.josense.inf.topic.compose.modifier

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

data class ShapeData(
    val shapeName: String,
    val shape: Shape,
)

@Composable
fun ShapeDemo() {
    val shapes = listOf(
        ShapeData("Circle", CircleShape),
        ShapeData("Rectangle", RectangleShape),
        ShapeData(
            "RoundedCornerShape(topStart = 16.dp, bottomEnd = 16.dp)",
            RoundedCornerShape(topStart = 16.dp, bottomEnd = 16.dp)
        ),
        ShapeData(
            "CutCornerShape(topEnd = 32.dp, bottomStart = 32.dp)",
            CutCornerShape(topEnd = 32.dp, bottomStart = 32.dp)
        ),
        ShapeData(
            "AbsoluteRoundedCornerShape(topLeft = 16.dp, bottomRight = 16.dp)",
            AbsoluteRoundedCornerShape(topLeft = 16.dp, bottomRight = 16.dp)
        ), ShapeData(
            "BubbleShape(bubbleCorner = 16.dp)",
            BubbleShape(bubbleCorner = 16.dp)
        )
    )

    LazyColumn(
        modifier = Modifier.padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items(shapes.count()) { index ->
            ShapeComponent(
                url = "https://picsum.photos/seed/${index}/600",
                shapeName = shapes[index].shapeName,
                shape = shapes[index].shape
            )
        }

    }
}

@Composable
fun ShapeComponent(
    url: String,
    shapeName: String,
    shape: Shape
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .border(1.dp, Color.Gray)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = shapeName,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1F)
        )
        AsyncImage(
            model = url,
            contentDescription = null,
            modifier = Modifier.size(100.dp)
                .border(1.dp, Color.Black)
                .clip(shape)
                .border(2.dp, Color.Red, shape = shape)

        )
    }
}

class BubbleShape(private val bubbleCorner: Dp): Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val cornerPx = with(density) { bubbleCorner.toPx() }
        val path = Path().apply {
            moveTo(cornerPx, 0F)
            lineTo(size.width - cornerPx * 2, 0F)
            quadraticTo(size.width - cornerPx, 0F, size.width - cornerPx, cornerPx)


            lineTo(size.width - cornerPx, size.height / 2 - cornerPx)
            lineTo(size.width, size.height / 2)
            lineTo(size.width - cornerPx, size.height / 2 + cornerPx)
            lineTo(size.width - cornerPx, size.height - cornerPx)


            quadraticTo(size.width - cornerPx, size.height, size.width - cornerPx * 2, size.height)
            lineTo(cornerPx, size.height)
            quadraticTo(0F, size.height, 0F, size.height - cornerPx)
            lineTo(0F, cornerPx)
            quadraticTo(0F, 0F, cornerPx, 0F)
            close()
        }
        return Outline.Generic(path)
    }
}


