package com.josense.inf.topic.compose.touchevent

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun TouchEventBasicDemo() {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Box(
                modifier = Modifier.padding(32.dp)
                    .size(200.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .pointerInput(PointerType.Touch) {
                        awaitPointerEventScope {
                            while (true) {
                                val event = awaitPointerEvent()
                                // handle pointer event
                                Log.d("TouchEventBasicDemo", "${event.type}, ${event.changes.first().position}")
                            }
                        }
                    }
                    .pointerInput(PointerType.Touch) {
                        detectTapGestures(
                            onTap = { Log.d("TouchEventBasicDemo", "onTap:$it") },
                            onPress = { Log.d("TouchEventBasicDemo", "onPress:$it") },
                            onDoubleTap = { Log.d("TouchEventBasicDemo", "onDoubleTap:$it") },
                            onLongPress = { Log.d("TouchEventBasicDemo", "onLongPress:$it") },
                        )
                    }
                    .pointerInput(PointerType.Touch) {
                        detectDragGestures(
                            orientationLock = null,
                            onDragStart = { down, slopTriggerChange, overSlopOffset -> Log.d("TouchEventBasicDemo", "onDragStart:$down | $slopTriggerChange | $overSlopOffset") },
                            onDrag = { change, dragAmount -> Log.d("TouchEventBasicDemo", "onDrag:$change | $dragAmount") },
                            onDragEnd = { Log.d("TouchEventBasicDemo", "onDragEnd") },
                            onDragCancel = { Log.d("TouchEventBasicDemo", "onDragCancel") },
                        )

                    }
                    .pointerInput(PointerType.Touch) {
                        detectDragGesturesAfterLongPress(
                            onDragStart = { offset -> Log.d("TouchEventBasicDemo", "dragAfterLongPress onDragStart:$offset") },
                            onDragEnd = { Log.d("TouchEventBasicDemo", "dragAfterLongPress onDragEnd") },
                            onDragCancel = { Log.d("TouchEventBasicDemo", "dragAfterLongPress onDragCancel") },
                            onDrag = { change, dragAmount -> Log.d("TouchEventBasicDemo", "dragAfterLongPress onDrag:$change | $dragAmount") },
                        )
                    }
                    .pointerInput(PointerType.Touch) {
                        detectTransformGestures(
                            onGesture = { centroid, pan, zoom, rotation ->
                                Log.d(
                                    "TouchEventBasicDemo",
                                    "onGesture:$centroid | $pan | $zoom | $rotation"
                                )
                            }
                        )
                    }
                    .pointerInput(PointerType.Touch) {
                        awaitEachGesture {
                            awaitFirstDown().also { it.consume() }
                            val up = waitForUpOrCancellation()
                            if (up != null) {
                                up.consume()
                                Toast.makeText(context, "OnClick!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

            )
        }
        item {
            WheelLikeListDemo()
        }
    }
}