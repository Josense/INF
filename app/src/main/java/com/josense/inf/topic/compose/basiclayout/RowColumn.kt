package com.josense.inf.topic.compose.basiclayout

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.josense.inf.ui.theme.INFTheme
import kotlin.math.roundToInt

@Composable
fun RowColumnDemo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Bottom),
        horizontalAlignment = Alignment.Horizontal { size, space, layoutDirection ->
            Log.i("RowColumnDemo", "size:$size, space:$space")
            (space * 1.0 - size).roundToInt()
        }
    ) {
        Box(modifier = Modifier.size(60.dp).background(Color.Red))
        Box(modifier = Modifier.size(60.dp).background(Color.Blue).align(Alignment.Start))
        Box(modifier = Modifier.size(60.dp).background(Color.Green))
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFF,
)
@Composable
fun RowColumnDemoPreview() {
    INFTheme {
        _root_ide_package_.com.josense.inf.topic.compose.basiclayout.RowColumnDemo()
    }
}