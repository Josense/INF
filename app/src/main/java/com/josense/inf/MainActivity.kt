package com.josense.inf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.josense.inf.data.Experiment
import com.josense.inf.data.ExperimentTopic
import com.josense.inf.data.ExperimentsData
import com.josense.inf.ui.experiment.ExperimentActivity
import com.josense.inf.ui.theme.INFTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            INFTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Column {
                                    Text(text = getString(R.string.app_name))
                                    Text(
                                        text = getString(R.string.app_description),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier.padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        HomePage()
                    }
                }
            }
        }
    }
}

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
                    text = { Text(text = topic.name) }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { currentTopicIndex ->
            val currentTopicExperiments = ExperimentsData[ExperimentTopic.entries[currentTopicIndex].topicName] ?: mapOf()
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(
                    currentTopicExperiments.entries.size
                ) { index ->
                    val experiment = currentTopicExperiments.entries.elementAt(index).value
                    Column(
                        modifier = Modifier.clickable {
                            experiment.launch(context, ExperimentActivity::class.java)
                        }
                    ) {

                        Text(
                            text = experiment.name,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = experiment.description,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}