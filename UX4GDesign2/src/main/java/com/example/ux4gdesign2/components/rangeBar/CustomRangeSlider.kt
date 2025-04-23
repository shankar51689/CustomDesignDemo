package com.example.ux4gdesign2.components.rangeBar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.ux4gdesign2.R
import com.example.ux4gdesign2.components.rangeBar.CustomRangeSlider.OnRangeChangeListener
import kotlin.math.max
import kotlin.math.min

class CustomRangeSlider @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    interface OnRangeChangeListener {
        fun onValuesChanged(leftValue: Float, rightValue: Float)
    }

    var rangeChangeListener: OnRangeChangeListener? = null

    var minValue = 0f
    var maxValue = 100f

    var leftThumbValue = minValue
        set(value) {
            field = max(minValue, min(value, rightThumbValue - 1))
            rangeChangeListener?.onValuesChanged(leftThumbValue, rightThumbValue)
            invalidate()
        }

    var rightThumbValue = maxValue
        set(value) {
            field = max(leftThumbValue + 1, min(value, maxValue))
            rangeChangeListener?.onValuesChanged(leftThumbValue, rightThumbValue)
            invalidate()
        }

    var trackHeight = 10f
        set(value) {
            field = value
            invalidate()
        }

    var thumbSize = 20f
        set(value) {
            field = value
            invalidate()
        }

    // Active track (between sliders) color (user-defined)
    var trackColor = Color.RED
        set(value) {
            field = value
            activeTrackPaint.color = value
            invalidate()
        }

    // Inactive track (rest of the track) color (gray)
    var inactiveTrackColor = Color.GRAY
        set(value) {
            field = value
            inactiveTrackPaint.color = value
            invalidate()
        }

    var thumbBorderColor = Color.BLUE
        set(value) {
            field = value
            thumbBorderPaint.color = value
            invalidate()
        }

    var thumbFillColor = Color.WHITE
        set(value) {
            field = value
            thumbPaint.color = value
            invalidate()
        }

    var labelColor = Color.BLACK
        set(value) {
            field = value
            labelPaint.color = value
            invalidate()
        }

    var labelTextSize = 40f
        set(value) {
            field = value
            labelPaint.textSize = value
            invalidate()
        }

    var showLabels = true
    var labelSuffix = "%"

    // Paint for thumb circles with shadow for elevation (3D effect)
    private val thumbPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = thumbFillColor
        style = Paint.Style.FILL
        // Shadow: radius, dx, dy, shadow color
        setShadowLayer(10f, 0f, 4f, Color.DKGRAY)
    }

    private val thumbBorderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = thumbBorderColor
        style = Paint.Style.STROKE
        strokeWidth = 4f
    }

    // Active track: colored portion between the sliders
    private val activeTrackPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = trackColor
    }

    // Inactive track: rest of the track (gray)
    private val inactiveTrackPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = inactiveTrackColor
    }

    private val labelPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = labelColor
        textSize = labelTextSize
    }

    private var isLeftThumbSelected = false
    private var isRightThumbSelected = false

    init {
        // Enable shadow drawing
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomRangeSlider, 0, 0).apply {
            try {
                thumbSize = getDimension(R.styleable.CustomRangeSlider_thumbSize, 20f)
                trackColor = getColor(R.styleable.CustomRangeSlider_rs_trackColor, Color.RED)
                inactiveTrackColor = getColor(R.styleable.CustomRangeSlider_rs_inActiveTrackColor, Color.GRAY)
                thumbBorderColor = getColor(R.styleable.CustomRangeSlider_thumbBorderColor, Color.BLUE)
                thumbFillColor = getColor(R.styleable.CustomRangeSlider_thumbFillColor, Color.WHITE)
                labelColor = getColor(R.styleable.CustomRangeSlider_labelColor, Color.BLACK)
                showLabels = getBoolean(R.styleable.CustomRangeSlider_showLabels, true)
                labelSuffix = getString(R.styleable.CustomRangeSlider_labelSuffix) ?: "%"

                activeTrackPaint.color = trackColor
                inactiveTrackPaint.color = inactiveTrackColor
                thumbBorderPaint.color = thumbBorderColor
                thumbPaint.color = thumbFillColor
                labelPaint.color = labelColor
            } finally {
                recycle()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerY = height / 2f
        val trackStart = paddingLeft + thumbSize
        val trackEnd = width - paddingRight - thumbSize

        val leftX = trackStart + ((leftThumbValue - minValue) / (maxValue - minValue)) * (trackEnd - trackStart)
        val rightX = trackStart + ((rightThumbValue - minValue) / (maxValue - minValue)) * (trackEnd - trackStart)

        // Draw inactive track (gray area)
        canvas.drawRoundRect(
            RectF(trackStart, centerY - trackHeight / 2, trackEnd, centerY + trackHeight / 2),
            10f, 10f, inactiveTrackPaint
        )

        // Draw active track (colored area between thumbs)
        canvas.drawRoundRect(
            RectF(leftX, centerY - trackHeight / 2, rightX, centerY + trackHeight / 2),
            10f, 10f, activeTrackPaint
        )

        // Draw left thumb with shadow and border
        canvas.drawCircle(leftX, centerY, thumbSize, thumbPaint)
        canvas.drawCircle(leftX, centerY, thumbSize, thumbBorderPaint)

        // Draw right thumb with shadow and border
        canvas.drawCircle(rightX, centerY, thumbSize, thumbPaint)
        canvas.drawCircle(rightX, centerY, thumbSize, thumbBorderPaint)

        // Draw labels below the thumb circles
        if (showLabels) {
            val leftLabelWidth = labelPaint.measureText("${leftThumbValue.toInt()}$labelSuffix")
            val rightLabelWidth = labelPaint.measureText("${rightThumbValue.toInt()}$labelSuffix")
            // Adjust y-position so labels are always below the slider and do not overlap
            canvas.drawText(
                "${leftThumbValue.toInt()}$labelSuffix",
                leftX - leftLabelWidth / 2,
                centerY + thumbSize + 60,
                labelPaint
            )
            canvas.drawText(
                "${rightThumbValue.toInt()}$labelSuffix",
                rightX - rightLabelWidth / 2,
                centerY + thumbSize + 60,
                labelPaint
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val x = event.x
                val centerY = height / 2f
                val trackStart = paddingLeft + thumbSize
                val trackEnd = width - paddingRight - thumbSize

                val leftX = trackStart + ((leftThumbValue - minValue) / (maxValue - minValue)) * (trackEnd - trackStart)
                val rightX = trackStart + ((rightThumbValue - minValue) / (maxValue - minValue)) * (trackEnd - trackStart)

                isLeftThumbSelected = kotlin.math.abs(x - leftX) < thumbSize
                isRightThumbSelected = kotlin.math.abs(x - rightX) < thumbSize

                // If both thumbs appear to be selected (overlapping), choose one based on touch position
                if (isLeftThumbSelected && isRightThumbSelected) {
                    if (x <= (leftX + rightX) / 2) {
                        isRightThumbSelected = false
                    } else {
                        isLeftThumbSelected = false
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val x = event.x
                val trackStart = paddingLeft + thumbSize
                val trackEnd = width - paddingRight - thumbSize

                val newValue = minValue + ((x - trackStart) / (trackEnd - trackStart)) * (maxValue - minValue)

                if (isLeftThumbSelected) {
                    leftThumbValue = max(minValue, min(newValue, rightThumbValue - 1))
                } else if (isRightThumbSelected) {
                    rightThumbValue = max(leftThumbValue + 1, min(newValue, maxValue))
                }
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                isLeftThumbSelected = false
                isRightThumbSelected = false
            }
        }
        return true
    }

    fun setOnRangeChangeListener(listener: OnRangeChangeListener) {
        this.rangeChangeListener = listener
    }
}
