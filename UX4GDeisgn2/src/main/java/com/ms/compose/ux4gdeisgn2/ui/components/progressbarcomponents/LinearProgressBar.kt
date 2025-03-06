package com.ms.compose.ux4gdeisgn2.ui.components.progressbarcomponents

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.ms.compose.ux4gdeisgn2.R

class LinearProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress = 50f
    private var maxProgress = 100f
    private var progressHeight = 10f
    private var progressColor = ContextCompat.getColor(context, R.color.primary_400)
    private var backgroundColor = ContextCompat.getColor(context, R.color.primary_100)

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LinearProgressBar)
        progress = typedArray.getFloat(R.styleable.LinearProgressBar_linearProgress, 50f)
        maxProgress = typedArray.getFloat(R.styleable.LinearProgressBar_maxProgress, 100f)
        progressHeight = typedArray.getDimension(R.styleable.LinearProgressBar_progressHeight, 10f)
        progressColor = typedArray.getColor(R.styleable.LinearProgressBar_progressColor, progressColor)
        backgroundColor = typedArray.getColor(R.styleable.LinearProgressBar_backgroundColor, backgroundColor)
        typedArray.recycle()

        backgroundPaint.color = backgroundColor
        progressPaint.color = progressColor
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width.toFloat()
        val height = height.toFloat()
        val progressWidth = (progress / maxProgress) * width

        // Draw background
        canvas.drawRect(0f, 0f, width, height, backgroundPaint)

        // Draw progress
        canvas.drawRect(0f, 0f, progressWidth, height, progressPaint)
    }

    fun setLinearProgress(value: Float) {
        progress = value.coerceIn(0f, maxProgress)
        invalidate()
    }
}
