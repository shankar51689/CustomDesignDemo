package com.example.ux4gdesign2.components.progressbarcomponents

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.ux4gdesign2.R

class HalfCircularProgressBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress = 40f // Default progress
    private var progressColor = Color.parseColor("#6A40FF")
    private var trackColor = Color.parseColor("#E0E0E0")
    private var labelText = "Uploading"
    private var labelColor = Color.DKGRAY
    private var showLabel = true

    private val trackPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SemiCircularProgressBar)

        labelText     = typedArray.getString(R.styleable.SemiCircularProgressBar_sc_labelText) ?: "Uploading"
        showLabel     = typedArray.getBoolean(R.styleable.SemiCircularProgressBar_sc_showLabel, true)
        trackColor    = typedArray.getColor(R.styleable.SemiCircularProgressBar_sc_trackColor, ContextCompat.getColor(context, R.color.UX4G_neutral_100))
        progressColor = typedArray.getColor(R.styleable.SemiCircularProgressBar_sc_progressColor, ContextCompat.getColor(context, R.color.UX4G_primary))


        typedArray.recycle()
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val widthSize = width.toFloat()
        val heightSize = height.toFloat()
        val isSmallSize = widthSize < 95f // Condition to switch UI mode

        val strokeWidth = if (isSmallSize) widthSize * 0.14f else widthSize * 0.12f
        val radius = (widthSize - strokeWidth) / 2

        trackPaint.color = trackColor
        trackPaint.strokeWidth = strokeWidth

        progressPaint.color = progressColor
        progressPaint.strokeWidth = strokeWidth

        val centerX = width / 2f
        val centerY = if (isSmallSize) heightSize * 0.6f else heightSize.toFloat()

        // Define arc bounds
        val rect = RectF(
            strokeWidth / 2, strokeWidth / 2, widthSize - strokeWidth / 2, widthSize
        )

        // Draw background track
        canvas.drawArc(rect, 180f, 180f, false, trackPaint)

        // Draw progress arc
        val sweepAngle = 180 * (progress / 100)
        canvas.drawArc(rect, 180f, sweepAngle, false, progressPaint)

        // Adjust text sizes dynamically
        val progressTextSize = if (isSmallSize) widthSize * 0.18f else widthSize * 0.16f
        textPaint.textSize = progressTextSize
        textPaint.color = Color.BLACK
        textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)

        // Center progress text
        val progressText = "${progress.toInt()}%"
        val progressTextY = if (isSmallSize) centerY - (radius * 0.05f) else centerY - (radius * 0.6f)

        canvas.drawText(progressText, centerX, progressTextY, textPaint)

        // Adjust label text size
        textPaint.textSize = progressTextSize * (if (isSmallSize) 0.6f else 0.55f)
        textPaint.color = labelColor
        textPaint.typeface = Typeface.DEFAULT

        // Adjust label position for small UI
        val labelYPosition = if (isSmallSize) heightSize - (progressTextSize * 0.5f) else progressTextY - (progressTextSize * 1f)
        if (showLabel) canvas.drawText(labelText, centerX, labelYPosition, textPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = MeasureSpec.getSize(widthMeasureSpec)
        val height = if (size < 45) (size * 0.85f).toInt() else (size / 2) + (size * 0.3f).toInt()
        setMeasuredDimension(size, height)
    }

    fun setProgress(value: Float) {
        progress = value.coerceIn(0f, 100f)
        invalidate()
    }

    fun setProgressColor(color: Int) {
        progressColor = color
        invalidate()
    }

    fun setTrackColor(color: Int) {
        trackColor = color
        invalidate()
    }

    fun setLabelText(text: String) {
        labelText = text
        invalidate()
    }

    fun setLabelColor(color: Int) {
        labelColor = color
        invalidate()
    }
}
