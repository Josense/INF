# SDL3 接入 Android 项目：集成与编译流程

本文记录本项目采用的 SDL3 Android 接入方案：保留现有 Kotlin/Compose App，由 Gradle 管理 Android 构建，由 CMake + Android NDK 编译 SDL3 和应用原生代码，并通过独立 SDL Activity 显示 SDL 内容。

## 1. 当前工程结构

```text
项目根目录/
├── app/
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/
│       │   ├── com/josense/inf/                 # 现有 Kotlin/Compose 代码
│       │   ├── com/josense/inf/topic/crossplatform/sdl3/
│       │   │   └── SDLExperimentActivity.kt     # SDL Activity 子类
│       │   └── org/libsdl/app/                  # SDL Android Java glue
│       └── cpp/
│           ├── CMakeLists.txt
│           └── main.cpp                         # SDL_main 入口
└── third_party/
    └── SDL/                                     # 完整 SDL3 源码树
```

本工程当前 SDL 版本是 **3.4.18**（见 `third_party/SDL/include/SDL3/SDL_version.h`）。`app/build.gradle.kts` 当前使用 `compileSdk 37`、`minSdk 24`，并将 CMake 入口指向 `app/src/main/cpp/CMakeLists.txt`。

## 2. 接入步骤

### 2.1 放入 SDL 源码

将完整 SDL3 源码放到项目的 `third_party/SDL/`。不要只复制 `src/` 或 `include/`：CMake 构建还需要 SDL 自己的根 `CMakeLists.txt`、构建脚本和其他源码目录。

在该方案中，SDL 源码树作为 CMake 子项目加入，不是 Gradle 子模块，也不需要 Maven 坐标或 SDL AAR。

### 2.2 接入 SDL Java glue

SDL Android 的 Java glue 位于 SDL 源码的：

```text
third_party/SDL/android-project/app/src/main/java/org/libsdl/app/
```

本项目把该目录中的 Java 文件放在：

```text
app/src/main/java/org/libsdl/app/
```

它包含 `SDLActivity`、`SDLSurface`、`SDL` 等相互依赖的类，应作为一组接入。升级 SDL 时应从同一 SDL 版本同步更新这些 Java 文件和本地 SDL 源码。

有两种等价的接入办法：

1. 将 Java glue 复制到 `app/src/main/java/org/libsdl/app/`；或
2. 在 Gradle 的 `sourceSets.main.java` 中直接引用 SDL 模板目录。

只能选一种。若 Java 文件已复制进 `app/src/main/java`，不要再通过 `sourceSets` 引入模板路径；否则同一包下的同名类会被编译两遍，出现 `duplicate class` 错误。本项目已选择第一种办法。

### 2.3 创建自己的 SDL Activity

在应用自己的包名下创建 SDL Activity 子类，不要复制或重写 SDL 的 `SDLActivity`：

```kotlin
package com.josense.inf.topic.crossplatform.sdl3

import org.libsdl.app.SDLActivity

class SDLExperimentActivity : SDLActivity()
```

该类是应用入口与 SDL glue 之间的 Activity 壳。Compose 页面通过 Intent 启动它，SDL 页面结束后 Android 返回原页面。

在 `app/src/main/AndroidManifest.xml` 的 `<application>` 下注册：

```xml
<activity
    android:name=".topic.crossplatform.sdl3.SDLExperimentActivity"
    android:configChanges="layoutDirection|locale|grammaticalGender|fontScale|fontWeightAdjustment|orientation|uiMode|screenLayout|screenSize|smallestScreenSize|keyboard|keyboardHidden|navigation"
    android:exported="false" />
```

`configChanges` 很重要：SDL 会在创建窗口时根据窗口尺寸请求屏幕方向。如果 Android 因方向切换重建 Activity，SDL 当前模板可能将其识别为不允许的重新创建并结束应用。让 SDLActivity 自行处理这些配置变化，可以避免该问题。

从 Compose 页面启动方式与普通 Activity 相同：

```kotlin
val context = LocalContext.current

Button(onClick = {
    context.startActivity(Intent(context, SDLExperimentActivity::class.java))
}) {
    Text("打开 SDL 页面")
}
```

### 2.4 配置 Gradle 调用 CMake

在 `app/build.gradle.kts` 中指定 app 的顶层 CMake 文件：

```kotlin
android {
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
        }
    }
}
```

保留工程原有的 Compose、Kotlin、SDK 等设置。这个源码集成方式不需要在 `dependencies` 中加入 SDL Maven 依赖。

### 2.5 将 SDL 加入 CMake 并构建 `libmain.so`

`app/src/main/cpp/CMakeLists.txt`：

