package com.example.ux4gdesign2.components.rangeBar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.ux4gdesign2.R
import com.google.android.material.slider.Slider
import kotlin.math.absoluteValue

class CustomRangeBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val valueText: TextView
    private val slider: Slider

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER

        // Inflate layout
        LayoutInflater.from(context).inflate(R.layout.view_custom_range, this, true)

        // Initialize views
        valueText = findViewById(R.id.valueText)
        slider = findViewById(R.id.materialSlider)

        // Setup default attributes
        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomRangeView, 0, 0).apply {
            try {
                slider.valueFrom = getFloat(R.styleable.CustomRangeView_valueFrom, 0f)
                slider.valueTo = getFloat(R.styleable.CustomRangeView_valueTo, 100f)
                slider.stepSize = getFloat(R.styleable.CustomRangeView_stepSize, 1f)
                slider.value = getFloat(R.styleable.CustomRangeView_defaultValue, 0f)
            } finally {
                recycle()
            }
        }

        // Update text position
        slider.addOnChangeListener { _, value, _ ->
            valueText.text = value.toInt().toString()
            val thumbX = getThumbXPosition(value)
            valueText.translationX = thumbX
        }
    }

    // Function to calculate thumb X position
    private fun getThumbXPosition(value: Float): Float {
        val trackWidth = width - paddingLeft - paddingRight
        val relativePos = (value - slider.valueFrom) / (slider.valueTo - slider.valueFrom)
        return paddingLeft + relativePos * trackWidth
    }

    // Public methods to access slider properties
    fun setValue(value: Float) {
        slider.value = value
    }

    fun getValue(): Float = slider.value

    fun setOnValueChangeListener(listener: (Float) -> Unit) {
        slider.addOnChangeListener { _, value, _ -> listener(value) }
    }
}

