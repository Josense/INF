package com.josense.inf.topic.compose.layout

import com.josense.inf.data.PageType
import com.josense.inf.data.experiment.Experiment
import com.josense.inf.data.experiment.ExperimentTopic
import com.josense.inf.topic.compose.basiclayout.ComposeLogoIcon
import com.josense.inf.topic.compose.basiclayout.ScaffoldDemo


val ComposeLayoutInternalsConstraintsExperiment = Experiment(
    id = "compose_layout_internals_constraints",
    name = "Constraints & Size",
    description = "Compose 布局阶段原理",
    icon = ComposeLogoIcon,
    page = ::ConstraintsDemo,
    topic = ExperimentTopic.Compose
)

val ComposeLayoutInternalsSizeAndPositionExperiment = Experiment(
    id = "compose_layout_internals_size_and_position",
    name = "Size & Position 获取",
    description = "Compose 布局阶段原理",
    icon = ComposeLogoIcon,
    pageType = PageType.Activity,
    activity = SizeAndPositionDemoActivity::class.java,
    topic = ExperimentTopic.Compose
)

val LayoutInternalsExperiments = listOf(
    ComposeLayoutInternalsConstraintsExperiment,
    ComposeLayoutInternalsSizeAndPositionExperiment,
)