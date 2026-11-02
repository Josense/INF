package com.josense.inf.topic.concurrency.coroutine

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.allowHardware
import coil3.toBitmap
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.measureTime

@Composable
fun CoroutineBasicExperimentDemo() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item { BirdChirpTask() }
        item { PhotoPrimaryColorPick() }
    }
}

@Composable
fun PhotoPrimaryColorPick() {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var color by remember { mutableStateOf(Color.Transparent) }
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )
    val context = LocalContext.current
    LaunchedEffect(selectedImageUri) {
        launch(Dispatchers.IO) {
            Log.d("PhotoPrimaryColorPick", "analyze color in ${Thread.currentThread().name}")
            var bitmap: Bitmap? = null
            val cost1 = measureTime {
                val request = ImageRequest.Builder(context)
                    .data(selectedImageUri)
                    .allowHardware(false)
                    .build()
                bitmap = ImageLoader(context).execute(request).image?.toBitmap()

            }

            withContext(Dispatchers.Default) {
                val cost2 = measureTime {
                    if (bitmap != null) {
                        Palette.from(bitmap).generate().dominantSwatch?.rgb?.let { color = Color(it) }
                    }
                }
                Log.d("PhotoPrimaryColorPick", "vaildUri:${selectedImageUri != null} cost1:$cost1, cost2:$cost2")
            }
        }
    }

    Column {
        Button(onClick = {
            singlePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }) {
            Text("选择照片")
        }

        selectedImageUri?.let { uri ->
            Box(
                modifier = Modifier.size(200.dp)
                    .background(color)
            ) {
                AsyncImage(model = uri, contentDescription = "")
            }
        }
    }
}

@Composable
fun BirdChirpTask() {
    var bird by remember { mutableStateOf<Int?>(null) }

    val bird1 = "https://picsum.photos/seed/4/600"
    val bird2 = "https://picsum.photos/seed/5/600"
    val bird3 = "https://picsum.photos/seed/6/600"

    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = { bird = 0 }) {
                Text(text = "Coo")
            }
            Button(onClick = { bird = 1 }) {
                Text(text = "Caw")
            }
            Button(onClick = { bird = 2 }) {
                Text(text = "Chirp")
            }
            Button(onClick = { bird = null }) {
                Text(text = "Stop")
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth()
                .height(300.dp)
                .padding(8.dp)
                .border(1.dp, Color.Gray)

        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = if (bird == 0) bird1 else if (bird == 1) bird2 else if (bird == 2) bird3 else "",
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize()
                )
            }
            if (bird == 0) BirdChirpOutput("Coo", "Tweety", 500L.milliseconds)
            else if (bird == 1) BirdChirpOutput("Caw", "Zazu", 1000L.milliseconds)
            else if (bird == 2) BirdChirpOutput("Chirp", "Woodstock", 1500L.milliseconds)
        }
    }
}

@Composable
fun BirdChirpOutput(
    bird: String,
    name: String,
    chirpInterval: Duration,
) {
    var chirp by remember { mutableStateOf("$bird: ") }
    LaunchedEffect(true) {
        withContext(CoroutineName(name)) {
            chirp += "${coroutineContext[CoroutineName]?.name} "
            while (true) {
                delay(chirpInterval)
                chirp += "tweet "
            }
        }
    }
    Text(
        text = chirp,
        modifier = Modifier.background(Color.Transparent),
        color = Color.Green
    )
}