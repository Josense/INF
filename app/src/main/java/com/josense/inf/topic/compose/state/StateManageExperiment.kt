package com.josense.inf.topic.compose.state

import com.josense.inf.data.experiment.Experiment
import com.josense.inf.data.experiment.ExperimentTopic
import com.josense.inf.topic.compose.basiclayout.ComposeLogoIcon


private val ComposeStateBasicSyntaxExperiment = Experiment(
    id = "compose_state_basic_syntax",
    name = "State & Remember",
    description = "Compose 状态管理",
    icon = ComposeLogoIcon,
    page = ::StaticBasicSyntaxDemo,
    topic = ExperimentTopic.Compose,
)

val ComposeStateManageExperiments = listOf(
    ComposeStateBasicSyntaxExperiment,
)

