package com.josense.inf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.josense.inf.ui.page.home.HomePage
import com.josense.inf.ui.page.home.HomeScreen
import com.josense.inf.ui.page.home.HomeScreenGraph
import com.josense.inf.ui.page.home.ToolPage
import com.josense.inf.ui.page.home.homeBottomNavItems
import com.josense.inf.ui.theme.INFTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            INFTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Column {
                                    Text(text = getString(R.string.app_name))
                                    Text(
                                        text = getString(R.string.app_description),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentRoute = navBackStackEntry?.destination?.route
                            homeBottomNavItems.forEach { screen ->
                                NavigationBarItem(
                                    icon = {
                                        Icon(imageVector = screen.icon, contentDescription = screen.title)
                                    },
                                    label = {
                                        Text(screen.title)
                                    },
                                    selected = navBackStackEntry?.destination?.hasRoute(screen.route::class) == true,
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.startDestinationId) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = HomeScreenGraph.Home,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<HomeScreenGraph.Home> { HomePage() }
                        composable<HomeScreenGraph.Tool> { ToolPage() }
                    }
                }
            }
        }
    }
}

