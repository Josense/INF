package com.josense.inf.topic.compose.basiclayout

import com.josense.inf.data.Experiment
import com.josense.inf.data.ExperimentTopic
import com.josense.inf.topic.compose.basiclayout.BoxDemo
import com.josense.inf.topic.compose.basiclayout.FlowDemo
import com.josense.inf.topic.compose.basiclayout.RowColumnDemo

val ComposeBasicLayoutBoxExperiment = Experiment(
    id = "compose_basic_layout_box",
    name = "Box",
    description = "Compose 基本布局实验",
    page = ::BoxDemo,
    topic = ExperimentTopic.Compose,
)

val ComposeBasicLayoutRowColumnExperiment = Experiment(
    id = "compose_basic_layout_row_column",
    name = "Row & Column",
    description = "Compose 基本布局实验",
    page = ::RowColumnDemo,
    topic = ExperimentTopic.Compose,
)

val ComposeBasicLayoutFlowExperiment = Experiment(
    id = "compose_basic_layout_flow",
    name = "Flow",
    description = "Compose 基本布局实验",
    page = ::FlowDemo,
    topic = ExperimentTopic.Compose,
)


