package com.josense.inf.ui.topic.viewmodel.savedstatehandle

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
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
data class ScreenB(val greeting: String)

@Serializable
data object ScreenC

@Serializable
data object ScreenBAndCGraph

val ViewModelSavedStateHandleExperiment = Experiment(
    id = "viewModel_saved_state_handle",
    name = "ViewModel SavedStateHandle 实验",
    page = ::ViewModelSavedStateHandleExperimentPage,
    topic = ExperimentTopic.ViewModel,
)

@Composable
fun ViewModelSavedStateHandleExperimentPage() {
    val innerNavController = rememberNavController()
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
        NavHost(
            navController = innerNavController,
            startDestination = ScreenA,
            route = InnerNavGraph::class,
            modifier = Modifier.fillMaxSize(),
        ) {
            composable<ScreenA> {
                val viewModel = viewModel<SavedCounterViewModel>()
                val counter by viewModel.counter.collectAsStateWithLifecycle()

                // ⚠️⚠️⚠️ 接收来自下一个屏幕回传的结果值；
                val screenBCounter by it.savedStateHandle
                    .getStateFlow("screen_b_counter", 0)
                    .collectAsStateWithLifecycle()
                val context = LocalContext.current
                LaunchedEffect(true) {
                    Toast.makeText(
                        context,
                        "The counter on screen B was $screenBCounter",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                CounterUi(
                    screenName = "Screen A",
                    counter = counter,
                    onIncrementClick = viewModel::increment,
                    onNavigateClick = { innerNavController.navigate(ScreenB("Hello from Screen A!")) },
                    navigateButtonText = "Go to Screen B",
                )
            }

            navigation<ScreenBAndCGraph>(startDestination = ScreenB("")) {
                composable<ScreenB> {
                    val viewModel = it.shardViewModel<SavedCounterViewModel>(innerNavController)
                    val counter by viewModel.counter.collectAsStateWithLifecycle()
                    val context = LocalContext.current
                    var hasShownToast by rememberSaveable { mutableStateOf(false) }

                    // ⚠️⚠️⚠️ 接收来自上一个屏幕的参数；
                    LaunchedEffect(true) {
                        if (hasShownToast) return@LaunchedEffect
                        hasShownToast = true
                        Toast.makeText(context, "The greeting is ${viewModel.greeting}", Toast.LENGTH_SHORT).show()
                    }

                    // ⚠️⚠️⚠️ 实时变化传给上一个屏幕；
                    LaunchedEffect(counter) {
                        innerNavController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("screen_b_counter", counter)
                    }
                    CounterUi(
                        screenName = "Screen B",
                        counter = counter,
                        onIncrementClick = viewModel::increment,
                        onNavigateClick = { innerNavController.navigate(ScreenC) },
                        navigateButtonText = "Go to Screen C",
                        onBackClick = { innerNavController.popBackStack() },
                        backButtonText = "Back to Screen A"
                    )
                }
                composable<ScreenC> {
                    val viewModel = it.shardViewModel<SavedCounterViewModel>(innerNavController)
                    val counter by viewModel.counter.collectAsStateWithLifecycle()
                    CounterUi(
                        screenName = "Screen C",
                        counter = counter,
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

