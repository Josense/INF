package com.josense.inf.ui.experiment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.josense.inf.R
import com.josense.inf.data.Experiment
import com.josense.inf.data.parseExperiment
import com.josense.inf.ui.components.EmptyScreen
import com.josense.inf.ui.theme.INFTheme

class ExperimentActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
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
                            TopAppBar(
                                title = {
                                    Column {
                                        Text(
                                            text = experiment.name,
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                        Text(
                                            text = experiment.topic.topicName,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        this@ExperimentActivity.finish()
                                    }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = stringResource(R.string.back)
                                        )
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        if (experiment != null) {
                            experiment.page()
                        } else {
                            val topicName = intent.getStringExtra(Experiment.LAUNCH_PARAM_TOPIC_NAME)
                            val experimentId = intent.getStringExtra(Experiment.LAUNCH_PARAM_EXPERIMENT_ID)
                            EmptyScreen(
                                description = "Experiment not found for id: $experimentId and topic: $topicName"
                            )
                        }
                    }
                }
            }
        }
    }
}