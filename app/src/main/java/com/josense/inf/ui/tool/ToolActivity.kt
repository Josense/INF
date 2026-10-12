package com.josense.inf.ui.tool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.josense.inf.data.experiment.Experiment
import com.josense.inf.data.experiment.parseExperiment
import com.josense.inf.data.tools.Tool.Companion.LAUNCH_PARAM_TOOL_ID
import com.josense.inf.data.tools.parseTool
import com.josense.inf.ui.components.ContentWithEmptyStateComponent
import com.josense.inf.ui.components.EmptyScreen
import com.josense.inf.ui.components.TopAppBarComponent
import com.josense.inf.ui.theme.INFTheme

class ToolActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val tool = parseTool(intent)
        setContent {
            INFTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        if (tool != null) {
                            TopAppBarComponent(tool.name, tool.description) {
                                this@ToolActivity.finish()
                            }
                        }
                    }
                ) { innerPadding ->
                    val toolId = intent.getStringExtra(LAUNCH_PARAM_TOOL_ID)
                    ContentWithEmptyStateComponent(
                        innerPadding = innerPadding,
                        emptyStateDescription = "Tool not found for id: $toolId",
                        isEmptyState = {
                            tool?.page == null
                        },
                        content = tool?.page ?: {}
                    )
                }
            }
        }
    }
}