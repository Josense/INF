package com.josense.inf.ui.page.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.SmartButton
import androidx.compose.ui.graphics.vector.ImageVector

sealed class HomeScreen(val route: String, val title: String, val icon: ImageVector) {
    object Home : HomeScreen("Home", "主页", Icons.TwoTone.Home)
    object Tool : HomeScreen("Tool", "工具", Icons.TwoTone.SmartButton)
}

val homeBottomNavItems = listOf(
    HomeScreen.Home,
    HomeScreen.Tool
)
