package com.example.ux4gdesign2.components

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.example.ux4gdesign2.R

class CustomCanvasView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var circleColor: Int = Color.BLUE
    private var circleRadius: Float = 100f
    private var circleX: Float = 150f
    private var circleY: Float = 150f
    private var isDraggable: Boolean = true  // Default: Draggable
    private val paint = Paint().apply { isAntiAlias = true }
    private var externalClickListener: OnClickListener? = null  // Store external click listener
    var isDragging = false  // Flag to check if dragging
    private var isAnimating = false // Prevent multiple clicks causing unwanted scaling


    private var startX = 0f
    private var startY = 0f
    private var touchStartTime: Long = 0


    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomCanvasView, 0, 0).apply {
            try {
                circleColor = getColor(R.styleable.CustomCanvasView_circleColor, Color.BLUE)
                circleRadius = getDimension(R.styleable.CustomCanvasView_circleRadius, 100f)
                circleX = getDimension(R.styleable.CustomCanvasView_circleX, 0f)
                circleY = getDimension(R.styleable.CustomCanvasView_circleY, 0f)
                isDraggable = getBoolean(R.styleable.CustomCanvasView_isDraggable, true) // Read from XML
            } finally {
                recycle()
            }
        }
        paint.color = circleColor

        super.setOnClickListener {
            startAnimation()
            externalClickListener?.onClick(this)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if(circleX == 0f || circleY == 0f) {
            canvas.drawCircle(circleRadius, circleRadius, circleRadius, paint)
        } else {
            canvas.drawCircle(circleX, circleY, circleRadius, paint)
        }
    }


    private fun startAnimation() {
        if (isAnimating) return // Prevent multiple animations running simultaneously
        isAnimating = true

        val animator = ValueAnimator.ofFloat(circleRadius, circleRadius * 1.2f, circleRadius).apply {
            duration = 500
            interpolator = DecelerateInterpolator()
            addUpdateListener { animation ->
                circleRadius = animation.animatedValue as Float
                invalidate()
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    isAnimating = false // Reset flag after animation completes
                }
            })
        }
        animator.start()
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        externalClickListener = listener
    }

    // Handle touch events to move the circle
    override fun onTouchEvent(event: MotionEvent): Boolean {
       /* when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val dx = event.x - circleX
                val dy = event.y - circleY
                if (dx * dx + dy * dy <= circleRadius * circleRadius) {
                    touchStartTime = System.currentTimeMillis()
                    startX = event.x
                    startY = event.y
                    isDragging = true
                    return true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (isDragging && isDraggable) {
                    circleX = event.x
                    circleY = event.y
                    invalidate()
                    return true
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isDragging = false
                val touchTime = System.currentTimeMillis() - touchStartTime
                val moveDistance = Math.sqrt(((event.x - startX) * (event.x - startX) + (event.y - startY) * (event.y - startY)).toDouble())

                if (touchTime < 200 && moveDistance < 10) {
                    externalClickListener?.onClick(this) // Call external click listener
                    startAnimation()
                }
                return true
            }
        }*/
        return super.onTouchEvent(event)
    }

    // Allow enabling/disabling dragging dynamically
    fun setDraggable(draggable: Boolean) {
        isDraggable = draggable
    }
}
