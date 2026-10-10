package com.josense.inf.topic.viewmodel.internals

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue

class CounterMiniSimulatedViewModel(
    initCount: Int = 0,
): com.josense.inf.topic.viewmodel.internals.MiniSimulatedViewModel() {

    var counter by mutableIntStateOf(initCount)
        private set

    fun increment() {
        counter++
    }

    override fun onCleared() {
        super.onCleared()
        println("CounterMiniSimulatedViewModel cleared!")
    }

    class Factory(private val initCount: Int):
        com.josense.inf.topic.viewmodel.internals.MiniSimulatedViewModelFactory {
        override fun <T : com.josense.inf.topic.viewmodel.internals.MiniSimulatedViewModel> create(viewModelClass: Class<T>): T {
            return CounterMiniSimulatedViewModel(initCount) as T
        }
    }
}