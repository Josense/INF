package com.josense.inf.topic.ipc.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.josense.inf.data.experiment.Experiment
import com.josense.inf.data.experiment.ExperimentTopic

val ImplicitBroadcastsExperiment = Experiment(
    id = "implicit_broadcasts",
    name = "隐式广播实验",
    description = "接收系统电量/充电状态变量广播",
    page = ::ImplicitBroadcastsExperimentPage,
    topic = ExperimentTopic.IPC,
)

@Composable
fun ImplicitBroadcastsExperimentPage() {
    var isCharging by remember { mutableStateOf(false) }
    var batteryPct by remember { mutableStateOf<Float?>(0f) }
    val context = LocalContext.current
    DisposableEffect(context) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == Intent.ACTION_BATTERY_CHANGED) {
                    intent?.let {
                        val status = it.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
                        isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING
                                || status == BatteryManager.BATTERY_STATUS_FULL

                        val level = it.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                        val scale = it.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                        batteryPct = if (scale > 0) level * 100 / scale.toFloat() else null
                    }
                } else if (intent?.action == Intent.ACTION_POWER_CONNECTED) {
                    Toast.makeText(context, "Power connected", Toast.LENGTH_SHORT).show()
                } else if (intent?.action == Intent.ACTION_POWER_DISCONNECTED) {
                    Toast.makeText(context, "Power disconnected", Toast.LENGTH_SHORT).show()
                }
            }
        }
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_BATTERY_CHANGED)
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val stickIntent = context.registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED)
            receiver.onReceive(context, stickIntent)
        }
        onDispose {
            context.unregisterReceiver(receiver)
        }
    }


    Column {
        Text(text = "isCharging: $isCharging")
        Text(text = "batteryPct: $batteryPct")
    }
}