```cmake
cmake_minimum_required(VERSION 3.22.1)
project(app_native)

add_subdirectory(
    "${CMAKE_CURRENT_LIST_DIR}/../../../../third_party/SDL"
    "${CMAKE_CURRENT_BINARY_DIR}/SDL"
)

add_library(main SHARED
    main.cpp
)

target_link_libraries(main
    PRIVATE SDL3::SDL3
)
```

相对路径从 `app/src/main/cpp` 回到项目根目录，再进入 `third_party/SDL`。`add_subdirectory()` 将 SDL CMake 工程纳入同一轮构建；`target_link_libraries()` 声明应用原生目标依赖 SDL。

目标名 `main` 是刻意设置的：SDL Android Java glue 默认加载 `libSDL3.so` 和 `libmain.so`，并从 `libmain.so` 调用 `SDL_main()`。

### 2.6 实现 SDL 原生入口

最小入口示意：

```cpp
#include <SDL3/SDL.h>
#include <SDL3/SDL_main.h>

int SDL_main(int argc, char *argv[])
{
    SDL_Window *window = nullptr;
    SDL_Renderer *renderer = nullptr;

    if (!SDL_CreateWindowAndRenderer(
            "SDL Feature", 1280, 720, SDL_WINDOW_RESIZABLE,
            &window, &renderer)) {
        SDL_Log("SDL 初始化失败：%s", SDL_GetError());
        return 1;
    }

    bool running = true;
    SDL_Event event;
    while (running) {
        while (SDL_PollEvent(&event)) {
            if (event.type == SDL_EVENT_QUIT) {
                running = false;
            }
        }
        SDL_SetRenderDrawColor(renderer, 24, 28, 36, 255);
        SDL_RenderClear(renderer);
        SDL_RenderPresent(renderer);
    }

    SDL_DestroyRenderer(renderer);
    SDL_DestroyWindow(window);
    return 0;
}
```

SDL Android 启动的是 `SDL_main()`，不需要自行创建 Android 原生 `main()`。从 `SDL_main()` 返回会结束 SDL Activity；需要处理返回键、暂停或恢复时，要在事件循环和生命周期处理中实现对应行为。

### 2.7 屏幕方向

默认情况下 SDL 会参考窗口宽高请求方向。若要允许系统自动旋转，并尊重用户的自动旋转设置，可在 SDL 初始化前允许各方向，并将窗口设为可调整大小：

```cpp
SDL_SetHint(SDL_HINT_ORIENTATIONS,
            "LandscapeLeft LandscapeRight Portrait PortraitUpsideDown");

SDL_CreateWindowAndRenderer(
    "SDL Feature", 1280, 720, SDL_WINDOW_RESIZABLE,
    &window, &renderer);
```

SDL Android glue 在可调整大小且横竖方向都被允许时会采用 Android 的用户方向策略。系统关闭自动旋转时，设备方向仍会遵循系统设置。方向变化要与 Manifest 的 `configChanges` 配合。

### 2.8 文本渲染

当前示例用 `SDL_RenderDebugText()` 绘制 “Hello, SDL Android”，再用 `SDL_SetRenderScale()` 放大调试字体并按渲染输出尺寸居中。这个 API 使用固定的 8×8 位图字体，适合接入验证和调试；正式 UI 应考虑 SDL_ttf 或其他字体方案。

## 3. Gradle、CMake、NDK 的编译原理

### 3.1 各工具负责什么

| 工具 | 主要职责 |
|---|---|
| Gradle / Android Gradle Plugin | 管理 Android 变体和任务；编译 Kotlin/Java；合并资源与 Manifest；调用 CMake；收集 `.so` 并打包 APK。 |
| CMake | 读取 `CMakeLists.txt`；建立 SDL 与 `main` 的目标和依赖关系；为当前 Android 配置生成 Ninja 等构建规则。 |
| Android NDK | 提供 Android 目标的 Clang/LLVM 编译器、链接器、头文件、系统库和 CMake toolchain。实际编译链接由这些工具执行。 |
| SDL Java glue | 作为 Android Activity 层，加载原生库，通过 JNI 将 Activity 生命周期、Surface 和输入连接到 SDL 原生后端。 |
| SDL 原生代码与 `SDL_main()` | 创建 SDL 窗口/渲染器，运行事件循环并绘制内容。 |

因此，**Gradle 调度，CMake描述，NDK工具链执行编译和链接**。CMake 不会替代 NDK；NDK 也不会代替 Gradle 管理 Android APK。

### 3.2 一次 Debug 构建的阶段

执行：

```bash
./gradlew :app:assembleDebug
```

典型过程如下：

