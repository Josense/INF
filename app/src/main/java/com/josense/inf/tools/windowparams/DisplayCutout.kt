package com.josense.inf.tools.windowparams

import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.RectF
import android.os.Build
import android.view.DisplayCutout
import android.view.ViewTreeObserver
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.DpRect

/** Window-coordinate bounds in dp; API 31+ prefers the actual cutout path. */
@Composable
fun rememberDisplayCutoutDpBounds(): List<DpRect> {
    val view = LocalView.current
    val density = LocalDensity.current
    var pixelBounds by remember(view) { mutableStateOf(emptyList<RectF>()) }

    DisposableEffect(view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            var previousCutout: DisplayCutout? = null
            var initialized = false
            fun updateBounds() {
                val cutout = view.rootWindowInsets?.displayCutout
                if (!initialized || cutout != previousCutout) {
                    initialized = true
                    previousCutout = cutout
                    pixelBounds = cutout?.let(::readCutoutBounds).orEmpty()
                }
            }

            // Observe after Insets dispatch without replacing Compose's Insets listener.
            val observer = view.viewTreeObserver
            val listener = ViewTreeObserver.OnPreDrawListener {
                updateBounds()
                true
            }
            observer.addOnPreDrawListener(listener)
            updateBounds()
            onDispose {
                if (observer.isAlive) {
                    observer.removeOnPreDrawListener(listener)
                } else {
                    view.viewTreeObserver.removeOnPreDrawListener(listener)
                }
            }
        } else {
            onDispose { }
        }
    }

    return remember(pixelBounds, density) {
        with(density) {
            pixelBounds.map { rect ->
                DpRect(
                    left = rect.left.toDp(),
                    top = rect.top.toDp(),
                    right = rect.right.toDp(),
                    bottom = rect.bottom.toDp()
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
private fun readCutoutBounds(cutout: DisplayCutout): List<RectF> {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val path = cutout.cutoutPath
        if (path != null && !path.isEmpty) {
            val bounds = mutableListOf<RectF>()
            val measure = PathMeasure(path, false)
            // Keep separate holes separate instead of enclosing all holes in one rectangle.
            do {
                val contour = Path()
                if (measure.getSegment(0f, measure.length, contour, true)) {
                    val rect = RectF()
                    contour.computeBounds(rect, true)
                    if (!rect.isEmpty) bounds.add(rect)
                }
            } while (measure.nextContour())
            if (bounds.isNotEmpty()) return bounds
        }
    }
    return cutout.boundingRects.map { RectF(it) }
}
