package com.josense.inf.topic.compose.basiclayout


import com.josense.inf.data.experiment.Experiment
import com.josense.inf.data.experiment.ExperimentTopic

val ComposeBasicLayoutBoxExperiment = Experiment(
    id = "compose_basic_layout_box",
    name = "Box",
    description = "Compose 基本布局实验",
    icon = ComposeLogoIcon,
    page = ::BoxDemo,
    topic = ExperimentTopic.Compose,
)

val ComposeBasicLayoutRowColumnExperiment = Experiment(
    id = "compose_basic_layout_row_column",
    name = "Row & Column",
    description = "Compose 基本布局实验",
    icon = ComposeLogoIcon,
    page = ::RowColumnDemo,
    topic = ExperimentTopic.Compose,
)

val ComposeBasicLayoutFlowExperiment = Experiment(
    id = "compose_basic_layout_flow",
    name = "Flow",
    description = "Compose 基本布局实验",
    icon = ComposeLogoIcon,
    page = ::FlowDemo,
    topic = ExperimentTopic.Compose,
)

val ComposeBasicLayoutLazyListExperiment = Experiment(
    id = "compose_basic_layout_lazy_list",
    name = "LazyList",
    description = "Compose 基本布局实验",
    icon = ComposeLogoIcon,
    page = ::LazyListDemo,
    topic = ExperimentTopic.Compose
)

val ComposeBasicLayoutLazyGridExperiment = Experiment(
    id = "compose_basic_layout_lazy_grid",
    name = "LazyGrid",
    description = "Compose 基本布局实验",
    icon = ComposeLogoIcon,
    page = ::LazyGridDemo,
    topic = ExperimentTopic.Compose
)

val ComposeBasicLayoutScaffoldExperiment = Experiment(
    id = "compose_basic_layout_scaffold",
    name = "Scaffold",
    description = "Compose 基本布局实验",
    icon = ComposeLogoIcon,
    page = ::ScaffoldDemo,
    topic = ExperimentTopic.Compose
)

val ComposeBasicLayoutExperiments = listOf(
    ComposeBasicLayoutBoxExperiment,
    ComposeBasicLayoutRowColumnExperiment,
    ComposeBasicLayoutFlowExperiment,
    ComposeBasicLayoutLazyListExperiment,
    ComposeBasicLayoutLazyGridExperiment,
    ComposeBasicLayoutScaffoldExperiment,
)




