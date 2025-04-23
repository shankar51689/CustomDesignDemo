package com.example.ux4gdesign2.components.progressbarcomponents

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.ux4gdesign2.R
import kotlin.math.min

class LinearProgressBar2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Default dimensions
    private val defaultWidth  = 300 // Default width in pixels
    private val defaultHeight = 50 // Default height in pixels

    private var progress            = 0  // Progress value (0-100)
    private var progressTextSize    = 40f
    private var backgroundBarColor  = ContextCompat.getColor(context, R.color.UX4G_primary_100)
    private var progressBarColor    = ContextCompat.getColor(context, R.color.UX4G_primary_400)
    private var showPercentage      = false  // Default: don't show percentage
    private var showDownLabel       = false  // Default: don't show down Label Percentage

    private val progressPaint   = Paint(Paint.ANTI_ALIAS_FLAG)
    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint       = Paint(Paint.ANTI_ALIAS_FLAG)
    private val thumbPaint      = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.LinearProgressBar2, 0, 0).apply {
            try {
                progress            = getInteger(R.styleable.LinearProgressBar2_progress, 0)
                progressTextSize    = getDimension(R.styleable.LinearProgressBar2_progressTextSize, 40f)
                backgroundBarColor  = getColor(R.styleable.LinearProgressBar2_backgroundBarColor, ContextCompat.getColor(context, R.color.UX4G_primary_100))
                progressBarColor    = getColor(R.styleable.LinearProgressBar2_progressBarColor, ContextCompat.getColor(context, R.color.UX4G_primary_400))
                showPercentage      = getBoolean(R.styleable.LinearProgressBar2_showPercentageText, false) // Default: false
                showDownLabel       = getBoolean(R.styleable.LinearProgressBar2_showDownLabel, false) // Default: false
            } finally {
                recycle()
            }
        }

        progressPaint.apply {
            color = progressBarColor
            style = Paint.Style.FILL
        }
        backgroundPaint.apply {
            color = backgroundBarColor
            style = Paint.Style.FILL
        }
        textPaint.apply {
            color       = Color.DKGRAY
            textSize    = progressTextSize
            textAlign   = Paint.Align.LEFT
        }
        thumbPaint.apply {
            color = progressBarColor
            style = Paint.Style.FILL
        }
    }

    fun setProgress(value: Int) {
        progress = min(100, maxOf(0, value))
        invalidate()
    }

    fun setProgressTextSize(size: Float) {
        progressTextSize   = size
        textPaint.textSize = size
        invalidate()
    }

    fun setBackgroundBarColor(color: Int) {
        backgroundBarColor    = color
        backgroundPaint.color = color
        invalidate()
    }

    fun setProgressBarColor(color: Int) {
        progressBarColor    = color
        progressPaint.color = color
        thumbPaint.color    = color
        invalidate()
    }

    fun setShowPercentage(show: Boolean) {
        showPercentage = show
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth  = defaultWidth
        val desiredHeight = defaultHeight

        val width  = resolveSize(desiredWidth, widthMeasureSpec)
        val height = resolveSize(desiredHeight, heightMeasureSpec)

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if(showDownLabel) {
            downProgressLabel(canvas)
        } else {
            sideProgressLabel(canvas)
        }

    }

    private fun sideProgressLabel(canvas: Canvas) {
        val width  = width.toFloat()
        val height = height.toFloat()
        val trackHeight = height / 4
        val radius = trackHeight / 2

        // Draw background bar (rounded at both ends)
        val backgroundRect = RectF(0f, height / 2 - trackHeight / 2, width - 100, height / 2 + trackHeight / 2)
        canvas.drawRoundRect(backgroundRect, radius, radius, backgroundPaint)

        // Draw progress bar (rounded at both ends)
        val progressWidth = (progress / 100f) * (width - 100)
        val progressRect  = RectF(0f, height / 2 - trackHeight / 2, progressWidth, height / 2 + trackHeight / 2)
        canvas.drawRoundRect(progressRect, radius, radius, progressPaint)

        // Draw circular thumb at progress end
        val thumbRadius = height / 8
        canvas.drawCircle(progressWidth, height / 2, thumbRadius, thumbPaint)

        // Show progress text if enabled
        if (showPercentage) {
            val progressText = "$progress%"
            canvas.drawText(progressText, width - 90, height / 2 + textPaint.textSize / 3, textPaint)
        }
    }

    private fun downProgressLabel(canvas: Canvas) {
        val width  = width.toFloat()
        val height = height.toFloat()

        // Adjusted heights for separation
        val trackHeight = height / 5  // Thin progress bar
        val textHeight  = textPaint.textSize + 10  // Space for text
        val radius = trackHeight / 2  // Rounded edges

        // Calculate Y positions
        val progressBarY = trackHeight / 2  // Move progress bar slightly up
        val textY = height - 10  // Push text to bottom

        // Draw background bar (fully rounded)
        val backgroundRect = RectF(0f, progressBarY - trackHeight / 2, width, progressBarY + trackHeight / 2)
        canvas.drawRoundRect(backgroundRect, radius, radius, backgroundPaint)

        // Calculate progress width
        val progressWidth = (progress / 100f) * width

        // Draw progress bar (fully rounded)
        val progressRect = RectF(0f, progressBarY - trackHeight / 2, progressWidth, progressBarY + trackHeight / 2)
        canvas.drawRoundRect(progressRect, radius, radius, progressPaint)

        // Draw circular thumb at the end of progress bar
        val thumbRadius = trackHeight / 1.8f
        canvas.drawCircle(progressWidth, progressBarY, thumbRadius, thumbPaint)

        // Draw progress text below the progress bar, aligned to bottom-right
        if (showPercentage) {
            val progressText = "$progress%"
            val textWidth = textPaint.measureText(progressText)

            // Align text to bottom-right
            val textX = width - textWidth - 10
            canvas.drawText(progressText, textX, textY, textPaint)
        }
    }

}
