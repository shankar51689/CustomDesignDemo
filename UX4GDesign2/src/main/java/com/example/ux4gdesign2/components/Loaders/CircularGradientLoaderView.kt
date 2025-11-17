package com.example.ux4gdesign2.components.Loaders

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.core.graphics.ColorUtils
import kotlin.math.min

class CircularGradientLoaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var loaderColor = Color.parseColor("#613AF5") // default
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var gradient: SweepGradient? = null
    private var rotationAnimator: ObjectAnimator? = null
    init {
        startRotation()
    }

    fun setLoaderColor(color: Int) {
        loaderColor = color
        invalidate()
    }

    private fun startRotation() {
        rotationAnimator = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f).apply {
            duration = 1200L
            interpolator = LinearInterpolator()
            repeatCount = ObjectAnimator.INFINITE
            start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val size = min(width, height).toFloat()
        val radius = size / 2.2f
        val cx = width / 2f
        val cy = height / 2f

        // Tail color = 16% of head color
        val tailColor = ColorUtils.setAlphaComponent(loaderColor, (0.16f * 255).toInt())

        // Create conic-like gradient
        gradient = SweepGradient(
            cx, cy,
            intArrayOf(tailColor, loaderColor),
            floatArrayOf(0f, 1f)
        )

        val matrix = Matrix()
        // Rotate gradient start to match visual direction
        matrix.postRotate(180f, cx, cy)
        gradient?.setLocalMatrix(matrix)

        paint.shader = gradient
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = size / 10
        paint.strokeCap = Paint.Cap.ROUND

        canvas.drawCircle(cx, cy, radius, paint)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        rotationAnimator?.cancel()
    }


}