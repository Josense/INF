package com.josense.inf.topic.uirender.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

class AnimatedCircleView(
    private val context: Context,
    private val attributeSet: AttributeSet? = null,
    private val defStyleAttr: Int = 0,
    private val defStyleRes: Int = 0
): View(context, attributeSet, defStyleAttr, defStyleRes) {

    companion object {
        private const val TAG = "AnimatedCircleView"
    }

    private val radius = 100
    private val diameter
        get() = radius * 2

    private val paint = Paint().apply {
        color = Color.RED
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.d(TAG, "onAttachedToWindow")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d(TAG, "onMeasure, widthMeasureSpec: $widthMeasureSpec, heightMeasureSpec: $heightMeasureSpec")
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        setMeasuredDimension(diameter.coerceAtLeast(width), diameter.coerceAtLeast(height))
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.d(TAG, "onSizeChanged, w: $w, h: $h, oldw: $oldw, oldh: $oldh")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.d(TAG, "onLayout, changed: $changed, left: $left, top: $top, right: $right, bottom: $bottom")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.d(TAG, "onDraw")
        canvas.drawCircle(width / 2f, height / 2f, radius.toFloat(), paint)
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.d(TAG, "onDetachedFromWindow")
    }
}