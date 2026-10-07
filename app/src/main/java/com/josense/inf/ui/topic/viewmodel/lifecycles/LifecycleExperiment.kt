package com.josense.inf.ui.topic.viewmodel.lifecycles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.josense.inf.data.Experiment
import com.josense.inf.data.ExperimentTopic
import kotlinx.serialization.Serializable

@Serializable
data object InnerNavGraph

@Serializable
data object ScreenA

@Serializable
data object ScreenB

@Serializable
data object ScreenC

@Serializable
data object ScreenBAndCGraph

val ViewModelLifecycleExperiment = Experiment(
    id = "viewModel_lifecycles",
    name = "ViewModel 生命周期实验",
    page = ::ViewModelLifecyclesExperimentPage,
    topic = ExperimentTopic.ViewModel,
)

@Composable
fun ViewModelLifecyclesExperimentPage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5F)
            .heightIn(min = 300.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        val innerNavController = rememberNavController()
        NavHost(
            navController = innerNavController,
            startDestination = ScreenA,
            route = InnerNavGraph::class,
            modifier = Modifier.fillMaxSize(),
        ) {
            composable<ScreenA> {
                val viewModel = viewModel<CounterViewModel>()
                CounterUi(
                    screenName = "Screen A",
                    counter = viewModel.counter,
                    onIncrementClick = viewModel::increment,
                    onNavigateClick = { innerNavController.navigate(ScreenB) },
                    navigateButtonText = "Go to Screen B",
                )
            }

            navigation<ScreenBAndCGraph>(startDestination = ScreenB) {
                composable<ScreenB> {
                    val viewModel = it.shardViewModel<CounterViewModel>(innerNavController)
                    CounterUi(
                        screenName = "Screen B",
                        counter = viewModel.counter,
                        onIncrementClick = viewModel::increment,
                        onNavigateClick = { innerNavController.navigate(ScreenC) },
                        navigateButtonText = "Go to Screen C",
                        onBackClick = { innerNavController.popBackStack() },
                        backButtonText = "Back to Screen A"
                    )
                }
                composable<ScreenC> {
                    val viewModel = it.shardViewModel<CounterViewModel>(innerNavController)
                    CounterUi(
                        screenName = "Screen C",
                        counter = viewModel.counter,
                        onIncrementClick = viewModel::increment,
                        onBackClick = { innerNavController.popBackStack() },
                        backButtonText = "Back to Screen B",
                    )
                }
            }
        }
    }
}

@Composable
private inline fun <reified T: ViewModel> NavBackStackEntry.shardViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(viewModelStoreOwner = parentEntry)
}

@Composable
fun CounterUi(
    screenName: String,
    counter: Int,
    onIncrementClick: () -> Unit,
    onNavigateClick: () -> Unit = {},
    navigateButtonText: String = "",
    onBackClick: () -> Unit = {},
    backButtonText: String = "",
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = screenName,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Button(onClick = onIncrementClick) {
            Text("Counter: $counter")
        }

        if (navigateButtonText.isNotBlank()) {
            Button(onClick = onNavigateClick) {
                Text(navigateButtonText)
            }
        }

        if (backButtonText.isNotBlank()) {
            Button(onClick = onBackClick) {
                Text(backButtonText)
            }
        }
    }
}