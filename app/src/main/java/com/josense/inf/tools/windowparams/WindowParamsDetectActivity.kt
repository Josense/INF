package com.josense.inf.tools.windowparams

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.height
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.width

class WindowParamsDetectActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        setContent {
            WindowParamsDetectPage()
        }

    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun WindowParamsDetectPage() {
    val statusBarHeightDp = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val navigationBarHeightDp = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val screenCorners = rememberScreenCornersSafe()
    val windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass
    val displayCutout = rememberDisplayCutoutDpBounds()

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
            .border(
                2.dp,
                Color(0x55ff0000),
                RoundedCornerShape(
                    screenCorners.topLeft,
                    screenCorners.topRight,
                    screenCorners.bottomLeft,
                    screenCorners.bottomRight
                )
            )
    ) {
        val parentWidth = maxWidth
        val parentHeight = maxHeight
        // 窗口
        Text(
            text = "Window: $parentWidth x $parentHeight\n" +
                    "圆角:\nTopLeft:${screenCorners.topLeft}\nTopRight:${screenCorners.topRight}\n" +
                    "BottomLeft:${screenCorners.bottomLeft}\nBottomRight:${screenCorners.bottomRight}\n" +
                    "windowSizeClass: \nwidth:${windowSizeClass.windowWidthSizeClass}\nheight:${windowSizeClass.windowHeightSizeClass}",
            modifier = Modifier.padding(bottom = navigationBarHeightDp * 3, end = 2.dp)
                .background(Color(0x55ff0000))
                .padding(8.dp)
                .align(Alignment.BottomEnd),
        )
        // 状态栏
        if (statusBarHeightDp > 0.dp) {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .height(statusBarHeightDp)
                    .padding(2.dp, 2.dp, 2.dp, 0.dp)
                    .border(
                        2.dp, Color(0x5500FF00),
                        RoundedCornerShape(
                            screenCorners.topLeft,
                            screenCorners.topRight,
                        )
                    )
            )
            Text(
                text = "StatusBarHeight: $statusBarHeightDp",
                modifier = Modifier.padding(top = statusBarHeightDp, start = 2.dp)
                    .widthIn(max = parentWidth / 3)
                    .background(Color(0x5500FF00))
                    .padding(4.dp)
                    .align(Alignment.TopStart),
            )
        }

        // 导航栏
        if(navigationBarHeightDp > 0.dp) {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .height(navigationBarHeightDp)
                    .padding(2.dp, 0.dp, 2.dp, 2.dp)
                    .border(
                        width = 2.dp, color = Color(0x5500FF00),
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = screenCorners.bottomLeft,
                            bottomEnd = screenCorners.bottomRight,
                        )
                    )
                    .align(Alignment.BottomCenter)
            )
            Text(
                text = "NavigationBarHeight: $navigationBarHeightDp",
                modifier = Modifier.padding(bottom = navigationBarHeightDp, start = 2.dp)
                    .background(Color(0x5500FF00))
                    .padding(4.dp)
                    .align(Alignment.BottomStart),
            )
        }

        // 手势区
        val gestureStartDp = WindowInsets.safeGestures
            .asPaddingValues()
            .calculateStartPadding(LayoutDirection.Ltr)
        val gestureEndDp = WindowInsets.safeGestures
            .asPaddingValues()
            .calculateEndPadding(LayoutDirection.Ltr)
        val gestureTopDp = WindowInsets.safeGestures
            .asPaddingValues()
            .calculateTopPadding()
        val gestureBottomDp = WindowInsets.safeGestures
            .asPaddingValues()
            .calculateBottomPadding()
        Box(
            modifier = Modifier.padding(
                start = gestureStartDp,
                top = gestureTopDp,
                end = gestureEndDp,
                bottom = gestureBottomDp,
            ).fillMaxSize()
                .border(2.dp, Color(0x550000FF))
        ) {
            Text(
                text = "手势安全区\nstart:$gestureStartDp\ntop:$gestureTopDp\nend:$gestureEndDp\nbottom:$gestureBottomDp",
                modifier = Modifier.padding(start = 2.dp)
                    .background(Color(0x550000FF))
                    .padding(8.dp)
                    .align(Alignment.Center)

            )
        }

//

        displayCutout.forEach { rect ->

            Box(
                modifier = Modifier
                    .absoluteOffset(x = rect.left - 4.dp, y = rect.top - 4.dp)
                    .size(rect.width + 8.dp, rect.height + 8.dp)
                    .background(Color(0x5500FFFF))
            )
            if ((parentHeight - rect.bottom) > parentHeight / 2) {
                // 下方放置
                Text(
                    text = "displayCutout:\n${"[${rect.left}, ${rect.top}, ${rect.right}, ${rect.bottom} ]"}",
                    modifier = Modifier.offset(y = rect.bottom + 4.dp)
                        .align(Alignment.TopCenter)
                        .widthIn(min = parentWidth / 4, max = parentWidth / 3)
                        .background(Color(0x5500FFFF))
                        .padding(8.dp)
                )
            } else if ((parentWidth - rect.right) > parentWidth / 2) {
                Text(
                    text = "displayCutout:\n${"[${rect.left}, ${rect.top}, ${rect.right}, ${rect.bottom} ]"}",
                    modifier = Modifier.offset(x = rect.right + 4.dp)
                        .align(Alignment.CenterStart)
                        .widthIn(min = parentWidth / 6, max = parentWidth / 4)
                        .background(Color(0x5500FFFF))
                        .padding(8.dp)
                )
            } else {
                Text(
                    text = "displayCutout:\n${"[${rect.left}, ${rect.top}, ${rect.right}, ${rect.bottom} ]"}",
                    modifier = Modifier.align(Alignment.CenterEnd)
                        .padding(end = parentWidth - rect.left + 4.dp)
                        .widthIn(min = parentWidth / 6, max = parentWidth / 4)
                        .background(Color(0x5500FFFF))
                        .padding(8.dp)
                )
            }

        }

    }
}

//@PreviewScreenSizes
@Composable
fun WindowParamsDetectPagePreview() {
    WindowParamsDetectPage()
}

enum class  ScreenOrientation {
    PORTRAIT,
    LEFT_LANDSCAPE,
    RIGHT_LANDSCAPE,
}