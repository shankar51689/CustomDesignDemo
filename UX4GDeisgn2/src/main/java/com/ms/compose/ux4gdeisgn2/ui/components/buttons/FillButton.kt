package com.ms.compose.ux4gdeisgn2.ui.components.buttons

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.TextViewCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.ms.compose.ux4gdeisgn2.R
import com.ms.compose.ux4gdeisgn2.ui.components.Utility.dpToPx
import com.ms.compose.ux4gdeisgn2.ui.components.Utility.getThemeColor

class FillButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.buttonStyle
) : MaterialButton(context, attrs, defStyleAttr) {

    init {
        stateListAnimator = null // Remove click elevation effect

        insetTop        = 0
        insetBottom     = 0
        minHeight       = 0
        minimumHeight   = 0

        // ✅ Default Text Size (Always 16sp if not explicitly set)
        if (!isAttributeSet(attrs, android.R.attr.textSize)) {
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        }

       /* if (!isAttributeSet(attrs, android.R.attr.textAppearance)) {
            Log.i("TaG","-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=->")
            setTextAppearance(R.style.UX4GTheme_Button_T1) // Apply default typography
        }*/


        try {
            applyCustomStyle(attrs)
        } catch (e: Exception) {
            Log.e("FillButton", "Error applying custom style ${e.message}")
        }


    }

    private fun applyCustomStyle(attrs: AttributeSet?) {

        context.theme.obtainStyledAttributes(attrs, R.styleable.FillButton, 0, 0).apply {
            try {
                // ✅ Preserve User-Defined Text Color (If Already Set)
                val userTextColor = textColors ?: null

                // ✅ Apply Text Appearance First
                val textStyleRes = getResourceId(R.styleable.FillButton_style, R.style.UX4GTheme_L1)
                TextViewCompat.setTextAppearance(this@FillButton, textStyleRes)

                // ✅ Ensure User-Defined Text Color Takes Precedence
                if (userTextColor != null) {
                    setTextColor(userTextColor)
                }

                // ✅ Set padding between drawable and text
                val drawablePadding = getDimensionPixelSize(R.styleable.FillButton_drawablePadding, 5.dpToPx(context))
                setCompoundDrawablePadding(drawablePadding)

                // ✅ Handle Corner Radius
                val cornerRadius = getDimension(R.styleable.FillButton_cornerRadius, resources.getDimension(R.dimen.ux4g_button_radius_8px))
                val shapeModel   = ShapeAppearanceModel.Builder().run{
                                                                        setAllCornerSizes(cornerRadius)
                                                                        build()
                                                                    }


                val shapeDrawable = MaterialShapeDrawable(shapeModel)

                // ✅ Set Background Color Automatically
                val backgroundColor     = getColor(R.styleable.FillButton_fb_background, context.getThemeColor(R.attr.colorPrimary))
                shapeDrawable.fillColor = ColorStateList.valueOf(backgroundColor)

                // ✅ Get Ripple Color from Text Color (Automatically!)
                val rippleColor    = textColors.defaultColor.withAlpha(50)
                val rippleDrawable = RippleDrawable(ColorStateList.valueOf(rippleColor), shapeDrawable, shapeDrawable)

                background = rippleDrawable // 🚀 Now ripple effect works perfectly without extra argument!

                // ✅ Handle Drawable Start & End
                val drawableStart = getDrawable(R.styleable.FillButton_drawableStart)?.mutate()
                val drawableEnd   = getDrawable(R.styleable.FillButton_drawableEnd)?.mutate()

                val iconSize = getDimensionPixelSize(R.styleable.FillButton_iconSize, 14.dpToPx(context))
                drawableStart?.let { setDrawableSize(it, iconSize) }
                drawableEnd?.let { setDrawableSize(it, iconSize) }

                // ✅ Apply Text Color to Icons
                val colorStateList = textColors
                drawableStart?.setTintList(colorStateList)
                drawableEnd?.setTintList(colorStateList)

                setCompoundDrawablesRelative(drawableStart, null, drawableEnd, null)

            } finally {
                recycle()
            }
        }
    }


    private fun setDrawableSize(drawable: Drawable, size: Int) {
        val wrappedDrawable = DrawableCompat.wrap(drawable)
        wrappedDrawable.setBounds(0, 0, size, size)
    }

    private fun isAttributeSet(attrs: AttributeSet?, attribute: Int): Boolean {
        val typedArray = context.obtainStyledAttributes(attrs, intArrayOf(attribute))
        val isSet      = typedArray.hasValue(0)
        typedArray.recycle()
        return isSet
    }

    private fun Int.withAlpha(alpha: Int): Int {
        return (this and 0x00FFFFFF) or (alpha shl 24)
    }
}

