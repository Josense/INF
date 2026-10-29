package com.josense.inf.topic.compose.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.serpro69.kfaker.Faker

@Composable
fun NestedLazyLayoutDemo() {
    val faker = Faker()
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .border(2.dp, Color.Red)
    ) {
        stickyHeader {
            Text(
                text = "Books",
                modifier = Modifier.fillMaxWidth()
                    .background(Color.Red)
                    .padding(vertical = 16.dp),
                style = MaterialTheme.typography.titleLarge,
            )
        }
        items(10) {
            Text(
                text = faker.book.title(),
                modifier = Modifier.padding(8.dp)
            )
        }

        item {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
                    .height(1000.dp)
                    .border(2.dp, Color.Blue)
            ) {
                stickyHeader {
                    Text(
                        text = "Artists",
                        modifier = Modifier.fillMaxWidth()
                            .background(Color.Blue)
                            .padding(vertical = 16.dp),
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
                items(20) {
                    Text(
                        text = faker.artist.names(),
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}