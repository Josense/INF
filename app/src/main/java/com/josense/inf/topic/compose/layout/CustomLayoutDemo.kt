package com.josense.inf.topic.compose.layout

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.constrainWidth
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import kotlin.random.Random
import kotlin.random.nextInt

@Composable
fun CustomLayoutDemo() {
    // 按当前元素位移
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "自定义 LayoutModifier")

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.size(50.dp)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "原始位置",
                    fontSize = 8.sp
                )
            }

            Box(
                modifier = Modifier.size(50.dp).filp(Axis.Left)
                    .background(Color.Green),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "左移一个当前组件的宽度",
                    fontSize = 8.sp
                )
            }

            Box(
                modifier = Modifier.size(50.dp).filp(Axis.Right)
                    .background(Color.Green),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "右移一个当前组件的宽度",
                    fontSize = 8.sp
                )
            }

            Box(
                modifier = Modifier.size(50.dp).filp(Axis.Top)
                    .background(Color.Green),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "上移一个当前组件的高度",
                    fontSize = 8.sp
                )
            }

            Box(
                modifier = Modifier.size(50.dp).filp(Axis.Bottom)
                    .background(Color.Green),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "下移一个当前组件的高度",
                    fontSize = 8.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(60.dp))

        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text(text = "自定义 Layout")
        // Layout PagedRow
        var pageIndex by remember { mutableStateOf(0) }
        Button(
            onClick = {
                pageIndex++
            }
        ) {
            Text(text = "Go to Next Page")
        }
        PagedRow(
            pageIndex = pageIndex,
            modifier = Modifier.heightIn(max = 300.dp)
        ) {
            for (i in 1..10) {
                val width = Random.nextInt(50, 300)
                Box(
                    modifier = Modifier.height(Random.nextInt(50, 100).dp)
                        .width(width.dp)
                        .background(Color(Random.nextInt()))
                        .border(1.dp, Color.Gray)
                ) {
                    Text(text = width.toString())
                }
            }
//            Box(
//                modifier = Modifier.height(100.dp)
//                    .width(300.dp)
//                    .background(Color.Red)
//            )
//            Box(
//                modifier = Modifier.height(100.dp)
//                    .width(300.dp)
//                    .background(Color.Blue)
//            )
        }

        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text(text = "自定义 SubcomposeLayout")
        // Layout PagedRow
        var pageIndex2 by remember { mutableStateOf(0) }
        Button(
            onClick = {
                pageIndex2++
            }
        ) {
            Text(text = "Go to Next Page")
        }

        SubcomposePagedRow(
            pageIndex = pageIndex2,
            modifier = Modifier
        ) {
            for (i in 1..10) {
                val width = Random.nextInt(50, 300)
                Box(
                    modifier = Modifier.height(Random.nextInt(50, 100).dp)
                        .width(width.dp)
                        .background(Color(Random.nextInt()))
                        .border(1.dp, Color.Gray)
                ) {
                    Text(text = width.toString())
                }
            }
        }

        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
    }
}


fun Modifier.filp(axis: Axis = Axis.Top) = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    val (x, y) = when (axis) {
        Axis.Left -> -placeable.width to 0
        Axis.Top -> 0 to -placeable.height
        Axis.Right -> placeable.width to 0
        Axis.Bottom -> 0 to placeable.height
    }
    layout(placeable.width, placeable.height) {
        placeable.place(x, y)
    }
}

enum class Axis {
    Left, Top, Right, Bottom;
}


@Composable
fun PagedRow(
    pageIndex: Int = 0,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content,
    ) { measurables, constraints ->
        Log.d("PagedRow","constraints:$constraints")
        var page = 0
        var pageTotalWidth = 0
        var pageTotalHeight = 0
        val placeables = mutableListOf<Placeable>()
        for (index in measurables.indices) {
            Log.d("PagedRow","measure index $index")
            val placeable = measurables[index].measure(constraints)
            Log.d("PagedRow","placeable:${placeable.width}, pageTotalWidth:$pageTotalWidth, maxWidth:${constraints.maxWidth}")
            if (pageTotalWidth + placeable.width > constraints.maxWidth) {
                page++
                if (page > pageIndex) {
                    break
                }
                pageTotalWidth = 0
                pageTotalHeight = 0
                placeables.clear()
            }

            pageTotalWidth += placeable.width
            pageTotalHeight = maxOf(pageTotalHeight, placeable.height)
            placeables.add(placeable)
        }

        if (page <= pageIndex - 1) {
            layout(0, 0) {}
        } else {
            Log.d("PagedRow","placeables:${placeables.size}")
            val width = constraints.constrainWidth(pageTotalWidth)
            val height = constraints.constrainHeight(pageTotalHeight)
            layout(width, height) {
                var offsetX = 0
                placeables.forEach { placeable ->
                    placeable.place(offsetX, 0)
                    offsetX += placeable.width
                }
            }
        }

    }
}

@Composable
fun SubcomposePagedRow(
    pageIndex: Int = 0,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    SubcomposeLayout(
        modifier = modifier,
    ) { constraints ->
        Log.d("PagedRow","constraints:$constraints")
        var page = 0
        var pageTotalWidth = 0
        var pageTotalHeight = 0
        val placeables = mutableListOf<Placeable>()
        val measurables = subcompose("content", content)
        for (index in measurables.indices) {
            Log.d("PagedRow","measure index $index")
            val placeable = measurables[index].measure(constraints)
            Log.d("PagedRow","placeable:${placeable.width}, pageTotalWidth:$pageTotalWidth, maxWidth:${constraints.maxWidth}")
            if (pageTotalWidth + placeable.width > constraints.maxWidth) {
                page++
                if (page > pageIndex) {
                    break
                }
                pageTotalWidth = 0
                pageTotalHeight = 0
                placeables.clear()
            }

            pageTotalWidth += placeable.width
            pageTotalHeight = maxOf(pageTotalHeight, placeable.height)
            placeables.add(placeable)
        }

        if (page <= pageIndex - 1) {
            layout(0, 0) {}
        } else {

            Log.d("PagedRow","placeables:${placeables.size}")
            val width = constraints.constrainWidth(pageTotalWidth)
            val height = constraints.constrainHeight(pageTotalHeight)
            layout(width, height) {
                var offsetX = 0
                placeables.forEach { placeable ->
                    placeable.place(offsetX, 0)
                    offsetX += placeable.width
                }
            }
        }

    }
}

