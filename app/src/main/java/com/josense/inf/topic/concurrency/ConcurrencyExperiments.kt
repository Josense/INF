package com.josense.inf.topic.concurrency

import com.josense.inf.data.experiment.Experiment
import com.josense.inf.data.experiment.ExperimentTopic
import com.josense.inf.topic.concurrency.coroutine.CoroutineBasicExperimentDemo
import com.josense.inf.topic.concurrency.coroutine.KotlinIcon


val CoroutineBasicExperiment = Experiment(
    id = "concurrency_coroutine_basic_",
    name = "协程基础",
    description = "Android 中的并发",
    icon = KotlinIcon,
    page = ::CoroutineBasicExperimentDemo,
    topic = ExperimentTopic.Concurrency,
)

val ConcurrencyExperiments = listOf(
    CoroutineBasicExperiment
)