package com.josense.inf.topic.compose.layout

import android.R.attr.data
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ConstraintsDemo() {
    Column(
        modifier = Modifier.padding(top = 30.dp).fillMaxSize()
            .border(2.dp, Color.Red)

    ) {

        val density: Density = LocalDensity.current
        val constraintStates = remember {
            List(5) { mutableStateOf<String>("") }
        }
        val writers = remember {
            constraintStates.map { state ->
                { tag: String, constraints: Constraints ->
                    state.value = "$tag:\n$constraints"
                }
            }
        }


        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth().height(100.dp)
        ) {
            val parentWidth = maxWidth
            Row(
                modifier = Modifier.fillMaxWidth().height(100.dp)
            ) {
                Box(
                    modifier = Modifier.height(100.dp)
                        .fillMaxWidth(1 /2.0F)
                        .background(Color.Red)
                )
                Box(
                    modifier = Modifier.height(100.dp)
                        .fillMaxWidth(1F)
                        .background(Color.Blue)
                )
                Box(
                    modifier = Modifier.height(80.dp)
                        .requiredWidth(parentWidth)
                        .background(Color.Green)
                ) {
                    Text(
                        text = "这个盒子没有空间了，但是按照 requiredWidth 设置了屏幕宽度，它只能在剩余空间中居中放置。所以最终渲染效果就是在屏幕上占用了一半的宽度",
                        modifier = Modifier.align(Alignment.CenterStart)
                            .fillMaxWidth(1/2F),
                        fontSize = 9.sp
                    )
                }
            }
        }

        // 实验 box 的约束
        BoxWithConstraints(
            modifier = Modifier.padding(32.dp)
                .size(300.dp)
//                .aspectRatio(1F)
                .border(1.dp, Color.Blue)
        ) {
            val parentWidth = maxWidth
            val parentHeight = maxHeight
            Text(
                text = "width: $parentWidth, height: $parentHeight",
                modifier = Modifier.layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    val textHeight = placeable.height
                    val yOffset = -(textHeight)
                    layout(placeable.width, placeable.height) {
                        placeable.placeRelative(0, yOffset)
                    }
                }.background(Color.Blue),
                fontSize = 12.sp,
                color = Color.White,
            )

            // 实验 box
            BoxWithConstraints(
                modifier = Modifier.writerConstraints("origin", writers[0])
                    .wrapContentSize()
                    .writerConstraints("after wrapContentSize", writers[1])
                    .height(100.dp)
                    .writerConstraints("after height(${density.toPx(100.dp)})", writers[2])
                    .height(200.dp)
                    .writerConstraints("after height(${density.toPx(200.dp)})", writers[3])
                    .requiredHeight(200.dp)
                    .writerConstraints("after requiredHeight(${density.toPx(200.dp)})", writers[4])
                    .align(Alignment.Center)
                    .background(Color.Blue),
            ) {
                val parentWidth = maxWidth
                val parentHeight = maxHeight
                Text(
                    text = "constraints: $parentWidth ✕ $parentHeight",
                    color = Color.White,
                )
            }
        }

        val constraintsLog = constraintStates.mapIndexed { index, state ->
            state.value
        }.joinToString("\n\n")

        val scrollState = rememberScrollState()
        Text(
            text = constraintsLog,
            modifier = Modifier.verticalScroll(scrollState),
            fontSize = 12.sp
        )
    }
}


fun Density.toPx(dp: Dp): Float {
    return dp.toPx()
}



fun Modifier.writerConstraints(tag: String, writer: (String, Constraints) -> Unit): Modifier {
    return layout { measurable, constraints ->
        writer(tag, constraints)
        val placeable = measurable.measure(constraints = constraints)
        layout(placeable.width, placeable.height) {
            placeable.place(0, 0)
        }
    }
}