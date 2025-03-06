package com.ms.compose.ux4gdeisgn2.ui.components.progressbarcomponents

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.ms.compose.ux4gdeisgn2.R

class CircularProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress = 50f
    private var maxProgress = 100f
    private var strokeWidth = 10f
    private var progressColor = ContextCompat.getColor(context, R.color.primary_400)
    private var backgroundColor = ContextCompat.getColor(context, R.color.primary_100)

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircularProgressBar)
        progress = typedArray.getFloat(R.styleable.CircularProgressBar_circularProgress, 50f)
        maxProgress = typedArray.getFloat(R.styleable.CircularProgressBar_maxCircularProgress, 100f)
        strokeWidth = typedArray.getDimension(R.styleable.CircularProgressBar_circularStrokeWidth, 10f)
        progressColor = typedArray.getColor(R.styleable.CircularProgressBar_circularProgressColor, progressColor)
        backgroundColor = typedArray.getColor(R.styleable.CircularProgressBar_circularBackgroundColor, backgroundColor)
        typedArray.recycle()

        backgroundPaint.apply {
            color = backgroundColor
            strokeWidth = this@CircularProgressBar.strokeWidth
        }

        progressPaint.apply {
            color = progressColor
            strokeWidth = this@CircularProgressBar.strokeWidth
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = (width - strokeWidth) / 2f

        val oval = RectF(
            centerX - radius, centerY - radius,
            centerX + radius, centerY + radius
        )

        // Draw background circle
        canvas.drawArc(oval, 0f, 360f, false, backgroundPaint)

        // Draw progress arc
        val sweepAngle = (progress / maxProgress) * 360
        canvas.drawArc(oval, -90f, sweepAngle, false, progressPaint)
    }

    fun setProgress(value: Float) {
        progress = value.coerceIn(0f, maxProgress)
        invalidate()
    }
}
