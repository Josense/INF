package com.josense.inf.topic.concurrency

import com.josense.inf.data.PageType
import com.josense.inf.data.experiment.Experiment
import com.josense.inf.data.experiment.ExperimentTopic
import com.josense.inf.topic.concurrency.coroutine.CoroutineBasicExperimentDemo
import com.josense.inf.topic.concurrency.coroutine.CoroutineCancellationExperiment2Demo
import com.josense.inf.topic.concurrency.coroutine.CoroutineCancellationExperimentDemo
import com.josense.inf.topic.concurrency.coroutine.KotlinIcon
import com.josense.inf.topic.concurrency.coroutine.callbackconvert.BiometricActivity


val CoroutineBasicExperiment = Experiment(
    id = "concurrency_coroutine_basic",
    name = "协程基础",
    description = "Android 中的并发",
    icon = KotlinIcon,
    page = ::CoroutineBasicExperimentDemo,
    topic = ExperimentTopic.Concurrency,
)

val CoroutineCancellationExperiment = Experiment(
    id = "concurrency_coroutine_cancellation",
    name = "协程的取消",
    description = "Android 中的并发",
    icon = KotlinIcon,
    page = ::CoroutineCancellationExperimentDemo,
    topic = ExperimentTopic.Concurrency,
)
val CoroutineCancellationExperiment2 = Experiment(
    id = "concurrency_coroutine_cancellation_2",
    name = "协程的取消 - 2",
    description = "Android 中的并发",
    icon = KotlinIcon,
    page = ::CoroutineCancellationExperiment2Demo,
    topic = ExperimentTopic.Concurrency,
)

val CoroutineCallbackConvertExperiment = Experiment(
    id = "concurrency_coroutine_callback_convert",
    name = "回调转换为挂起函数",
    description = "Android 中的并发",
    icon = KotlinIcon,
    pageType = PageType.Activity,
    activity = BiometricActivity::class.java,
    topic = ExperimentTopic.Concurrency,
)

val ConcurrencyExperiments = listOf(
    CoroutineBasicExperiment,
    CoroutineCancellationExperiment,
    CoroutineCancellationExperiment2,
    CoroutineCallbackConvertExperiment,
)