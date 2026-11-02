package com.josense.inf.topic.concurrency.coroutine

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlin.time.Duration.Companion.seconds

@Composable
fun FlowDemo() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            CountDownDemo()
        }
    }
}

@Composable
fun CountDownDemo() {
    val scope = rememberCoroutineScope()
    val viewModel = viewModel<CountDownViewModel>()
    val started by viewModel.started.collectAsStateWithLifecycle()
    val countdown by viewModel.countDownState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier.fillMaxWidth()
            .border(1.dp, Color.LightGray)
    ) {
        Text(text = "倒计时: $countdown")
        Button(
            onClick = {
                viewModel.started.update { it.not() }
            }
        ) {
            Text(text = "${ if (started) "Stop" else "Start" } CountDown")
        }
    }
}

class CountDownViewModel: ViewModel() {
    val started = MutableStateFlow(false)
    @OptIn(ExperimentalCoroutinesApi::class)
    val countDownState = started.flatMapLatest {
        if (it) {
            flow {
                emit(10)
                repeat(10) {
                    delay(1.seconds)
                    emit(9 - it)
                }
                started.value = false
            }
        } else {
            emptyFlow()
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        10
    )
}