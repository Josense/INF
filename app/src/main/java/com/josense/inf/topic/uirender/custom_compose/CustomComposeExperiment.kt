package com.josense.inf.topic.uirender.custom_compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.josense.inf.data.experiment.Experiment
import com.josense.inf.data.experiment.ExperimentTopic
val CustomComposeExperiment = Experiment(
    id = "custom_compose",
    name = "自定义 Compose 实验",
    description = "Composition / (Measurement + Layout) / Drawing",
    page = ::CustomComposeExperimentPage,
    topic = ExperimentTopic.UiSystem,
)

@Composable
fun CustomComposeExperimentPage() {
    Column {
        Box(modifier = Modifier.width(500.dp).height(500.dp)) {
            AnimatedCircle(modifier = Modifier.wrapContentSize())
        }

        AnimatedCircle(modifier = Modifier.wrapContentSize())
    }
}