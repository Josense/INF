package com.josense.inf.topic.compose.touchevent

import com.josense.inf.data.experiment.Experiment
import com.josense.inf.data.experiment.ExperimentTopic
import com.josense.inf.topic.compose.basiclayout.ComposeLogoIcon


private val ComposeTouchEventExperiment = Experiment(
    id = "compose_touch_event_basic",
    name = "触控交互基础",
    description = "Compose 触控交互",
    icon = ComposeLogoIcon,
    page = ::TouchEventBasicDemo,
    topic = ExperimentTopic.Compose,
)


val TouchEventExperiments = listOf(
    ComposeTouchEventExperiment,
)