1. **Gradle 配置项目**：读取插件、依赖、`defaultConfig`、`externalNativeBuild` 和 build type。
2. **编译 Kotlin/Java**：编译 Compose 页面、Activity 和 `org.libsdl.app` Java glue。
3. **配置原生工程**：Android Gradle Plugin 为选中的每个 ABI 调用 CMake，并自动传入 NDK toolchain、ABI、Android API level、Debug/Release 等参数。
4. **生成构建规则**：CMake 读取 app 与 SDL 的 `CMakeLists.txt`，生成当前 ABI 的 Ninja 构建文件。
5. **编译 C/C++**：NDK 的 Clang 将 SDL 源码和 `main.cpp` 编译成 ABI 对应的目标文件。
6. **链接共享库**：链接器生成 `libSDL3.so` 和 `libmain.so`，并处理它们与 Android 系统库之间的依赖。
7. **合并原生库并打包**：Gradle 把各 ABI 的 `.so` 放进 APK 的 `lib/<abi>/` 下，同时打包字节码、资源、Manifest 等。

你此前构建日志中出现过这些任务：

```text
configureCMakeDebug[arm64-v8a]
buildCMakeDebug[arm64-v8a]
mergeDebugNativeLibs
packageDebug
assembleDebug
```

这表示 CMake 为该 ABI 配置/构建了原生库，Gradle 随后收集并打包原生输出。

### 3.3 ABI 与 APK 中的库

ABI 是 Android 原生库使用的 CPU 指令集和二进制接口。常见值包括：

- `arm64-v8a`：现代 ARM64 手机和平板；
- `armeabi-v7a`：32 位 ARM 设备；
- `x86_64`、`x86`：模拟器及少量设备。

默认情况下 Gradle/NDK 可以为多个 ABI 构建并打包库。APK 内结构类似：

```text
lib/arm64-v8a/libSDL3.so
lib/arm64-v8a/libmain.so
lib/armeabi-v7a/libSDL3.so
lib/armeabi-v7a/libmain.so
```

设备安装时 Android 只加载与设备 ABI 兼容的那一份；开发时可通过 `ndk.abiFilters` 限定要构建/打包的 ABI，以缩短构建时间或缩小包体。

### 3.4 运行时从 Activity 到 SDL

```text
Compose 页面
    │ Intent
    ▼
SDLExperimentActivity : SDLActivity
    │ SDL Java glue 创建/管理 Surface、处理生命周期和输入
    │ JNI 调用
    ▼
加载 libSDL3.so 与 libmain.so
    │
    ▼
调用 SDL_main()
    │
    ▼
SDL 事件循环与渲染器绘制到 Android Surface
```

Activity 与原生模块不是两个 APK。它们一起被打包在一个 Android App 中；SDLActivity 是 Android/Java 前端，SDL C/C++ 代码是原生后端。

## 4. 常见问题排查

### `duplicate class: org.libsdl.app...`

SDL Java glue 同时从 app 本地目录和 SDL 模板目录加入了编译。检查 `app/build.gradle.kts` 是否有额外 `sourceSets` 指向 `third_party/SDL/android-project/.../java`。保留复制版或 sourceSet 引用版之一即可。

### SDL 页面一打开就退出

先确认 `.so` 成功加载，再查看 SDL 日志是否有 `SDL main() finished`、`activity finished`。若 SDL 请求屏幕方向后 Activity 被 Android 重建，检查 SDL Activity 是否声明了 SDL 模板中的 `configChanges`。

### `Failed to open APK` 出现在厂商系统进程

判断日志的进程名。如果错误来自 `com.meizu.safe` 等厂商安全/建议服务，而不是 `com.josense.inf`，且 App 进程中没有 `FATAL EXCEPTION` 或 `Fatal signal`，它通常不是 SDL 自身的崩溃证据。继续查看应用进程的 SDL、AndroidRuntime 和 native crash 日志。

### 库加载失败

检查 APK 是否包含设备 ABI 对应的 `libSDL3.so` 和 `libmain.so`；检查应用目标名是否为 `main`；检查 SDL Java glue 与 SDL C 源码是否来自相同 SDL 版本。

## 5. 官方参考

- [SDL3 Android 构建文档](https://github.com/libsdl-org/SDL/blob/main/docs/README-android.md)
- [Android：将 Gradle 连接到 CMake/NDK 构建](https://developer.android.com/studio/projects/gradle-external-native-builds)
- [Android NDK：CMake](https://developer.android.com/ndk/guides/cmake)
- [Android NDK：ABI 指南](https://developer.android.com/ndk/guides/abis)
- [SDL_HINT_ORIENTATIONS](https://wiki.libsdl.org/SDL3/SDL_HINT_ORIENTATIONS)
