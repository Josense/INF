package com.josense.inf.topic.uirender.custom_view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.josense.inf.data.Experiment
import com.josense.inf.data.ExperimentTopic
import com.josense.inf.topic.uirender.custom_view.CustomViewExperimentPage


val CustomViewExperiment = Experiment(
    id = "custom_view",
    name = "自定义 View 实验",
    page = ::CustomViewExperimentPage,
    topic = ExperimentTopic.UiSystem,
)

@Composable
fun CustomViewExperimentPage() {
    Column {
        Box(modifier = Modifier.width(500.dp).height(500.dp)) {
            AndroidView(factory = { context ->
                _root_ide_package_.com.josense.inf.topic.uirender.custom_view.AnimatedCircleView(
                    context = context
                )
            }, modifier = Modifier.wrapContentHeight().fillMaxWidth())
        }

        AndroidView(factory = { context ->
            _root_ide_package_.com.josense.inf.topic.uirender.custom_view.AnimatedCircleView(context = context)
        }, modifier = Modifier.wrapContentHeight().fillMaxWidth())
    }

}
