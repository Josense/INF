package com.josense.inf.ui.page.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.josense.inf.R
import com.josense.inf.data.tools.tools
import com.josense.inf.ui.components.ListItemComponent

@Composable
fun ToolPage() {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(tools.size) { index ->
            val tool = tools.entries.elementAt(index).value
            ListItemComponent(
                icon = tool.icon,
                name = tool.name,
                description = tool.description,
                emptyDescription = stringResource(R.string.tools_no_description),
                onClick = { tool.launch(context) }
            )

        }
    }
}