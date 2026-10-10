package com.josense.inf.topic.viewmodel.internals

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel


/**
 * 原始教程是在 Activity 中实现的
 * 源码地址：https://github.com/philipplackner/AndroidInternals/blob/viewmodel/viewmodel-internals/app/src/main/java/com/plcoding/androidinternals/MyViewModel.kt
 */
abstract class MiniSimulatedViewModel {
    val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    open fun onCleared() {
        viewModelScope.cancel()
    }
}

