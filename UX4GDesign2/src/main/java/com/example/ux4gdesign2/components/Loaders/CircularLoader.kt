package com.example.ux4gdesign2.components.Loaders

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.example.ux4gdesign2.R

class CircularLoader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var loaderColor: Int = Color.BLUE
    private var strokeWidth: Float = 10f
    private var animator: ValueAnimator? = null
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    init {
        // Load attributes if provided in XML
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CircularLoader)
            loaderColor = typedArray.getColor(R.styleable.CircularLoader_loaderColor, R.attr.colorPrimary)
            strokeWidth = typedArray.getDimension(R.styleable.CircularLoader_loaderStrokeWidth, 10f)
            typedArray.recycle()
        }

        paint.color = loaderColor
        paint.strokeWidth = strokeWidth

        startLoading()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val radius = (width.coerceAtMost(height) / 2f) - (strokeWidth / 2)
        val cx = width / 2f
        val cy = height / 2f

        val oval = RectF(cx - radius, cy - radius, cx + radius, cy + radius)
        canvas.drawArc(oval, 0f, 270f, false, paint)  // Draw arc (incomplete circle)
    }

    private fun startLoading() {
        animator = ValueAnimator.ofFloat(0f, 360f).apply {
            duration = 1200L  // Animation speed (1 second per rotation)
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                rotation = it.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator?.cancel()
    }
}
