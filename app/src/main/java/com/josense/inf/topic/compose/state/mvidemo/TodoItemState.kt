package com.josense.inf.topic.compose.state.mvidemo

data class TodoItemState(
    val title: String = "",
    val description: String = "" ,
    val checked: Boolean = false,
)