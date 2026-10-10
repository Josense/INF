package com.josense.inf.topic.compose.basiclayout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.josense.inf.R
import com.josense.inf.ui.theme.INFTheme

@Composable
fun BoxDemo(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.BottomCenter) {
        Image(
            painter = painterResource(R.drawable.kermit),
            contentDescription = null
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color.Black)
                    )
                ),
            contentAlignment = Alignment.BottomEnd
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color.Yellow
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BoxDemoPreview() {
    INFTheme() {
        _root_ide_package_.com.josense.inf.topic.compose.basiclayout.BoxDemo()
    }
}