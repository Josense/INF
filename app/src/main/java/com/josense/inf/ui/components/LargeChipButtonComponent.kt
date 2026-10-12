package com.josense.inf.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun LargeChipButtonComponent(
    name: String,
    checked: Boolean,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit,
) {

    FilterChip(
        selected = checked,
        enabled = enabled,
        onClick = { onCheckedChange(!checked) },
        shape = RoundedCornerShape(100),
        modifier = modifier,
        label = {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 8.dp, top = 10.dp, end = 8.dp, bottom = 10.dp),
                textAlign = TextAlign.Center
            )
        }
    )
}