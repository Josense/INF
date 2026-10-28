package com.josense.inf.topic.compose.state.mvidemo

sealed interface TodoItemAction {
    data object OnCheck: TodoItemAction
}