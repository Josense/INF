package com.josense.inf.topic.compose.touchevent

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.scrollableArea
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastSumBy
import io.github.serpro69.kfaker.Faker
import kotlin.math.ceil
import kotlin.math.roundToInt

@Composable
fun WheelLikeList(
    childHeight: Dp = 32.dp,
    modifier: Modifier,
    content: @Composable () -> Unit,
) {

    var scrollDelta by remember { mutableStateOf(0F) }
    var scrollTotalDelta by remember { mutableStateOf(0F) }
    val scrollState = rememberScrollableState { float ->
        scrollDelta = float
        Log.d("WheelLikeList", "onscroll, delta:$float")
        float
    }
    val childHeightPx = with(LocalDensity.current) { childHeight.toPx() }
    Layout(
        content,
        modifier.scrollableArea(scrollState, Orientation.Vertical),
    ) { measurables, constraints ->
        val totalHeight = measurables.size * childHeightPx
        val currentDelta = (scrollTotalDelta + scrollDelta).coerceIn(-totalHeight / 2, totalHeight / 2)
        scrollTotalDelta = currentDelta
        val validScrollDelta = currentDelta //.coerceIn(-totalHeight / 2, totalHeight / 2)
        var offset = ((constraints.maxHeight - totalHeight) / 2 - validScrollDelta).roundToInt()
        val placeable = measurables.fastMap { it.measure(constraints.copy(
            minHeight = childHeightPx.roundToInt(),
            maxHeight = childHeightPx.roundToInt(),
            minWidth = 0,
            maxWidth = Int.MAX_VALUE,
        )) }
        layout(constraints.maxWidth, constraints.maxHeight) {
            placeable.forEach { it ->
                it.place(0, offset)
                offset += it.height
            }
        }
    }
}

@Composable
fun WheelLikeListDemo() {
    val faker = Faker()
    WheelLikeList(
        modifier = Modifier.padding(16.dp)
            .border(1.dp, Color.Red)
            .fillMaxSize()
            .height(180.dp)
    ) {
        for (i in 0 until 19) {
            Text(
                text = "${i + 1}: ${faker.book.title()}",
//                modifier = Modifier.height(32.dp),
                maxLines = 1,
            )
        }
    }
}