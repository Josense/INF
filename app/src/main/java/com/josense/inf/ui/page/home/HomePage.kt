package com.josense.inf.ui.page.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.josense.inf.R
import com.josense.inf.data.experiment.ExperimentTopic
import com.josense.inf.data.experiment.ExperimentsData
import com.josense.inf.ui.components.EmptyScreen
import com.josense.inf.ui.components.ListItemComponent
import kotlinx.coroutines.launch

@Composable
fun HomePage() {
    val context = LocalContext.current
    val pagerState = rememberPagerState(pageCount = { ExperimentTopic.entries.size })
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxSize()) {
        SecondaryScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            edgePadding = 0.dp,
        ) {
            ExperimentTopic.entries.forEachIndexed { index, topic ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text = topic.name,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { currentTopicIndex ->
            val currentTopicExperiments =
                ExperimentsData[ExperimentTopic.entries[currentTopicIndex].topicName] ?: mapOf()
            if (currentTopicExperiments.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                ) {
                    items(
                        currentTopicExperiments.entries.size
                    ) { index ->
                        val experiment = currentTopicExperiments.entries.elementAt(index).value
                        ListItemComponent(
                            icon = experiment.icon,
                            name = experiment.name,
                            description = experiment.description,
                            emptyDescription = stringResource(R.string.experiment_no_description),
                            onClick = { experiment.launch(context) }
                        )
                    }
                }
            } else {
                EmptyScreen(
                    description = stringResource(id = R.string.empty_topic)
                )
            }
        }
    }
}