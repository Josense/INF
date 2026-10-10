package com.josense.inf.topic.viewmodel.savedstatehandle

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class SavedCounterViewModel(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    val counter = savedStateHandle.getStateFlow("counter", 0)
    val greeting = savedStateHandle.get<String>("greeting")


    fun increment() {
        savedStateHandle.update<Int>("counter", { it + 1})
    }

    override fun onCleared() {
        super.onCleared()
        println("CounterViewModel cleared!")
    }
}

inline fun <T> SavedStateHandle.update(key: String, update: (T) -> T) {
    synchronized(this) {
        val current = get<T>(key)
        val updated = update(current ?: return)
        set(key, updated)
    }
}