package com.example.ux4gdesign.components

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.example.ux4gdesign.R

class CustomButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.style.UX4GTheme_roundFillButton
) : AppCompatButton(context, attrs, defStyleAttr) {

    init {
        applyAttributes(attrs)
        //setPadding(24, 12, 24, 12)
    }

    private fun applyAttributes(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomButton)

        val buttonType = typedArray.getInt(R.styleable.CustomButton_buttonType, 0)
        val cornerRadius = typedArray.getDimension(R.styleable.CustomButton_cornerRadius, 8f)
//        setTextColor(ContextCompat.getColorStateList(context, R.color.button_text_color))

        // Apply button type styles
        when (buttonType) {
            0 -> setBackgroundResource(R.drawable.button_primary) // Primary
            1 -> setBackgroundResource(R.drawable.button_rounded) // Secondary
            2 -> {
                setBackgroundResource(R.drawable.button_outline)
            } // Outline
        }

        typedArray.recycle()
    }
}