package com.josense.inf.ui.topic.viewmodel.internals

interface MiniSimulatedViewModelFactory {
    fun <T: MiniSimulatedViewModel> create(viewModelClass: Class<T>): T
}

class DefaultMiniSimulatedViewModelFactory: MiniSimulatedViewModelFactory {
    override fun <T : MiniSimulatedViewModel> create(viewModelClass: Class<T>): T {
        return viewModelClass.getDeclaredConstructor().newInstance()
    }
}