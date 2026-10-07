package com.josense.inf.data

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import com.josense.inf.ui.components.EmptyScreen

enum class ExperimentTopic(val topicName: String) {
    Example("Example Topic")
    ;
}

val ExampleExperiment = Experiment(
    id = "example",
    name = "Example Experiment",
    topic = ExperimentTopic.Example,
    page = {
        EmptyScreen("Example Experiment")
    }
)
val ExperimentsData: Map<String, Map<String, Experiment>> = mapOf(
    ExperimentTopic.Example.topicName to mapOf(
        ExampleExperiment.id to ExampleExperiment
    )
)

data class Experiment(
    val name: String,
    val id: String,
    val description: String = "",
    val topic: ExperimentTopic,
    var page: @Composable () -> Unit
) {

    fun launch(context: Context, targetActivityClass: Class<*>) {
        val intent = Intent(context, targetActivityClass).apply {
            putExtra(LAUNCH_PARAM_EXPERIMENT_ID, id)
            putExtra(LAUNCH_PARAM_TOPIC_NAME, topic.topicName)
        }
        context.startActivity(intent)
    }

    companion object {
        const val LAUNCH_PARAM_EXPERIMENT_ID = "experiment_id"
        const val LAUNCH_PARAM_TOPIC_NAME = "topic_name"
    }
}

fun parseExperiment(intent: Intent): Experiment? {
    val topicName = intent.getStringExtra(Experiment.LAUNCH_PARAM_TOPIC_NAME)
    val experimentId = intent.getStringExtra(Experiment.LAUNCH_PARAM_EXPERIMENT_ID)
    return ExperimentsData[topicName]?.get(experimentId)
}


