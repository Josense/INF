package com.josense.inf.data.experiment

import com.josense.inf.topic.compose.basiclayout.ComposeBasicLayoutBoxExperiment
import com.josense.inf.topic.compose.basiclayout.ComposeBasicLayoutFlowExperiment
import com.josense.inf.topic.compose.basiclayout.ComposeBasicLayoutRowColumnExperiment
import com.josense.inf.topic.ipc.broadcast.ImplicitBroadcastsExperiment
import com.josense.inf.topic.uirender.custom_compose.CustomComposeExperiment
import com.josense.inf.topic.uirender.custom_view.CustomViewExperiment
import com.josense.inf.topic.viewmodel.internals.ViewModelInternalsExperiment
import com.josense.inf.topic.viewmodel.lifecycles.ViewModelLifecycleExperiment
import com.josense.inf.topic.viewmodel.savedstatehandle.ViewModelSavedStateHandleExperiment



val ExperimentsData: Map<String, Map<String, Experiment>> = mapOf(
    ExperimentTopic.Compose.topicName to mapOf(
        ComposeBasicLayoutBoxExperiment.id to ComposeBasicLayoutBoxExperiment,
        ComposeBasicLayoutRowColumnExperiment.id to ComposeBasicLayoutRowColumnExperiment,
        ComposeBasicLayoutFlowExperiment.id to ComposeBasicLayoutFlowExperiment,
    ),
    ExperimentTopic.ViewModel.topicName to mapOf(
        ViewModelLifecycleExperiment.id to ViewModelLifecycleExperiment,
        ViewModelSavedStateHandleExperiment.id to ViewModelSavedStateHandleExperiment,
        ViewModelInternalsExperiment.id to ViewModelInternalsExperiment,
    ),
    ExperimentTopic.UiSystem.topicName to mapOf(
        CustomViewExperiment.id to CustomViewExperiment,
        CustomComposeExperiment.id to CustomComposeExperiment,
    ),
    ExperimentTopic.IPC.topicName to mapOf(
        ImplicitBroadcastsExperiment.id to ImplicitBroadcastsExperiment,
    ),
    ExperimentTopic.CrossPlatform.topicName to mapOf(
    )
)




