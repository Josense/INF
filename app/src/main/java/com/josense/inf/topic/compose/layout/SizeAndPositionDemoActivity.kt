package com.josense.inf.topic.compose.layout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.core.view.WindowCompat
import com.josense.inf.R

class SizeAndPositionDemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_size_and_position_demo)

        val composeView = findViewById<ComposeView>(R.id.compose_view)
        composeView.setContent {
            ComposeRoot()
        }
    }
}

@Composable
fun ComposeRoot() {
    BoxWithConstraints(
        modifier = Modifier.background(Color.Blue),
        contentAlignment = Alignment.Center,
    ) {

        val parentWidth = maxWidth
        var popupShow by remember { mutableStateOf(false) }
        Text(
            text = "Compose Root",
            modifier = Modifier.align(Alignment.TopStart),
            color = Color.White,
        )

        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.Green),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Compose Parent",
                modifier = Modifier.align(Alignment.TopStart),
            )

            var boxSize by remember { mutableStateOf(IntSize.Zero) }
            var positionInParent by remember { mutableStateOf(Offset.Zero) }
            var positionInRoot by remember { mutableStateOf(Offset.Zero) }
            var positionInWindow by remember { mutableStateOf(Offset.Zero) }
            var positionInScreen by remember { mutableStateOf(Offset.Zero) }

            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.Cyan)
                    .onSizeChanged {
                        boxSize = it
                    }
                    .onGloballyPositioned {
                        positionInParent = it.positionInParent()
                        positionInRoot = it.positionInRoot()
                        positionInWindow = it.positionInWindow()
                        positionInScreen = it.positionOnScreen()
                    }
            ) {

                Text(
                    text = "BoxSize: $boxSize\n\n" +
                            "positionInParent: $positionInParent\n\n" +
                            "positionInRoot: $positionInRoot\n\n" +
                            "positionInWindow: $positionInWindow\n\n" +
                            "positionInScreen: $positionInScreen\n\n",
                    fontSize = 12.sp,
                    modifier = Modifier.clickable {
                        popupShow = true
                    }
                )

            }
        }

        if (popupShow) {
            Popup(
                alignment = Alignment.TopCenter,
//                offset = IntOffset(0, 50), // 相对于 Box 锚点的偏移量
                properties = PopupProperties(
                    focusable = true,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                ),
                onDismissRequest = { popupShow = false }
            ) {
                PopupRoot()
            }
        }

    }
}

@Composable
fun PopupRoot() {
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
            .height(500.dp)
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {

        val parentWidth = maxWidth
        var popupShow by remember { mutableStateOf(false) }
        Text(
            text = "Popup Root",
            modifier = Modifier.align(Alignment.TopStart).padding(top = 16.dp),
            color = Color.Black,
        )

        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.Green),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Compose Parent",
                modifier = Modifier.align(Alignment.TopStart),
            )

            var boxSize by remember { mutableStateOf(IntSize.Zero) }
            var positionInParent by remember { mutableStateOf(Offset.Zero) }
            var positionInRoot by remember { mutableStateOf(Offset.Zero) }
            var positionInWindow by remember { mutableStateOf(Offset.Zero) }
            var positionInScreen by remember { mutableStateOf(Offset.Zero) }

            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.Cyan)
                    .onSizeChanged {
                        boxSize = it
                    }
                    .onGloballyPositioned {
                        positionInParent = it.positionInParent()
                        positionInRoot = it.positionInRoot()
                        positionInWindow = it.positionInWindow()
                        positionInScreen = it.positionOnScreen()
                    }
            ) {

                Text(
                    text = "BoxSize: $boxSize\n\n" +
                            "positionInParent: $positionInParent\n\n" +
                            "positionInRoot: $positionInRoot\n\n" +
                            "positionInWindow: $positionInWindow\n\n" +
                            "positionInScreen: $positionInScreen\n\n" +
                            "点击打开一个 PopupWindow",
                    fontSize = 12.sp,
                )
            }
        }
    }
}