package com.josense.inf.topic.compose.basiclayout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.swmansion.enriched.markdown.compose.EnrichedMarkdownText
import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.fakerConfig
import kotlin.random.Random

@Composable
fun LazyListDemo() {
    var reverse by remember { mutableStateOf(false) }
    var isColumn by remember { mutableStateOf(true) }
    Column (
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
    ) {
        EnrichedMarkdownText(
            markdown = """
                Lazy List 的关键是 Lazy，即只有在真的需要的时候才会加载，他基本上是 XML 中 RecyclerView 的等价物。
                
                有 LazyColumn 和 LazyRow 两个版本。
                
                item 可以多次调用，这意味着我们在 RecyclerView 中使用的类似 MultiTypeAdapter 等适配不同 item 的方案完全不需要了。
                
                支持 reverse 反向排列，比如聊天列表。
                
                支持 stickHeader，比如在很多电商 APP 中加很多个子 Header 的场景就会变得更容易实现。
            """.trimIndent()
        )
        Row(
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = {
                reverse = !reverse
            }) {
                Text(text = "Reverse")
            }
            Button(onClick = {
                isColumn = !isColumn
            }) {
                Text(text = if (isColumn) "Change to Row" else "Change to Colume")
            }
        }
        LazyListShell(
            isColumn,
            reverse,
            modifier = Modifier.fillMaxSize()
                .border(1.dp, MaterialTheme.colorScheme.outline)
                .padding(16.dp, 0.dp)
        ) {
            val config = fakerConfig {
                locale = "zh-CN"
            }
            val zhFaker = Faker(config)
            stickyHeader {
                Box(
                    modifier = Modifier.apply {
                        if (isColumn) fillMaxWidth() else fillMaxHeight()
                    }.background(Color(Random.nextInt()))
                        .padding(8.dp)
                ) {
                    Text(text="Books")
                }
            }
            items(20) { index ->
                Text(
                    text = zhFaker.book.title(),
                    modifier = Modifier.padding(8.dp)
                )
            }
            stickyHeader {
                Box(
                    modifier = Modifier.apply {
                        if (isColumn) fillMaxWidth() else fillMaxHeight()
                    }.background(Color(Random.nextInt()))
                        .padding(8.dp)
                ) {
                    Text(text="Name")
                }
            }
            items(20) { index ->
                Text(
                    text = zhFaker.name.name(),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun LazyListShell(
    isColumn: Boolean,
    reverse: Boolean,
    modifier: Modifier = Modifier,
    content: LazyListScope.() -> Unit
) {
    if (isColumn) {
        LazyColumn(reverseLayout = reverse, modifier = modifier, content = content)
    } else {
        LazyRow(reverseLayout = reverse, modifier = modifier, content = content)
    }
}
