package com.josense.inf.topic.crossplatform.sdl3

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.SportsEsports
import com.josense.inf.data.PageType
import com.josense.inf.data.experiment.Experiment
import com.josense.inf.data.experiment.ExperimentTopic

val sdl3Experiment = Experiment(
    name = "SDL3 实验",
    id = "cross_platform_sdl3",
    description = "尝试使用 SDL3 来绘制一个页面",
    topic = ExperimentTopic.CrossPlatform,
    icon = Icons.TwoTone.SportsEsports,
    pageType = PageType.Activity,
    activity = SDLExperimentActivity::class.java,
)