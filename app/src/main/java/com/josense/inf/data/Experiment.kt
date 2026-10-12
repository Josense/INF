package com.josense.inf.data

import android.content.Context
import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Extension
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.josense.inf.ui.experiment.ExperimentActivity

data class Experiment(
    val name: String,
    val id: String,
    val description: String = "",
    val topic: ExperimentTopic,
    val icon: ImageVector = Icons.TwoTone.Extension,
    val pageType: PageType = PageType.Compose,
    val activity: Class<*>? = null,
    val page: (@Composable () -> Unit)? = null
) {

    fun launch(context: Context) {
        when {
            pageType == PageType.Activity &&  activity != null -> {
                innerLaunch(context, activity)
            }
            else -> {
                innerLaunch(context, ExperimentActivity::class.java)
            }
        }
    }

    private fun innerLaunch(context: Context, targetActivityClass: Class<*>) {
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