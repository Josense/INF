package com.josense.inf.topic.compose.basiclayout

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.waterfall
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.swmansion.enriched.markdown.compose.EnrichedMarkdownText
import kotlin.random.Random
import kotlin.random.nextInt

enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    HOME("首页", Icons.Default.Home, ""),
    FAVORITES("收藏", Icons.Default.Favorite, ""),
    SHOPPING("购物", Icons.Default.ShoppingCart, ""),
    PROFILE("我", Icons.Default.AccountBox, ""),
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldDemo() {
    Column {
        Box(
            modifier = Modifier.padding(16.dp)
//                .border(1.dp, MaterialTheme.colorScheme.outline)
        ) {

            var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }
            NavigationSuiteScaffold(
                navigationSuiteItems = {
                    AppDestinations.entries.forEach {
                        item (
                            icon = {
                                Icon(
                                    it.icon,
                                    contentDescription = it.contentDescription
                                )
                            },
                            label = { Text(text = it.label) },
                            selected = it == currentDestination,
                            onClick = { currentDestination = it },
                            modifier = Modifier.border(1.dp, Color.Magenta)
                        )
                    }
                },
                modifier = Modifier.border(2.dp, Color.Red)
            ) {
                Scaffold (
                    modifier = Modifier.padding(2.dp).border(2.dp, Color.Green),
                    topBar = {
                        TopAppBar(
                            modifier = Modifier.padding(2.dp).border(2.dp, Color.Blue),
                            title = {
                                Text("TopAppBar")
                            },
                            navigationIcon = {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = ""
                                )
                            },
                            windowInsets = WindowInsets.waterfall
                        )
                    },
                    bottomBar = {
                        BottomAppBar {
                            NavigationBarItem(
                                selected = true,
                                onClick = { },
                                icon = {
                                    Icon(Icons.Default.Home, contentDescription = "")
                                },
                                label = {
                                    Text(text = "首页")
                                },
                                modifier = Modifier.padding(2.dp).border(1.dp, Color.Magenta)
                            )
                            NavigationBarItem(
                                selected = true,
                                onClick = { },
                                icon = {
                                    Icon(Icons.Default.Settings, contentDescription = "")
                                },
                                label = {
                                    Text(text = "设置")
                                },
                                modifier = Modifier.padding(2.dp).border(1.dp, Color.Magenta)
                            )
                        }
                    },
                ) { paddingValues ->
                    val scrollState = rememberScrollState()
                    val uriHandler = LocalUriHandler.current
                    Box(
                        modifier = Modifier.padding(paddingValues)
                            .padding(2.dp)
                            .fillMaxSize()
                            .border(2.dp, Color.Black)
                            .padding(16.dp)
                            .verticalScroll(scrollState),
                    ) {
                        EnrichedMarkdownText(
                            markdown = """
                                        Scaffold 本质上是 Material 设计中定义的骨架组件。从下图中我们能看到他的设计，除了图中所示的区域，另外还包括 FAB、SnackBarHost

                                        默认情况下 Pane 内容区是全屏绘制的（包括状态栏部分），这也是官方提供 padding 的原因，通过设置给定的 padding，可以将内容区限定在正确的范围内。

                                        注意默认 Compose 依赖中的 Scaffold 组件并不包括 Rail 区域，如果需要完整支持这部分，需要额外引入专门的库并使用 NavigationSuiteScaffold 来定义，可以参考[build adaptive navigation](https://developer.android.com/develop/adaptive-apps/guides/build-adaptive-navigation)。
                                        
                                        ![Scaffold](https://firebasestorage.googleapis.com/v0/b/design-spec/o/projects%2Fgoogle-material-3%2Fimages%2Fmp3n7x8n-01.png?alt=media)
                                    """.trimIndent(),
                            onLinkClick = { link ->
                                uriHandler.openUri(link)
                            }
                        )
                    }
                }
            }
        }
    }
}

