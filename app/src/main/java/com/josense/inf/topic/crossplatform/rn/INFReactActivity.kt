package com.josense.inf.topic.crossplatform.rn

import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import com.callstack.reactnativebrownfield.ReactNativeFragment
import com.infreact.ReactNativeHostManager

class INFReactActivity: FragmentActivity() {
    companion object {
        private const val CONTAINER_ID = 0x00F001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ReactNativeHostManager.initialize(application)
        super.onCreate(savedInstanceState)
        val container = FrameLayout(this).apply {
            id = CONTAINER_ID
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
        }
        setContentView(container)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    CONTAINER_ID,
                    ReactNativeFragment.createReactNativeFragment("INFReact")
                )
                .commit()
        }
    }
}