package com.josense.inf.topic.viewmodel.internals

interface MiniSimulatedViewModelFactory {
    fun <T: com.josense.inf.topic.viewmodel.internals.MiniSimulatedViewModel> create(viewModelClass: Class<T>): T
}

class DefaultMiniSimulatedViewModelFactory:
    com.josense.inf.topic.viewmodel.internals.MiniSimulatedViewModelFactory {
    override fun <T : com.josense.inf.topic.viewmodel.internals.MiniSimulatedViewModel> create(viewModelClass: Class<T>): T {
        return viewModelClass.getDeclaredConstructor().newInstance()
    }
}