package com.josense.inf.topic.thirdpartylibrary.enrichedmarkdown

val markdownTestData = """
    这是一篇检查全站正文样式的示例文档，无需指定 `layout`。请在桌面、手机以及明暗主题下观察标题层级、段落节奏、表格和代码的显示。

    中文与 English 混排时，保持自然的行距与标点。Android 的 `Activity` 生命周期、Jetpack Compose 和 Kotlin 都是技术文章中常见的内容。**这是一段加粗文字**，*这是一段强调文字*，~~这是一段删除文字~~。

    第二段正文用于检查段间距。正文应限制阅读宽度；长链接应自动换行，宽代码块和表格应在各自区域内滚动。

    ## 二级标题：链接与行内元素

    访问 [Astro 官方文档](https://docs.astro.build/)，或跳转到[代码示例](#代码示例)。键盘操作示例：按 <kbd>Ctrl</kbd> + <kbd>K</kbd> 打开搜索。

    长链接测试：[https://example.com/documentation/android/application-startup-process/very-long-path-without-spaces-for-responsive-typesetting](https://example.com/documentation/android/application-startup-process/very-long-path-without-spaces-for-responsive-typesetting)。

    ### 三级标题：列表

    - 准备 Android 开发环境。
    - 阅读应用启动流程。
      - 理解进程创建。
      - 理解主线程消息循环。
        - 检查嵌套列表的缩进和间距。
    - 编写并运行示例。

    1. 打开项目。
    2. 执行构建。
    3. 在设备中检查结果。

    - [x] 自动应用 Markdown 布局
    - [x] 统一正文排版
    - [ ] 在手机中检查横向滚动

    #### 四级标题：引用

    > 好的排版让读者专注于内容。
    >
    > 引用中也可以使用 **强调**、`行内代码` 和 [链接](https://docs.astro.build/)。
    >
    > > 这是一段嵌套引用，用于检查背景、缩进与边框。

    ##### 五级标题

    较深层级仍应保持可辨识的字重与段落间距。

    ###### 六级标题

    技术文档尽量使用清晰、不过度嵌套的章节结构。

    ## 代码示例

    行内代码示例：调用 `application.onCreate()`，并检查 `savedInstanceState != null`。

    ```kotlin
    class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                Text("你好，MakingHome！")
            }
        }
    }
    ```

    ```sh
    npm run build
    cd src
    echo "检查 shell 内置命令高亮"
    ```

    ```json
    {
      "name": "MakingHome",
      "enabled": true,
      "count": 42
    }
    ```

    ```html
    <!-- 检查标签、属性和实体的颜色 -->
    <a href="/notes/" class="note">手记 &amp; Notes</a>
    ```

    ```javascript
    // 检查注释、关键字、函数、字符串和数字
    function greet(name) {
        const message = `Hello, ${'$'}{name}`;
        return { message, count: 42 };
    }
    ```

    ```text
    这是一行刻意保留的长代码：application.startActivity(Intent(context, MainActivity::class.java).apply { putExtra("message", "Check horizontal scrolling without stretching the entire page") })
    ```

    ## 表格

    | 元素 | 用途 | 检查要点 |
    | :--- | :---: | ---: |
    | 标题 | 章节结构 | 字号与间距 |
    | 行内代码 | API 名称 | 背景与字体 |
    | 引用 | 补充说明 | 边框与缩进 |

    以下宽表格用于检查移动端滚动：

    | 阶段 | 触发入口 | 所属进程 | 主要组件 | 预期结果 | 较长的诊断标识 |
    | --- | --- | --- | --- | --- | --- |
    | 冷启动 | Launcher 点击 | system_server | ActivityTaskManager | 创建应用进程 | android.application.startup.cold.process.created |
    | 初始化 | 进程就绪 | 应用进程 | ActivityThread | 创建 Application | android.application.startup.application.initialized |

    ## 图片与说明

    <figure>
        <img src="/favicon.svg" alt="MakingHome 网站图标" width="96" height="96" />
        <figcaption>图 1：本地图片及说明文字，使用现有网站图标。</figcaption>
    </figure>

    ---

    ## 脚注

    Markdown 的脚注可以补充资料来源，不打断主要叙述。[^note]

    [^note]: 检查脚注字号、链接颜色以及返回正文的链接是否清晰可见。
""".trimIndent()