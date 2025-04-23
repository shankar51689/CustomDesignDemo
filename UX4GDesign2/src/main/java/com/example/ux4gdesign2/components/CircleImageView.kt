package com.example.ux4gdesign2.components

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.example.ux4gdesign2.R
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

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
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CircularImageView)
            borderWidth = typedArray.getDimension(R.styleable.CircularImageView_borderWidth, 0f)
            borderColor = typedArray.getColor(R.styleable.CircularImageView_borderColor, Color.GRAY)
            cornerRadius = typedArray.getDimension(R.styleable.CircularImageView_imageCornerRadius, 0f)
            isCircle = typedArray.getBoolean(R.styleable.CircularImageView_isCircle, true)
            showGreenDot = typedArray.getBoolean(R.styleable.CircularImageView_isActive, false)
            typedArray.recycle()
        }
        borderPaint.color = borderColor
        borderPaint.strokeWidth = borderWidth
    }

    override fun onDraw(canvas: Canvas) {
        bitmap ?: return

        val availableWidth = width - paddingLeft - paddingRight
        val availableHeight = height - paddingTop - paddingBottom
        val size = min(availableWidth, availableHeight).toFloat()
        val radius = size / 2f

        val left = paddingLeft.toFloat()
        val top = paddingTop.toFloat()
        val right = left + size
        val bottom = top + size

        bitmapShader?.let {
            paint.shader = it
            if (isCircle) {
                val centerX = (left + right) / 2
                val centerY = (top + bottom) / 2
                canvas.drawCircle(centerX, centerY, radius - borderWidth, paint)
            } else {
                canvas.drawRoundRect(left, top, right, bottom, cornerRadius, cornerRadius, paint)
            }
        }

        if (borderWidth > 0) {
            if (isCircle) {
                val centerX = (left + right) / 2
                val centerY = (top + bottom) / 2
                canvas.drawCircle(centerX, centerY, radius - (borderWidth / 2), borderPaint)
            } else {
                borderRect.set(left + borderWidth, top + borderWidth, right - borderWidth, bottom - borderWidth)
                canvas.drawRoundRect(borderRect, cornerRadius, cornerRadius, borderPaint)
            }
        }

        // Draw the green dot if the condition is met
        if (showGreenDot) {
            val dotX: Float
            val dotY: Float

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
            bitmapShader = BitmapShader(it, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            val size = Math.min(width, height).toFloat()

            if (isCircle) {
                val radius = size / 2f
                borderRect.set(borderWidth, borderWidth, size - borderWidth, size - borderWidth)
            } else {
                // Set border rect for rounded rectangle shape
                borderRect.set(0f, 0f, size, size) // Same dimensions for the rectangle
            }

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

    fun setShape(isCircle: Boolean) {
        this.isCircle = isCircle
        invalidate()
    }

    fun setShowGreenDot(show: Boolean) {
        showGreenDot = show
        invalidate()
    }
}