package com.josense.inf.topic.compose.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.swmansion.enriched.markdown.compose.EnrichedMarkdownText

@Composable
fun FocusDemo() {
    val focusManager = LocalFocusManager.current
    val rowNum = 6
    var parentIsFocused by remember { mutableIntStateOf(0) }
    var childIsFocused by remember { mutableIntStateOf(0) }
    var hasFocused by remember { mutableIntStateOf(0) }
    val colors = listOf(0xFF0d3b66, 0xFFa8d5e2, 0xFFf9a620, 0xFFffd449, 0xFF548c2f, 0xFF104911)
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            EnrichedMarkdownText(
                markdown = """
                    红色方框表示当前元素拥有焦点
                    
                    灰色方块表示当前焦点组内有元素有焦点
                """.trimIndent()
            )
        }

        items(5) { col ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.focusGroup()
                    .onFocusChanged {
                        if (it.isFocused) {
                            parentIsFocused = col + 1
                        } else {
                            parentIsFocused = 0
                        }
                        if (it.hasFocus) {
                            hasFocused = col + 1
                        } else {
                            hasFocused = 0
                        }
                    }
                    .focusable()
                    .padding(4.dp)
                    .border(2.dp, if (parentIsFocused == col + 1) Color.Red else if (hasFocused == col + 1) Color.Gray else Color.Transparent)
                    .padding(4.dp)
            ) {
                for (row in 0 until rowNum) {
                    Box(
                        modifier = Modifier.size(50.dp).background(Color(colors[row]))
                            .border(2.dp, if (childIsFocused == col * rowNum + row + 1) Color.Red else Color.Transparent)
                            .onFocusChanged {
                                if (it.isFocused) {
                                    childIsFocused = col * rowNum + row + 1
                                } else {
                                    childIsFocused = 0
                                }
                            }
                            .focusable()
                    )
                }
            }
        }

        item {
            Row {
                Button(
                    onClick = {
                        focusManager.moveFocus(FocusDirection.Next)
                    }
                ) {
                    Text(("Next Focus"))
                }
                Button(
                    onClick = {
                        focusManager.moveFocus(FocusDirection.Previous)
                    }
                ) {
                    Text(("Previous Focus"))
                }
            }

        }
        item {
            Row {
                Button(
                    onClick = {
                        focusManager.moveFocus(FocusDirection.Left)
                    }
                ) {
                    Text(("Left Focus"))
                }
                Button(
                    onClick = {
                        focusManager.moveFocus(FocusDirection.Right)
                    }
                ) {
                    Text(("Right Focus"))
                }
            }
        }
        item {
            Row {
                Button(
                    onClick = {
                        focusManager.moveFocus(FocusDirection.Up)
                    }
                ) {
                    Text(("Up Focus"))
                }
                Button(
                    onClick = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ) {
                    Text(("Down Focus"))
                }
            }
        }
        item {
            Row {
                Button(
                    onClick = {
                        focusManager.moveFocus(FocusDirection.Enter)
                    }
                ) {
                    Text(("Enter Focus"))
                }
                Button(
                    onClick = {
                        focusManager.moveFocus(FocusDirection.Exit)
                    }
                ) {
                    Text(("Exit Focus"))
                }
            }
        }
        item {
            Button(
                onClick = {
                    focusManager.clearFocus()
                }
            ) {
                Text(("Clear Focus"))
            }
        }
    }
}