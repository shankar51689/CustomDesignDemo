package com.ms.compose.ux4gdeisgn2.ui.components.switchButtons

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import com.ms.compose.ux4gdeisgn2.R

class CustomSwitchButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CompoundButton(context, attrs, defStyleAttr) {

    private val thumbPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val trackPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var trackRadius = 0f
    private var thumbRadius = 0f
    private var thumbPosition = 0f

    private val animationDuration = 300L // Animation time (in ms)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // **Calculate sizes dynamically**
        trackRadius = h / 2f
        thumbRadius = (h * 0.7f) / 2 // Thumb size = 70% of track height
        thumbPosition = if (isChecked) width - trackRadius * 2 else 0f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // **Set track color based on state**
        trackPaint.color = if (isChecked) ContextCompat.getColor(context, R.color.success_500)
        else ContextCompat.getColor(context, android.R.color.darker_gray)

        // **Draw Track**
        canvas.drawRoundRect(RectF(0f, 0f, width.toFloat(), height.toFloat()), trackRadius, trackRadius, trackPaint)

        // **Draw Thumb (inside track)**
        thumbPaint.color =  ContextCompat.getColor(context, android.R.color.white)
        canvas.drawCircle(thumbPosition + trackRadius, height / 2f, thumbRadius, thumbPaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> return true
            MotionEvent.ACTION_UP -> {
                toggleWithAnimation()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun toggleWithAnimation() {
        val start = thumbPosition
        val end = if (isChecked) 0f else width - trackRadius * 2 // Move thumb dynamically

        val animator = ValueAnimator.ofFloat(start, end)
        animator.duration = animationDuration
        animator.addUpdateListener {
            thumbPosition = it.animatedValue as Float
            invalidate()
        }
        animator.start()

        toggle()
    }

    override fun setChecked(checked: Boolean) {
        if (isChecked != checked) {
            super.setChecked(checked)
            thumbPosition = if (checked) width - trackRadius * 2 else 0f
            invalidate()
        }
    }
}
