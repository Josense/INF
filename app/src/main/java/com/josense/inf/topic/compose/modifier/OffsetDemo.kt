package com.josense.inf.topic.compose.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun OffsetDemo() {
    Column (
        modifier = Modifier.fillMaxSize()
            .border(2.dp, Color.Red)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var offsetX by remember { mutableFloatStateOf(16F) }
        var offsetY by remember { mutableFloatStateOf(16F) }

        var textOffsetX by remember { mutableFloatStateOf(0F) }
        var textOffsetY by remember { mutableFloatStateOf(0F) }

        Text(text = "offset X: %.0f dp, Y: %.0f dp".format(offsetX, offsetY))
        Slider(
            value = offsetX,
            onValueChange = {
                offsetX = it
            },
            valueRange = -100F..100F
        )
        Slider(
            value = offsetY,
            onValueChange = {
                offsetY = it
            },
            valueRange = -100F..100F
        )
        Spacer(Modifier.height(100.dp))
        Box(
            modifier = Modifier.size(100.dp)
                .background(Color(0xFF627264))
                .offset {
                    IntOffset(offsetX.dp.roundToPx(), offsetY.dp.roundToPx())
                }
        ) {
            Box(
                modifier = Modifier.size(300.dp)
                    .background(Color(0xFFA1CDA8)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "InnerBox",
                    color = Color(0xFF1D2F6F),
                    modifier = Modifier
                        .offset {
                            IntOffset(textOffsetX.dp.roundToPx(), textOffsetY.dp.roundToPx())
                        }
                        .border(1.dp, Color(0xFF1D2F6F))
                )
            }
        }

        Spacer(Modifier.height(100.dp))



        Text(text = "offset X: %.0f dp, Y: %.0f dp".format(textOffsetX, textOffsetY))
        Slider(
            value = textOffsetX,
            onValueChange = {
                textOffsetX = it
            },
            valueRange = -100F..100F
        )
        Slider(
            value = textOffsetY,
            onValueChange = {
                textOffsetY = it
            },
            valueRange = -100F..100F
        )

    }


}