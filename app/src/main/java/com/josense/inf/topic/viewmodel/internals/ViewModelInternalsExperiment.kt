package com.josense.inf.topic.viewmodel.internals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
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

val ViewModelInternalsExperiment = Experiment(
    id = "viewModel_internals",
    name = "ViewModel 原理实验",
    description = "模拟手搓一个最简单的 ViewModel",
    page = ::ViewModelInternalsExperimentPage,
    topic = ExperimentTopic.ViewModel,
)

@Composable
fun ViewModelInternalsExperimentPage() {
    val innerNavController = rememberNavController()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(10.dp),
    ) {
        NavHost(
            navController = innerNavController,
            startDestination = ScreenA,
            route = InnerNavGraph::class,
            modifier = Modifier.fillMaxSize(),
        ) {
            composable<ScreenA> { entry ->
                val viewModel = entry.miniViewModel<CounterMiniSimulatedViewModel>()
                CounterUi(
                    screenName = "Screen A (Independent VM)",
                    counter = viewModel.counter,
                    onIncrementClick = viewModel::increment,
                    onNavigateClick = { innerNavController.navigate(ScreenB) },
                    navigateButtonText = "Go to Screen B",
                )
            }

            navigation<ScreenBAndCGraph>(startDestination = ScreenB) {
                composable<ScreenB> { entry ->
                    val viewModel = entry.sharedMiniViewModel<CounterMiniSimulatedViewModel>(innerNavController)
                    CounterUi(
                        screenName = "Screen B (Shared Graph VM)",
                        counter = viewModel.counter,
                        onIncrementClick = viewModel::increment,
                        onNavigateClick = { innerNavController.navigate(ScreenC) },
                        navigateButtonText = "Go to Screen C",
                        onBackClick = { innerNavController.popBackStack() },
                        backButtonText = "Back to Screen A"
                    )
                }
                composable<ScreenC> { entry ->
                    val viewModel = entry.sharedMiniViewModel<CounterMiniSimulatedViewModel>(innerNavController)
                    CounterUi(
                        screenName = "Screen C (Shared Graph VM)",
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
fun CounterUi(
    screenName: String,
    counter: Int,
    onIncrementClick: () -> Unit,
    onNavigateClick: () -> Unit = {},
    navigateButtonText: String = "",
    onBackClick: () -> Unit = {},
    backButtonText: String = "",
    modifier: Modifier = Modifier
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

