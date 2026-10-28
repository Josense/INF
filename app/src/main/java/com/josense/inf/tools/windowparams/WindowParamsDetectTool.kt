package com.josense.inf.tools.windowparams

import com.josense.inf.data.PageType
import com.josense.inf.data.tools.Tool
import kotlin.jvm.java

val windowParamsDetectTool = Tool(
    id = "tool_window_params_detect",
    name = "屏幕参数检测",
    description = "实时检测当前屏幕的窗口相关参数",
    pageType = PageType.Activity,
    activity = WindowParamsDetectActivity::class.java,
)