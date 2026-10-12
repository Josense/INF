package com.josense.inf.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.josense.inf.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarComponent(
    pageTitle: String,
    pageSubtitle: String,
    onBackClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = pageTitle,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = pageSubtitle,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Sharp.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    )
}
