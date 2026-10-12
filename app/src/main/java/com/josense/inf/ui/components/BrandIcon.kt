package com.josense.inf.ui.components

import androidx.annotation.Size
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.josense.inf.R

@Composable
fun BrandIcon(
    size: Dp = 40.dp,
    description: String = stringResource(R.string.app_name)
) {
    Image(
        painter = painterResource(id = R.drawable.empty_placeholder),
        contentDescription = description,
        modifier = Modifier.size(size)
    )
}
