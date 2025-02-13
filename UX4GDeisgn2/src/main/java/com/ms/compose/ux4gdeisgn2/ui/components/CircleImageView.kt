package com.ms.compose.ux4gdeisgn2.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Color
import android.graphics.Outline
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.ImageView
import android.view.ViewOutlineProvider



import android.view.View

/*class CircularImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr)
{


    init {
        clipToOutline = true
        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                val size = Math.min(view.width, view.height) // Use the smallest dimension
                outline.setOval(0, 0, size, size)
            }
        }

        setBackgroundResource(android.R.color.transparent)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        invalidateOutline() // Recalculate outline when size changes
    }
}*/

import android.graphics.*
import androidx.appcompat.widget.AppCompatImageView
import com.ms.compose.ux4gdeisgn2.R

class CircularImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var borderWidth = 0f  // Default border width = 0
    private var borderColor = Color.TRANSPARENT // Default border color = Transparent
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
            typedArray.recycle()
        }
        borderPaint.color = borderColor
        borderPaint.strokeWidth = borderWidth
    }

    override fun onDraw(canvas: Canvas) {
        bitmap ?: return

        val size = Math.min(width, height).toFloat()
        val radius = size / 2f

        // Draw the circular image
        bitmapShader?.let {
            paint.shader = it
            canvas.drawCircle(radius, radius, radius - borderWidth, paint)
        }

        // Draw the border if borderWidth > 0
        if (borderWidth > 0) {
            canvas.drawCircle(radius, radius, radius - (borderWidth / 2), borderPaint)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateShader()
    }

    private fun updateShader() {
        bitmap = getBitmapFromDrawable()
        bitmap?.let {
            bitmapShader = BitmapShader(it, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            val size = Math.min(width, height).toFloat()
            val radius = size / 2f
            bitmapRect.set(0f, 0f, size, size)
            borderRect.set(borderWidth, borderWidth, size - borderWidth, size - borderWidth)
            invalidate()
        }
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
}



