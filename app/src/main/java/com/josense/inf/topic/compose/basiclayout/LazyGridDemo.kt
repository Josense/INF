package com.josense.inf.topic.compose.basiclayout

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.swmansion.enriched.markdown.compose.EnrichedMarkdownText
import kotlin.random.Random

@Composable
fun LazyGridDemo() {

    Column (
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
    ) {
        EnrichedMarkdownText(
            markdown = """
                Compose 包括了 LazyVerticalGrid、LazyHorizontalGrid、以及他们对应的 Staggered 版本。

                GridCells.Fixed(count: Int) 会将网格固定为指定的列数；

                GridCells.Adaptive(minSize: Dp) 根据屏幕宽度自适应计算列数，保证每列的宽度至少为 minSize；

                GridCells.FixedSize(size: Dp) 固定每一列的宽度为绝对的 size，列数由总宽度决定。
            """.trimIndent()
        )
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(5),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(100) {
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(8.dp))
                        .width(Random.nextInt(1, 30).dp)
                        .height(Random.nextInt(30, 100).dp)
                        .background(Color(Random.nextInt()))
                )
            }
        }
    }
}