package com.josense.inf.topic.compose.basiclayout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.josense.inf.ui.theme.INFTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowDemo(modifier: Modifier = Modifier) {
    FlowRow(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalArrangement = Arrangement.Center,
        itemVerticalAlignment = Alignment.Bottom,
        maxItemsInEachRow = 3,
        maxLines = 5,
        overflow = FlowRowOverflow.expandOrCollapseIndicator(
            expandIndicator = {
                IconButton(onClick = {}) { Icon(Icons.Default.KeyboardArrowDown, contentDescription = "expand") }
            },
            collapseIndicator = {
                IconButton(onClick = {}) { Icon(Icons.Default.KeyboardArrowUp, contentDescription = "collapse") }
            }
        )
    ) {
        for (i in 0 until 30) {
            AssistChip(
                onClick = {},
                label = {
                    Text("Item: $i")
                },
                modifier = modifier.height(if (i % 3 == 1) 80.dp else 50.dp)
                    .padding(0.dp, 10.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlowDemoPreview() {
    INFTheme {
        _root_ide_package_.com.josense.inf.topic.compose.basiclayout.FlowDemo()
    }
}