package com.josense.inf.ui.topic.viewmodel.internals

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue

class CounterMiniSimulatedViewModel(
    initCount: Int = 0,
): MiniSimulatedViewModel() {

    var counter by mutableIntStateOf(initCount)
        private set

    fun increment() {
        counter++
    }

    override fun onCleared() {
        super.onCleared()
        println("CounterMiniSimulatedViewModel cleared!")
    }

    class Factory(private val initCount: Int): MiniSimulatedViewModelFactory {
        override fun <T : MiniSimulatedViewModel> create(viewModelClass: Class<T>): T {
            return CounterMiniSimulatedViewModel(initCount) as T
        }
    }
}