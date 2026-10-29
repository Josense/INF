package com.josense.inf.data.experiment

import android.os.Build
import com.josense.inf.topic.compose.basiclayout.ComposeBasicLayoutExperiments
import com.josense.inf.topic.compose.layout.LayoutInternalsExperiments
import com.josense.inf.topic.compose.modifier.ComposeModifierExperiments
import com.josense.inf.topic.compose.state.ComposeStateManageExperiments
import com.josense.inf.topic.crossplatform.rn.ReactNativeExperiment
import com.josense.inf.topic.ipc.broadcast.ImplicitBroadcastsExperiment
import com.josense.inf.topic.thirdpartylibrary.enrichedmarkdown.EnrichedMarkdownExperiment
import com.josense.inf.topic.uirender.custom_compose.CustomComposeExperiment
import com.josense.inf.topic.uirender.custom_view.CustomViewExperiment
import com.josense.inf.topic.viewmodel.internals.ViewModelInternalsExperiment
import com.josense.inf.topic.viewmodel.lifecycles.ViewModelLifecycleExperiment
import com.josense.inf.topic.viewmodel.savedstatehandle.ViewModelSavedStateHandleExperiment
import kotlin.to


val ExperimentsData: Map<String, Map<String, Experiment>> = mapOf(
    ExperimentTopic.Compose.topicName to mutableMapOf<String, Experiment>().apply {
        buildList {
            // 基本布局
            addAll(ComposeBasicLayoutExperiments)
            // 状态管理
            addAll(ComposeStateManageExperiments)
            // Modifier
            addAll(ComposeModifierExperiments)
            // 布局原理
            addAll(LayoutInternalsExperiments)
        }.reversed().forEach { this[it.id] = it }
    },
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
        ReactNativeExperiment.id to ReactNativeExperiment,
    ),
    ExperimentTopic.ThirdPartyLibrary.topicName to mapOf(
        EnrichedMarkdownExperiment.id to EnrichedMarkdownExperiment,
    ),
)




