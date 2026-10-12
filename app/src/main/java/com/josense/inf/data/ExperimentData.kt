package com.josense.inf.data

import com.josense.inf.ui.components.EmptyScreen

val ExamplePageExperiment = Experiment(
    id = "example_page",
    name = "Example Page Experiment",
    topic = ExperimentTopic.Example,
    page = {
        EmptyScreen("Example Page Experiment")
    }
)

val ExampleActivityExperiment = Experiment(
    id = "example_activity",
    name = "Example Activity Experiment",
    topic = ExperimentTopic.Example,
    page = {
        EmptyScreen("Example Activity Experiment")
    }
)

val ExperimentsData: Map<String, Map<String, Experiment>> = mapOf(
    ExperimentTopic.Example.topicName to mapOf(
        ExamplePageExperiment.id to ExamplePageExperiment,
        ExampleActivityExperiment.id to ExampleActivityExperiment,
    )
)




