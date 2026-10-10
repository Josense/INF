package com.josense.inf.topic.viewmodel.internals

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

fun Context.findActivity(): Activity? {
    var ctx = this
    while (ctx is ContextWrapper) {
        if (ctx is Activity) return ctx
        ctx = ctx.baseContext
    }
    return null
}

class MiniSimulatedViewModelProvider(
    private val viewModelStore: com.josense.inf.topic.viewmodel.internals.MiniSimulatedViewModelStore,
    private val viewModelFactory: com.josense.inf.topic.viewmodel.internals.MiniSimulatedViewModelFactory = _root_ide_package_.com.josense.inf.topic.viewmodel.internals.DefaultMiniSimulatedViewModelFactory()
) {
    fun <T : com.josense.inf.topic.viewmodel.internals.MiniSimulatedViewModel> getViewModel(modelClass: Class<T>): T {
        return getViewModel(modelClass, viewModelFactory)
    }

    fun <T : com.josense.inf.topic.viewmodel.internals.MiniSimulatedViewModel> getViewModel(
        modelClass: Class<T>,
        factory: com.josense.inf.topic.viewmodel.internals.MiniSimulatedViewModelFactory
    ): T {
        val existing = viewModelStore.get(modelClass)
        if (existing != null && modelClass.isInstance(existing)) {
            return existing
        }
        val viewModel = factory.create(modelClass)
        viewModelStore.put(viewModel)
        return viewModel
    }
}

/** 全局或模块内管理的 NavBackStackEntry -> MiniSimulatedViewModelStore 映射表 */
private val navEntryStores = HashMap<String, com.josense.inf.topic.viewmodel.internals.MiniSimulatedViewModelStore>()

class NavEntryMiniViewModelStoreOwner(
    override val miniViewModelStore: com.josense.inf.topic.viewmodel.internals.MiniSimulatedViewModelStore
) : com.josense.inf.topic.viewmodel.internals.MiniSimulatedViewModelStoreOwner

/**
 * 将 [MiniSimulatedViewModelStore] 绑定到 [NavBackStackEntry] 的真实 [Lifecycle] 上。
 */
fun NavBackStackEntry.getOrCreateMiniViewModelStoreOwner(activity: Activity? = null): MiniSimulatedViewModelStoreOwner {
    val store = navEntryStores.getOrPut(id) {
        val newStore = MiniSimulatedViewModelStore()
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                println("navEntry: $id, event:${event.name}")
                if (event == Lifecycle.Event.ON_DESTROY) {

                    // ⚠️⚠️⚠️ 这里能真正验证的前提是 MainActivity 没有配置 android:configChanges 属性。
                    if (activity?.isChangingConfigurations == true) {
                        // 屏幕旋转中！保留 ViewModel，不进行 clear
                        println("Screen rotating (isChangingConfigurations=true), preserving ViewModelStore for $id")
                        return
                    }
                    newStore.clear()
                    navEntryStores.remove(id)
                    lifecycle.removeObserver(this)
                }
            }
        })
        newStore
    }
    return NavEntryMiniViewModelStoreOwner(store)
}

/**
 * 从 [storeOwner] 中获取或创建 [MiniSimulatedViewModel]。
 */
@Composable
inline fun <reified T : MiniSimulatedViewModel> miniViewModel(
    storeOwner: MiniSimulatedViewModelStoreOwner,
    factory: MiniSimulatedViewModelFactory = DefaultMiniSimulatedViewModelFactory()
): T {
    val provider = remember(storeOwner) {
        MiniSimulatedViewModelProvider(storeOwner.miniViewModelStore, factory)
    }
    return provider.getViewModel(T::class.java, factory)
}

/**
 * 在 Compose 中，直接通过当前 [NavBackStackEntry] 获取自身的 [MiniSimulatedViewModel]。
 */
@Composable
inline fun <reified T : MiniSimulatedViewModel> NavBackStackEntry.miniViewModel(
    factory: MiniSimulatedViewModelFactory = DefaultMiniSimulatedViewModelFactory()
): T {
    val context = LocalContext.current
    val activity = remember(context) { context.findActivity() }
    return miniViewModel(getOrCreateMiniViewModelStoreOwner(activity), factory)
}

/**
 * 跨页面共享 [MiniSimulatedViewModel]：获取父级 NavGraph 作用域下的 ViewModel。
 */
@Composable
inline fun <reified T : MiniSimulatedViewModel> NavBackStackEntry.sharedMiniViewModel(
    navController: NavController,
    factory: MiniSimulatedViewModelFactory = DefaultMiniSimulatedViewModelFactory()
): T {
    val context = LocalContext.current
    val activity = remember(context) { context.findActivity() }
    val navGraphRoute = destination.parent?.route ?: return miniViewModel(factory)
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return parentEntry.miniViewModel(factory)
}