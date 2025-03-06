package com.ms.compose.ux4gdeisgn2.ui.components.buttons

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import android.content.res.ColorStateList
import android.view.ViewOutlineProvider
import androidx.core.content.ContextCompat
import com.ms.compose.ux4gdeisgn2.R
import com.ms.compose.ux4gdeisgn2.ui.components.Utility.getThemeColor

class ShadowButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {

    private var buttonColor: Int = Color.BLUE
    private var cornerRadius: Float = 20f
    private var elevationValue: Float = 10f
    private var rippleColor: Int = Color.LTGRAY

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ShadowButton,
            0, 0
        ).apply {
            try {
                buttonColor = getColor(R.styleable.ShadowButton_shadowButtonColor, context.getThemeColor(R.attr.colorPrimary))
                cornerRadius = getDimension(R.styleable.ShadowButton_shadowBtnCornerRadius, 20f)
                elevationValue = getDimension(R.styleable.ShadowButton_shadowElevation, 10f)
                rippleColor = getColor(R.styleable.ShadowButton_shadowRippleColor, ContextCompat.getColor(context, R.color.UX4G_neutral_500))
            } finally {
                recycle()
            }
        }
        applyStyles()
    }

    private fun applyStyles() {
        background = context.getDrawable(R.drawable.bg_filled_button)
        val shapeDrawable = GradientDrawable().apply {
            setColor(buttonColor)
            cornerRadius = this@ShadowButton.cornerRadius
        }

        val rippleDrawable = RippleDrawable(
            ColorStateList.valueOf(rippleColor),
            shapeDrawable,
            shapeDrawable // Use the same shape for the ripple effect to appear
        )

        background = rippleDrawable
        setPadding(40, 20, 40, 20)

        // Ensure proper shadow rendering
        setShadow()
    }

    private fun setShadow() {
        // Fix for proper shadow rendering
        outlineProvider = ViewOutlineProvider.BACKGROUND
        clipToOutline = false
        elevation = elevationValue
        translationZ = elevationValue

        // Enable hardware acceleration to ensure shadow appears
        setLayerType(View.LAYER_TYPE_HARDWARE, null)
    }
}
