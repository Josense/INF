package com.josense.inf.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.josense.inf.R

@Composable
fun ListItemComponent(
    icon: ImageVector,
    name: String,
    description: String,
    emptyDescription: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = name,
                modifier = Modifier.padding(12.dp),
                tint = MaterialTheme.colorScheme.tertiary,
            )
            Column {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge,
                )


                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = description.takeIf { it.isNotBlank() }
                        ?: emptyDescription,
                    style = MaterialTheme.typography.bodySmall,
                )

            }
        }
    }
}