package com.josense.inf.topic.compose.state.mvidemo

import androidx.lifecycle.ViewModel
import io.github.serpro69.kfaker.Faker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TodoItemViewModel: ViewModel() {

    private val faker = Faker()

    private val _state = MutableStateFlow(TodoItemState(
        faker.artist.names(),
        faker.address.fullAddress(),
    ))
    val state = _state.asStateFlow()


    fun onAction(action: TodoItemAction) {
        when (action) {
            TodoItemAction.OnCheck -> {
                _state.update { it.copy(checked = it.checked.not()) }
            }
        }
    }

}