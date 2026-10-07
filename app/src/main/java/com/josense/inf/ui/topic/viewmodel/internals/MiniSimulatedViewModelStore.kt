package com.josense.inf.ui.topic.viewmodel.internals

class MiniSimulatedViewModelStore {
    private val storeMap = HashMap<String, MiniSimulatedViewModel>()

    fun <T: MiniSimulatedViewModel> put(viewModel: T) {
        storeMap[viewModel.javaClass.simpleName] = viewModel
    }

    @Suppress("UNCHECKED_CAST")
    fun <T: MiniSimulatedViewModel> get(viewModelClass: Class<T>): T? {
        return storeMap[viewModelClass.simpleName] as? T
    }

    fun clear() {
        storeMap.forEach { (_, viewModel) ->
            viewModel.onCleared()
        }
        storeMap.clear()
    }
}

interface MiniSimulatedViewModelStoreOwner {
    val miniViewModelStore: MiniSimulatedViewModelStore
}