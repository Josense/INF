package com.josense.inf.data.tools

import android.content.Context
import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Extension
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.josense.inf.data.PageType
import com.josense.inf.data.tools.Tool.Companion.LAUNCH_PARAM_TOOL_ID
import com.josense.inf.ui.tool.ToolActivity

data class Tool(
    val name: String,
    val id: String,
    val description: String = "",
    val icon: ImageVector = Icons.TwoTone.Extension,
    val pageType: PageType = PageType.Compose,
    val activity: Class<*>? = null,
    val page: (@Composable () -> Unit)? = null
) {

    fun launch(context: Context) {
        when {
            pageType == PageType.Activity &&  activity != null -> {
                innerLaunch(context, activity)
            }
            else -> {
                innerLaunch(context, ToolActivity::class.java)
            }
        }
    }

    private fun innerLaunch(context: Context, targetActivityClass: Class<*>) {
        val intent = Intent(context, targetActivityClass).apply {
            putExtra(LAUNCH_PARAM_TOOL_ID, id)
        }
        context.startActivity(intent)
    }

    companion object {
        const val LAUNCH_PARAM_TOOL_ID = "tool_id"
    }
}

fun parseTool(intent: Intent): Tool? {
    val toolId = intent.getStringExtra(LAUNCH_PARAM_TOOL_ID)
    return tools[toolId]
}