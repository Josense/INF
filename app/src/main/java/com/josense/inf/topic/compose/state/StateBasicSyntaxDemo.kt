package com.josense.inf.topic.compose.state

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.josense.inf.topic.compose.tools.recomposeHighlighter
import com.swmansion.enriched.markdown.compose.EnrichedMarkdownText

@Composable
fun StaticBasicSyntaxDemo() {
    var recompose by remember { mutableStateOf(false) }
    val uriHandler = LocalUriHandler.current
    LazyColumn(
        modifier = Modifier.padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
    ) {
        item {
            EnrichedMarkdownText(
                markdown = """
                以下三个组件 count 按钮的变量分别用普通的 `var`， `var state` 和 `var remember state` 表示。
                
                点击时 count 加 1，并通过 Toast 来显示当前的实际数值。
                
                recompose 是一个 remember state 的 boolean 变量，每次点击，值会翻转，并显示在每个组件内部。
                
                每个 count 按钮通过 `recomposeHighlighter()` 来显示重组状态，可以参考 https://www.jetpackcompose.app/snippets/RecompositionHighlighter
                """.trimIndent(),
                onLinkClick = { link -> uriHandler.openUri(link) }
            )
        }
        item { Spacer(modifier = Modifier.height(10.dp)) }
        item {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    recompose = recompose.not()
                },
            ) {
                Text(text = "recompose")
            }
        }

        item { Spacer(modifier = Modifier.height(10.dp)) }

        item {
            Column(
                modifier = Modifier.border(2.dp, DividerDefaults.color)
                    .fillMaxWidth()
                    .heightIn(min = 120.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                PureVariableDemo(recompose)
            }
        }
        item { Spacer(modifier = Modifier.height(10.dp)) }
        item {
            Column(
                modifier = Modifier.border(2.dp, DividerDefaults.color)
                    .fillMaxWidth()
                    .heightIn(min = 120.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                StateVariableDemo(recompose)
            }
        }

        item { Spacer(modifier = Modifier.height(10.dp)) }
        item {
            Column(
                modifier = Modifier.border(2.dp, DividerDefaults.color)
                    .fillMaxWidth()
                    .heightIn(min = 120.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                RememberStateVariableDemo(recompose)
            }
        }

    }
}

@Composable
fun PureVariableDemo(recompose: Boolean) {
    val context = LocalContext.current
    var count = 0
    Button(
        modifier = Modifier.recomposeHighlighter(),
        onClick = {
            count++
            toast(context, "count:$count")
        },
    ) {
        Text(
            text = "count:$count",
            modifier = Modifier.recomposeHighlighter(),
        )
    }
    Text(text = "recompse:$recompose")
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun StateVariableDemo(recompose: Boolean) {
    val context = LocalContext.current
    var count by mutableIntStateOf(0)
    Button(
        modifier = Modifier.recomposeHighlighter(),
        onClick = {
            count++
            toast(context, "count:$count")
        },
    ) {
        Text(
            text = "count:$count",
            modifier = Modifier.recomposeHighlighter(),
        )
    }
    Text(text = "recompse:$recompose")
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun RememberStateVariableDemo(recompose: Boolean) {
    val context = LocalContext.current
    var count by remember { mutableIntStateOf(0) }
    Button(
        modifier = Modifier.recomposeHighlighter(),
        onClick = {
            count++
            toast(context, "count:$count")
        },
    ) {
        Text(
            text = "count:$count",
            modifier = Modifier.recomposeHighlighter(),
        )
    }
    Text(text = "recompse:$recompose")
}


fun toast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}