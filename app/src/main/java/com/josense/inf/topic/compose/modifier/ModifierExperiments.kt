package com.josense.inf.topic.compose.modifier

import androidx.compose.foundation.interaction.FocusInteraction
import com.josense.inf.data.experiment.Experiment
import com.josense.inf.data.experiment.ExperimentTopic
import com.josense.inf.topic.compose.basiclayout.ComposeLogoIcon

val ShapeModifierExperiment = Experiment(
    id = "compose_modifier_shape",
    name = "Clip & Shape",
    description = "Compose Modifier 使用",
    icon = ComposeLogoIcon,
    page = ::ShapeDemo,
    topic = ExperimentTopic.Compose,
)

val OffsetModifierExperiment = Experiment(
    id = "compose_modifier_offset",
    name = "Offset",
    description = "Compose Modifier 使用",
    icon = ComposeLogoIcon,
    page = ::OffsetDemo,
    topic = ExperimentTopic.Compose,
)

val ClickableModifierExperiment = Experiment(
    id = "compose_modifier_clickable",
    name = "Clickable",
    description = "Compose Modifier 使用",
    icon = ComposeLogoIcon,
    page = ::ClickableDemo,
    topic = ExperimentTopic.Compose,
)

val FocusModifierExperiment = Experiment(
    id = "compose_modifier_focus",
    name = "Focus",
    description = "Compose Modifier 使用",
    icon = ComposeLogoIcon,
    page = ::FocusDemo,
    topic = ExperimentTopic.Compose,
)

val DraggableModifierExperiment = Experiment(
    id = "compose_modifier_draggable",
    name = "Draggable",
    description = "Compose Modifier 使用",
    icon = ComposeLogoIcon,
    page = ::DraggableDemo,
    topic = ExperimentTopic.Compose,
)

val ComposeModifierExperiments = listOf(
    ShapeModifierExperiment,
    OffsetModifierExperiment,
    ClickableModifierExperiment,
    FocusModifierExperiment,
    DraggableModifierExperiment,
)