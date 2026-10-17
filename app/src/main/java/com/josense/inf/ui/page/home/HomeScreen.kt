package com.josense.inf.ui.page.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.SmartButton
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

data class HomeScreen<T: HomeScreenGraph>(
    val route: T,
    val title: String,
    val icon: ImageVector
)

val homeBottomNavItems = listOf(
    HomeScreen(HomeScreenGraph.Home, "主页", Icons.TwoTone.Home),
    HomeScreen(HomeScreenGraph.Tool, "工具", Icons.TwoTone.SmartButton)
)


sealed interface HomeScreenGraph {
    @Serializable object Home : HomeScreenGraph
    @Serializable object Tool : HomeScreenGraph
}
