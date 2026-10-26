package com.josense.inf.topic.thirdpartylibrary.enrichedmarkdown

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.josense.inf.data.experiment.Experiment
import com.josense.inf.data.experiment.ExperimentTopic
import com.swmansion.enriched.markdown.compose.EnrichedMarkdownText


val EnrichedMarkdownExperiment = Experiment(
    id = "third_party_enriched_markdown_text",
    name = "Enriched-Markdown",
    icon = EnrichedMarkdown,
    description = "三方Markdown开源库实验",
    topic = ExperimentTopic.ThirdPartyLibrary,
    page = ::EnrichedMarkdownExperimentPage
)

@Composable
fun EnrichedMarkdownExperimentPage() {
    val uriHandler = LocalUriHandler.current
    val scrollState = rememberScrollState()
    EnrichedMarkdownText(
        markdown = markdownTestData,
        modifier = Modifier.padding(16.dp)
            .verticalScroll(scrollState),
        onLinkClick = { url ->
            uriHandler.openUri(url)
        }
    )
}


