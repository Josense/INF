package com.josense.inf.topic.compose.state.mvidemo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.josense.inf.topic.compose.tools.recomposeHighlighter

@Composable
fun TodoItemRoot() {
    val viewModel = viewModel<TodoItemViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    TodoItemScreen(state, viewModel::onAction)
}


@Composable
fun TodoItemScreen(
    state: TodoItemState,
    onAction: (TodoItemAction) -> Unit,
) {
    Row(
        modifier = Modifier.padding(16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.secondary)

    ) {
        Column(
            modifier = Modifier.padding(16.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = state.title,
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.titleMedium,
                textDecoration = if (state.checked) {
                    TextDecoration.LineThrough
                } else null,
            )
            Text(
                text = state.description,
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodyMedium,
                textDecoration = if (state.checked) {
                    TextDecoration.LineThrough
                } else null,
            )
        }
        Checkbox(
            checked = state.checked,
            onCheckedChange = {
                onAction(TodoItemAction.OnCheck)
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}