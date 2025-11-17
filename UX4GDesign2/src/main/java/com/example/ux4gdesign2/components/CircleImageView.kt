package com.example.ux4gdesign2.components

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.example.ux4gdesign2.R
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin
import androidx.core.content.withStyledAttributes

class CircularImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var borderWidth = 0f  // Default border width = 0
    private var borderColor = Color.TRANSPARENT // Default border color = Transparent
    private var cornerRadius = 0f  // Default corner radius for rounded rectangle
    private var isCircle = true  // Flag to determine if the image is circular or rounded rectangle
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var bitmap: Bitmap? = null
    private var bitmapShader: BitmapShader? = null
    private var bitmapRect = RectF()
    private var borderRect = RectF()
    private var showGreenDot = false // Flag to control the visibility of the green dot
    private val dotPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.UX4G_success)
        style = Paint.Style.FILL
    }
    private val dotRadius = 15f // Radius of the green dot

    init {
        borderPaint.style = Paint.Style.STROKE
        loadAttributes(context, attrs)
    }

    private fun loadAttributes(context: Context, attrs: AttributeSet?) {
        attrs?.let {
            context.withStyledAttributes(it, R.styleable.CircularImageView) {
                borderWidth = getDimension(R.styleable.CircularImageView_borderWidth, 0f)
                borderColor = getColor(R.styleable.CircularImageView_borderColor, Color.GRAY)
                cornerRadius = getDimension(R.styleable.CircularImageView_imageCornerRadius, 0f)
                isCircle = getBoolean(R.styleable.CircularImageView_isCircle, true)
                showGreenDot = getBoolean(R.styleable.CircularImageView_isActive, false)
            }
        }
        borderPaint.color = borderColor
        borderPaint.strokeWidth = borderWidth
    }

    override fun onDraw(canvas: Canvas) {
        bitmap ?: return

        val viewWidth = width - paddingLeft - paddingRight
        val viewHeight = height - paddingTop - paddingBottom
        val size = min(viewWidth, viewHeight).toFloat()
        val left = (width - size) / 2f
        val top = (height - size) / 2f
        val right = left + size
        val bottom = top + size
        val radius = size / 2f
        val centerX = (left + right) / 2f
        val centerY = (top + bottom) / 2f

        bitmapShader?.let {
            paint.shader = it
            if (isCircle) {
                canvas.drawCircle(centerX, centerY, radius - borderWidth, paint)
            } else {
                canvas.drawRoundRect(left, top, right, bottom, cornerRadius, cornerRadius, paint)
            }
        }

        // Draw border
        if (borderWidth > 0) {
            if (isCircle) {
                canvas.drawCircle(centerX, centerY, radius - borderWidth / 2f, borderPaint)
            } else {
                borderRect.set(left + borderWidth / 2f, top + borderWidth / 2f,
                    right - borderWidth / 2f, bottom - borderWidth / 2f)
                canvas.drawRoundRect(borderRect, cornerRadius, cornerRadius, borderPaint)
            }
        }

        // Draw the green dot if the condition is met
        if (showGreenDot) {

            // Overlap amount: How much dot should be placed outside the corner
            val dotOverlap = dotRadius * 0.5f

            // Adjust for border and corner radius
            var dotX = right - borderWidth / 2f + dotOverlap
            var dotY = bottom - borderWidth / 2f + dotOverlap

            if (isCircle) {
                val centerX = (left + right + 25) / 2
                val centerY = (top + bottom + 25) / 2
                val angle = 45f // Angle to position the dot at the bottom-right
                val dotDistance = radius - borderWidth - dotRadius // Distance from center to dot
                dotX = centerX + dotDistance * cos(Math.toRadians(angle.toDouble())).toFloat()
                dotY = centerY + dotDistance * sin(Math.toRadians(angle.toDouble())).toFloat()
            } else {
                dotX = right - dotRadius * 2 - borderWidth + 18
                dotY = bottom - dotRadius * 2 - borderWidth + 18
            }

            canvas.drawCircle(dotX, dotY, dotRadius, dotPaint)
        }
    }

    private fun updateShader() {
        bitmap = getBitmapFromDrawable()
        bitmap?.let {
            val scale: Float
            val bitmapWidth = it.width.toFloat()
            val bitmapHeight = it.height.toFloat()

            val viewWidth = width.toFloat()
            val viewHeight = height.toFloat()

            val matrix = Matrix()

            if (isCircle) {
                val size = min(viewWidth, viewHeight)
                scale = size / min(bitmapWidth, bitmapHeight)
            } else {
                scale = maxOf(viewWidth / bitmapWidth, viewHeight / bitmapHeight)
            }

            matrix.setScale(scale, scale)

            bitmapShader = BitmapShader(it, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            bitmapShader?.setLocalMatrix(matrix)
            paint.shader = bitmapShader

            invalidate()
        }
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateShader()
    }

    private fun getBitmapFromDrawable(): Bitmap? {
        val drawable = drawable ?: return null
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    fun setBorderWidth(width: Float) {
        borderWidth = width
        borderPaint.strokeWidth = borderWidth
        invalidate()
    }

    fun setBorderColor(color: Int) {
        borderColor = color
        borderPaint.color = borderColor
        invalidate()
    }

    fun setCornerRadius(radius: Float) {
        cornerRadius = radius
        invalidate()
    }

    /*fun setShape(isCircle: Boolean) {
        this.isCircle = isCircle
        invalidate()
    }*/

    fun setShowGreenDot(show: Boolean) {
        showGreenDot = show
        invalidate()
    }
}