package com.josense.inf.topic.concurrency.coroutine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import kotlin.coroutines.coroutineContext
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun CoroutineCancellationExperimentDemo() {
    var isUploading by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isUploading) {
            CircularProgressIndicator()
            Button(onClick = {
                scope.coroutineContext.cancelChildren()
                isUploading = false
            }) {
                Text(text = "Cancel Upload")
            }
        } else {
            Button(onClick = {
                scope.launch {
                    isUploading = true
                    RemoteService.uploadFile()
                    isUploading = false
                }
            }) {
                Text(text = "Upload File")
            }
        }
    }
}

object RemoteService {
    suspend fun uploadFile() {
        val chunks = List(400) { it }
        println("Uploading File")
        var index = 0
        while (index < chunks.size) {
            try {
                // Simulate uploading a single chunk of data
                delay(5.milliseconds)
                index++
                println("Progress: ${index * 100 / chunks.size}%")
            } catch (e: Exception) {
                println("Error uploading file: ${e.message}")
                currentCoroutineContext().ensureActive()
            }
        }
        println("Upload Complete")
    }
}