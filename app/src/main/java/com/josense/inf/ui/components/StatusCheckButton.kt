package com.josense.inf.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowOutward
import androidx.compose.material.icons.twotone.Done
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun StatusEnableCheckButton(
    checked: Boolean,
    checkedText: String,
    uncheckedText: String,
    modifier: Modifier = Modifier,
    toCheck: () -> Unit,
) {
    FilledTonalButton (
        onClick = toCheck,
        modifier = modifier,
    ) {
        val name = if (checked) checkedText else uncheckedText
        Text(
            text = name
        )
        Icon(
            imageVector = if (checked) {
                Icons.TwoTone.Done
            } else {
                Icons.TwoTone.ArrowOutward
            },
            contentDescription = name
        )
    }
}