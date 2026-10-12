package com.josense.inf.tools.activitydetect

import com.josense.inf.data.PageType
import com.josense.inf.data.tools.Tool

val activityDetectTool = Tool(
    id = "tool_activity_detect",
    name = "当前 Activity 检测",
    description = "实时跟踪当前页面 Activity",
    pageType = PageType.Compose,
    page = ::ActivityDetectPage
)