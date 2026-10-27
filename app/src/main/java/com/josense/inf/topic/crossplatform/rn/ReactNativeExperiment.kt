package com.josense.inf.topic.crossplatform.rn

import com.josense.inf.data.PageType
import com.josense.inf.data.experiment.Experiment
import com.josense.inf.data.experiment.ExperimentTopic

val ReactNativeExperiment = Experiment(
    id = "cross_platform_react_native",
    name = "React Native",
    icon = ReactNativeIcon,
    description = "独立产物集成测试",
    topic = ExperimentTopic.CrossPlatform,
    pageType = PageType.Activity,
    activity = INFReactActivity::class.java,
)