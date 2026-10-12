package com.josense.inf.ui.experiment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.josense.inf.data.experiment.Experiment
import com.josense.inf.data.experiment.parseExperiment
import com.josense.inf.ui.components.ContentWithEmptyStateComponent
import com.josense.inf.ui.components.TopAppBarComponent
import com.josense.inf.ui.theme.INFTheme

class ExperimentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val experiment = parseExperiment(intent)
        setContent {
            INFTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        if (experiment != null) {
                            TopAppBarComponent(
                                pageTitle = experiment.name,
                                pageSubtitle = experiment.topic.topicName,
                                onBackClick = {
                                    this@ExperimentActivity.finish()
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    val topicName = intent.getStringExtra(Experiment.LAUNCH_PARAM_TOPIC_NAME)
                    val experimentId = intent.getStringExtra(Experiment.LAUNCH_PARAM_EXPERIMENT_ID)
                    val emptyStateDescription = "Experiment not found for id: $experimentId and topic: $topicName"
                    ContentWithEmptyStateComponent(
                        innerPadding = innerPadding,
                        emptyStateDescription = emptyStateDescription,
                        isEmptyState = {
                            experiment?.page == null
                        },
                        content = experiment?.page ?: {}
                    )
                }
            }
        }
    }
}