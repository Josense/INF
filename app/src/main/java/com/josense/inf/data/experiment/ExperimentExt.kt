package com.josense.inf.data.experiment

import android.content.Intent
import kotlin.collections.get

fun parseExperiment(intent: Intent): Experiment? {
    val topicName = intent.getStringExtra(Experiment.LAUNCH_PARAM_TOPIC_NAME)
    val experimentId = intent.getStringExtra(Experiment.LAUNCH_PARAM_EXPERIMENT_ID)
    return ExperimentsData[topicName]?.get(experimentId)
}