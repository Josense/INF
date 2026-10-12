package com.josense.inf.tools.activitydetect

import android.app.AppOpsManager
import android.content.Context
import android.os.Process

fun hasUsageAccess(context: Context): Boolean {
    val appOps = context.getSystemService(AppOpsManager::class.java)
    val opString = AppOpsManager.OPSTR_GET_USAGE_STATS
    val mode = appOps.checkOpNoThrow(
        opString, Process.myUid(), context.packageName
    )
    return mode == AppOpsManager.MODE_ALLOWED
}