package com.example.ux4gdesign2.components.rangeBar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.example.ux4gdesign2.R
import androidx.core.content.withStyledAttributes

class SingleThumbSlider @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    var thumbRadius = 8f.dp()
    var thumbBorderWidth = 1f.dp()
    var thumbBorderColor: Int = Color.BLUE
    var thumbFillColor: Int = Color.WHITE
    var activeTrackColor: Int = ContextCompat.getColor(context, R.color.purple_500)
    var inactiveTrackColor: Int = Color.GRAY
    var trackHeight = 4f.dp()
    var labelTextSize = 14f.sp(context)
    var labelColor = Color.BLACK

    // Paints
    private val trackPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val activeTrackPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val thumbPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val thumbStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val labelPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var progress = 0.5f
    private var thumbX = 0f
    private var centerY = 0f
    private var sliderStart = 0f
    private var sliderEnd = 0f

    init {
        attrs?.let {
            context.withStyledAttributes(it, R.styleable.SingleThumbSlider) {

                thumbRadius         = getDimension(R.styleable.SingleThumbSlider_sts_thumbRadius, thumbRadius)
                thumbBorderWidth    = getDimension(R.styleable.SingleThumbSlider_sts_thumbBorderWidth, thumbBorderWidth)
                thumbBorderColor    = getColor(R.styleable.SingleThumbSlider_sts_thumbBorderColor, thumbBorderColor)
                thumbFillColor      = getColor(R.styleable.SingleThumbSlider_sts_thumbFillColor, thumbFillColor)
                activeTrackColor    = getColor(R.styleable.SingleThumbSlider_sts_activeTrackColor, activeTrackColor)
                inactiveTrackColor  = getColor(R.styleable.SingleThumbSlider_sts_inactiveTrackColor, inactiveTrackColor)
                trackHeight         = getDimension(R.styleable.SingleThumbSlider_sts_trackHeight, trackHeight)
                labelTextSize       = getDimension(R.styleable.SingleThumbSlider_sts_labelTextSize, labelTextSize)
                labelColor          = getColor(R.styleable.SingleThumbSlider_sts_labelColor, labelColor)

            }
        }

        setLayerType(LAYER_TYPE_SOFTWARE, null)
        setupPaints()
    }

    private fun setupPaints() {
        trackPaint.color = inactiveTrackColor
        activeTrackPaint.color = activeTrackColor

        thumbPaint.apply {
            style = Paint.Style.FILL
            color = thumbFillColor
            setShadowLayer(6f.dp(), 0f, 2f.dp(), Color.BLACK)
        }

        thumbStrokePaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = thumbBorderWidth
            color = thumbBorderColor
        }

        labelPaint.apply {
            textSize = labelTextSize
            color = labelColor
            textAlign = Paint.Align.CENTER
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        centerY = height / 2f
        sliderStart = paddingLeft + thumbRadius
        sliderEnd = width - paddingRight - thumbRadius
        thumbX = sliderStart + (sliderEnd - sliderStart) * progress

        canvas.drawRoundRect(sliderStart, centerY - trackHeight / 2, sliderEnd, centerY + trackHeight / 2, 12f, 12f, trackPaint)
        canvas.drawRoundRect(sliderStart, centerY - trackHeight / 2, thumbX, centerY + trackHeight / 2, 12f, 12f, activeTrackPaint)
        canvas.drawCircle(thumbX, centerY, thumbRadius, thumbPaint)
        canvas.drawCircle(thumbX, centerY, thumbRadius, thumbStrokePaint)

        canvas.drawText("${(progress * 100).toInt()}%", thumbX, centerY + thumbRadius + 20f.dp(), labelPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)

        // Calculate label text height using FontMetrics
        val textPaint = Paint().apply {
            textSize = labelTextSize
        }
        val fontMetrics = textPaint.fontMetrics
        val labelHeight = fontMetrics.bottom - fontMetrics.top

        val desiredHeight = (thumbRadius * 2) + 20f.dp() + labelHeight + paddingTop + paddingBottom

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> desiredHeight.coerceAtMost(heightSize.toFloat()).toInt()
            MeasureSpec.UNSPECIFIED -> desiredHeight.toInt()
            else -> desiredHeight.toInt()
        }

        setMeasuredDimension(width, height)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val x = event.x.coerceIn(sliderStart, sliderEnd)
                progress = (x - sliderStart) / (sliderEnd - sliderStart)
                invalidate()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    // Extensions
    private fun Float.dp(): Float = this * resources.displayMetrics.density
    private fun Float.sp(context: Context): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, context.resources.displayMetrics)
}