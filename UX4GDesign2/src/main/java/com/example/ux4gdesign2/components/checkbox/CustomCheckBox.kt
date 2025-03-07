package com.example.ux4gdesign2.components.checkbox

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import com.example.ux4gdesign2.R

class CustomCheckBoxWithLabel @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CompoundButton(context, attrs, defStyleAttr) {

    private val boxPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val checkMarkPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var boxRadius = 0f
    private var checkMarkSize = 0f
    private var checkMarkPositionX = 0f
    private var checkMarkPositionY = 0f

    private var labelText = "" // Label text
    private var labelTextSize = 16f // Default text size
    private var labelTextColor = ContextCompat.getColor(context, android.R.color.black) // Default text color
    private var labelTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var uncheckedBackgroundColor = ContextCompat.getColor(context, android.R.color.white)
    private var checkedBackgroundColor = ContextCompat.getColor(context, R.color.UX4G_success)
    private var strokeColor = ContextCompat.getColor(context, android.R.color.black)
    private var strokeWidth = 3f

    private val checkboxSize = 20f // 20dp fixed size for checkbox

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs, R.styleable.CustomCheckBoxWithLabel, 0, 0
        )
        try {
            // Retrieve custom attributes from XML
            uncheckedBackgroundColor = typedArray.getColor(R.styleable.CustomCheckBoxWithLabel_uncheckedBackgroundColor, uncheckedBackgroundColor)
            checkedBackgroundColor = typedArray.getColor(R.styleable.CustomCheckBoxWithLabel_checkedBackgroundColor, checkedBackgroundColor)
            strokeColor = typedArray.getColor(R.styleable.CustomCheckBoxWithLabel_checkBoxStrokeColor, strokeColor)
            strokeWidth = typedArray.getDimension(R.styleable.CustomCheckBoxWithLabel_checkBoxStrokeWidth, strokeWidth)

            labelText = typedArray.getString(R.styleable.CustomCheckBoxWithLabel_labelText) ?: labelText
            labelTextSize = typedArray.getDimension(R.styleable.CustomCheckBoxWithLabel_labelTextSize, labelTextSize)
            labelTextColor = typedArray.getColor(R.styleable.CustomCheckBoxWithLabel_labelTextColor, labelTextColor)

        } finally {
            typedArray.recycle()
        }

        // Initialize paint objects
        boxPaint.color = uncheckedBackgroundColor
        checkMarkPaint.color = ContextCompat.getColor(context, android.R.color.white) // White for checkmark
        checkMarkPaint.strokeWidth = 5f
        checkMarkPaint.isAntiAlias = true
        checkMarkPaint.style = Paint.Style.STROKE

        borderPaint.color = strokeColor
        borderPaint.strokeWidth = strokeWidth
        borderPaint.style = Paint.Style.STROKE

        labelTextPaint.color = labelTextColor
        labelTextPaint.textSize = labelTextSize

        isClickable = true
    }

    // Dynamically update label text
    fun setLabelText(text: String) {
        labelText = text
        invalidate()
    }

    // Dynamically update label text size
    fun setLabelTextSize(size: Float) {
        labelTextSize = size
        labelTextPaint.textSize = size
        invalidate()
    }

    // Dynamically update label text color
    fun setLabelTextColor(color: Int) {
        labelTextColor = color
        labelTextPaint.color = color
        invalidate()
    }

    // Dynamically update checkbox background color
    fun setUncheckedBackgroundColor(color: Int) {
        uncheckedBackgroundColor = color
        boxPaint.color = color
        invalidate()
    }

    // Dynamically update checked background color
    fun setCheckedBackgroundColor(color: Int) {
        checkedBackgroundColor = color
        invalidate()
    }

    // Dynamically update stroke color
    fun setStrokeColor(color: Int) {
        strokeColor = color
        borderPaint.color = color
        invalidate()
    }

    // Dynamically update stroke width
    fun setStrokeWidth(width: Float) {
        strokeWidth = width
        borderPaint.strokeWidth = width
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // Set the checkbox size to a fixed 20dp x 20dp
        val density = resources.displayMetrics.density
        val checkboxSizePx = (checkboxSize * density).toInt() // Convert 20dp to pixels

        // Update the size of the checkbox (we're using a fixed 20dp x 20dp size)
        layoutParams.width = checkboxSizePx
        layoutParams.height = checkboxSizePx

        // Ensure the checkmark and box position adjusts accordingly
        boxRadius = checkboxSizePx / 4f
        checkMarkSize = checkboxSizePx * 0.5f
        checkMarkPositionX = (checkboxSizePx / 2) - (checkMarkSize / 2)
        checkMarkPositionY = (checkboxSizePx / 2) - (checkMarkSize / 2)

        // Trigger a re-layout
        requestLayout()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // **Draw Box**
        if (isChecked) {
            boxPaint.color = checkedBackgroundColor // Green when checked
            canvas.drawRoundRect(RectF(0f, 0f, height.toFloat(), height.toFloat()), boxRadius, boxRadius, boxPaint) // Fill box
        } else {
            boxPaint.color = uncheckedBackgroundColor // White when unchecked
            canvas.drawRoundRect(RectF(0f, 0f, height.toFloat(), height.toFloat()), boxRadius, boxRadius, boxPaint) // No fill
        }

        // **Draw Box Border**
        canvas.drawRoundRect(RectF(0f, 0f, height.toFloat(), height.toFloat()), boxRadius, boxRadius, borderPaint)

        // **Draw Checkmark (Only when checked)**
        if (isChecked) {
            drawCheckMark(canvas)
        }

        // **Draw Label Text (Positioned beside the checkbox)**
        if (labelText.isNotEmpty()) {
            val textWidth = labelTextPaint.measureText(labelText)
            val textX = height.toFloat() + 16f // Right of the box with a gap of 16f
            val textY = (height / 2) + (labelTextPaint.textSize / 3) // Vertically centered relative to the box
            canvas.drawText(labelText, textX, textY, labelTextPaint)
        }
    }

    private fun drawCheckMark(canvas: Canvas) {
        checkMarkPaint.color = ContextCompat.getColor(context, android.R.color.white) // White color for tick

        val checkMarkPath = Path()
        val margin = 5f // Margin to adjust the checkmark position

        // Create checkmark path (Proper tick shape)
        // Start at the bottom-left corner (first part of the tick)
        checkMarkPath.moveTo(0f, checkboxSize/2)

        // Diagonal line from bottom-left to middle-right
        checkMarkPath.lineTo(checkboxSize / 2, checkboxSize - 2)

        // Diagonal line from middle-right to top-right (complete the tick)
        checkMarkPath.lineTo(checkMarkSize - 2, checkboxSize / 2)

        canvas.drawPath(checkMarkPath, checkMarkPaint) // Draw the path
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
        val start = if (isChecked) 1f else 0f
        val end = if (isChecked) 0f else 1f

        val animator = ValueAnimator.ofFloat(start, end)
        animator.duration = 300L
        animator.addUpdateListener {
            invalidate()
        }
        animator.start()

        toggle()
    }

    override fun setChecked(checked: Boolean) {
        if (isChecked != checked) {
            super.setChecked(checked)
            invalidate()
        }
    }
}
