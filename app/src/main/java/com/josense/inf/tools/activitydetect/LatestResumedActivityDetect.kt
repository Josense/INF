package com.josense.inf.tools.activitydetect

import android.Manifest
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import androidx.annotation.RequiresPermission

data class ForegroundActivity(
    val packageName: String,
    val className: String,
)

fun getLatestResumedActivity(context: Context): ForegroundActivity? {
    val usageStats = context.getSystemService(UsageStatsManager::class.java)
    val end = System.currentTimeMillis()
    val events = usageStats.queryEvents(end - 60_000, end)
    val event = UsageEvents.Event()

    var latest: ForegroundActivity? = null

    while (events.hasNextEvent()) {
        events.getNextEvent(event)

        if (event.eventType == UsageEvents.Event.ACTIVITY_RESUMED) {
            val packageName = event.packageName ?: continue
            val className = event.className ?: continue
            latest = ForegroundActivity(packageName, className)
        }
    }

    return latest

}