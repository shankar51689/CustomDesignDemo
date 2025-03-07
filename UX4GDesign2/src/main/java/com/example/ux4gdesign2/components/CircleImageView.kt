package com.example.ux4gdesign2.components

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.example.ux4gdesign2.R

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
            typedArray.recycle()
        }
        borderPaint.color = borderColor
        borderPaint.strokeWidth = borderWidth
    }

    override fun onDraw(canvas: Canvas) {
        bitmap ?: return

        val size = Math.min(width, height).toFloat()
        val radius = size / 2f

        // Draw the image with the desired shape (circle or rounded rectangle)
        bitmapShader?.let {
            paint.shader = it
            if (isCircle) {
                // Draw circle
                canvas.drawCircle(radius, radius, radius - borderWidth, paint)
            } else {
                // Draw rounded rectangle
                canvas.drawRoundRect(0f, 0f, size, size, cornerRadius, cornerRadius, paint)
            }
        }

        // Draw the border if borderWidth > 0
        if (borderWidth > 0) {
            if (isCircle) {
                canvas.drawCircle(radius, radius, radius - (borderWidth / 2), borderPaint)
            } else {
                // Ensure the border is drawn correctly for rounded rectangle
                val borderRadius = cornerRadius
                // Apply the border width properly on the rounded rectangle
                borderRect.set(borderWidth, borderWidth, size - borderWidth, size - borderWidth)
                canvas.drawRoundRect(borderRect, borderRadius, borderRadius, borderPaint)
            }
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
}
