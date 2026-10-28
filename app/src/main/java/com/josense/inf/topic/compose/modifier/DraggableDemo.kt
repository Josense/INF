package com.josense.inf.topic.compose.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.AnchoredDraggableDefaults
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.draggable2D
import androidx.compose.foundation.gestures.rememberDraggable2DState
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollSource.Companion.SideEffect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.ViewConfiguration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import com.facebook.react.uimanager.PixelUtil.dpToPx
import kotlin.math.roundToInt

private enum class DragPosition {
    Start, End
}

@Composable
fun DraggableDemo() {
    var offsetOne by remember { mutableStateOf(Offset.Zero) }
    var offsetTwo by remember { mutableStateOf(Offset.Zero) }

    val density = LocalDensity.current
    val containerDpSize = LocalWindowInfo.current.containerDpSize
    val travelDistancePx = with(density) { (containerDpSize.width - 100.dp).toPx() }

    val state = remember {
        AnchoredDraggableState(
            initialValue = DragPosition.Start,
            anchors = DraggableAnchors {
                DragPosition.Start at 0f
                DragPosition.End at travelDistancePx
            }
        )
    }

    // 屏幕密度等变化后，同步更新锚点。
    SideEffect {
        state.updateAnchors(
            DraggableAnchors {
                DragPosition.Start at 0f
                DragPosition.End at travelDistancePx
            }
        )
    }
    val flingBehavior = AnchoredDraggableDefaults.flingBehavior(
        state = state,
        positionalThreshold = { distance -> distance * 0.5f }
    )



    Column(
        modifier = Modifier.fillMaxSize()
            .border(2.dp, Color.Red)
    ) {
        Box(
            modifier = Modifier.size(100.dp)
                .offset {
                    offsetOne.round()
                }
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFef476f))
                .draggable2D(state = rememberDraggable2DState {
                    offsetOne += it
                }, onDragStopped = {
                    offsetOne = Offset(
                        with(density) {offsetOne.x.coerceIn(0F, (containerDpSize.width - 100.dp).toPx())},
                        with(density) {offsetOne.y.coerceIn(0F, (containerDpSize.height - 100.dp).toPx())}
                    )
                }),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "draggable2D",
                color = Color.White,
                style = MaterialTheme.typography.labelMedium)
        }
        Box(
            modifier = Modifier.size(100.dp)
                .offset {
                    offsetTwo.round()
                }
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFffd166))
                .draggable(state = rememberDraggableState {
                    offsetTwo += Offset(it, 0f)
                }, orientation = Orientation.Horizontal, onDragStopped = {
                    offsetTwo = Offset(
                        with(density) {offsetTwo.x.coerceIn(0F, (containerDpSize.width - 100.dp).toPx())},
                        offsetTwo.y
                    )
                }),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "draggable",
                color = Color.White,
                style = MaterialTheme.typography.labelMedium)
        }


        Box(
            modifier = Modifier.height(100.dp).fillMaxWidth()
                .border(2.dp, Color.Gray)
                .anchoredDraggable(
                    state = state,
                    orientation = Orientation.Horizontal,
                    enabled = true,
                    flingBehavior = flingBehavior
                )

        ) {
            Box(
                modifier = Modifier.size(100.dp)
                    .offset {
                        IntOffset(
                            x = state.requireOffset().roundToInt(),
                            y = 0
                        )
                    }
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF06d6a0)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "anchoredDraggable",
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }


    }

}
