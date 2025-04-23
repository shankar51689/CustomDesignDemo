package com.example.ux4gdesign2.components.progressbarcomponents

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.example.ux4gdesign2.R
import kotlin.math.min

class CircularProgressBar2 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress        = 40f
    private var progressColor   = Color.parseColor("#6A40FF")
    private var trackColor      = Color.parseColor("#E0E0E0")
    private var labelText       = "Uploading"
    private var labelColor      = ContextCompat.getColor(context, R.color.UX4G_neutral_600)

    private val trackPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style       = Paint.Style.STROKE
        strokeCap   = Paint.Cap.ROUND
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style       = Paint.Style.STROKE
        strokeCap   = Paint.Cap.ROUND
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val size = min(width, height).toFloat()
        val strokeWidth = size * 0.1f
        val radius = (size - strokeWidth) / 2

        trackPaint.color = trackColor
        trackPaint.strokeWidth = strokeWidth

        progressPaint.color = progressColor
        progressPaint.strokeWidth = strokeWidth

        val centerX = width / 2f
        val centerY = height / 2f

        // Draw background track
        canvas.drawCircle(centerX, centerY, radius, trackPaint)

        val rect = RectF(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )

        canvas.drawArc(rect, -90f, 360 * (progress / 100), false, progressPaint)
        // Draw progress arc
        if ( size < 118) {



            /*val rect = RectF(strokeWidth / 2, strokeWidth / 2f, size - strokeWidth / 2, size - strokeWidth / 2)
            canvas.drawArc(rect, -90f, 360 * (progress / 100), false, progressPaint)*/
        } else {
           /* val rect = RectF(strokeWidth / 2, strokeWidth / 2, size - strokeWidth / 2, size - strokeWidth / 2)
            canvas.drawArc(rect, -90f, 360 * (progress / 100), false, progressPaint)*/
        }


        // Dynamically adjust text size
        val textSize = size * 0.2f
        textPaint.textSize = textSize
        textPaint.color = Color.BLACK
        textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD) // Make text bold

        // Draw progress text (always centered)
        val progressText = "${progress.toInt()}%"
        if (size < 118) {
            canvas.drawText(progressText, centerX, (centerY ) + textSize / 3, textPaint)
        } else {
            canvas.drawText(progressText, centerX, (centerY + 9 ) + textSize / 3, textPaint)
        }

        if (size < 118) { // 45dp
            // Draw label outside (bottom)
            // Adjust label placement based on size
            textPaint.textSize = 13f
            textPaint.color = labelColor
            textPaint.typeface = Typeface.DEFAULT // Label remains normal weight

            // Ensure label stays within bounds by increasing view height
            canvas.drawText(labelText, centerX, centerY + (height.toFloat() / 2.3f) , textPaint)
            //of canvas.drawText(labelText, centerX, height.toFloat() + textSize, textPaint)
        } else {
            // Draw label centered inside
            // Adjust label placement based on size
            textPaint.textSize = textSize * 0.45f
            textPaint.color = labelColor
            textPaint.typeface = Typeface.DEFAULT // Label remains normal weight

            canvas.drawText(labelText, centerX, (centerY + 8) - textSize / 1.5f, textPaint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = min(measuredWidth, measuredHeight)

        // If size is small, increase height for the label
        val newHeight = if (size < 118) (size + 50).toInt() else size // Add extra space for label
        setMeasuredDimension(size, newHeight)
    }


    // Public setters for customization
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
