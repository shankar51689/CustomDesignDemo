package com.example.ux4gdesign2.components.buttons

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import androidx.core.graphics.ColorUtils
import com.example.ux4gdesign2.R
import com.example.ux4gdesign2.components.Utility.dpToPx
import com.example.ux4gdesign2.components.Utility.getThemeColor
import com.example.ux4gdesign2.components.Utility.isPaddingSet
import com.google.android.material.button.MaterialButton

class OutlineButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.buttonStyle
) : MaterialButton(context, attrs, defStyleAttr) {

    init {
        // ✅ Remove default ripple effect
        foreground = null
        // ✅ Remove elevation effect during click
        stateListAnimator = null

        // ✅ Remove MaterialButton's default forced padding
        insetTop = 0
        insetBottom = 0

        // ✅ Remove forced minHeight
        minHeight = 0
        minimumHeight = 0

        if (!isAttributeSet(attrs, android.R.attr.textAppearance)) {
//            setTextAppearance(R.style.UX4GTheme_L1) // Apply default typography
        }

        applyCustomStyle(attrs)
    }

    @SuppressLint("CustomViewStyleable")
    private fun applyCustomStyle(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomOutlineButton)
        try{


            // ✅ Preserve User-Defined Text Color (If Already Set)
            val userTextColor = textColors ?: null
            // ✅ Apply Text Appearance First

            val textStyleRes = typedArray.getResourceId(R.styleable.CustomOutlineButton_cob_style, R.style.UX4GTheme_L1)
//            TextViewCompat.setTextAppearance(this@OutlineButton, textStyleRes)

            /*// ✅ Ensure User-Defined Text Color Takes Precedence
            if (userTextColor != null) {
                setTextColor(userTextColor)
            }*/

            val buttonColor = if (typedArray.hasValue(R.styleable.CustomOutlineButton_buttonColor)) {
                                    typedArray.getColor(R.styleable.CustomOutlineButton_buttonColor, Color.BLACK)
                                } else {
                                    context.getThemeColor(R.attr.colorPrimary) // ✅ Use theme color properly
                                }
            val strokeWidth  = typedArray.getDimension(R.styleable.CustomOutlineButton_cob_strokeWidth, 2f)
            val cornerRadius = typedArray.getDimension(R.styleable.CustomOutlineButton_cob_cornerRadius, resources.getDimension(R.dimen.ux4g_button_radius_8px))

            val startIcon = typedArray.getDrawable(R.styleable.CustomOutlineButton_cob_iconStart)
            val endIcon = typedArray.getDrawable(R.styleable.CustomOutlineButton_cob_iconEnd)
            val iconPadding = typedArray.getDimensionPixelSize(R.styleable.CustomOutlineButton_cob_iconPadding, 0)

            isAllCaps = false
            gravity = Gravity.CENTER

            // Set Background with Ripple Effect
            background = createRippleDrawable(buttonColor, strokeWidth, cornerRadius)

            // Set Text Color
            setTextColor(buttonColor)

            // Preserve user-defined padding
            val leftPadding = if (isPaddingSet(context, attrs, android.R.attr.paddingLeft)) paddingLeft else 10.dpToPx(context)
            val topPadding = if (isPaddingSet(context, attrs, android.R.attr.paddingTop)) paddingTop else 8.dpToPx(context)
            val rightPadding = if (isPaddingSet(context, attrs, android.R.attr.paddingRight)) paddingRight else 10.dpToPx(context)
            val bottomPadding = if (isPaddingSet(context, attrs, android.R.attr.paddingBottom)) paddingBottom else 8.dpToPx(context)

            setPadding(leftPadding, topPadding, rightPadding, bottomPadding)


            // Configure Icons

            // Apply tint to icons
            startIcon?.setTint(buttonColor)
            endIcon?.setTint(buttonColor)

            val iconSize = (textSize * 1.0).toInt()
            startIcon?.setBounds(0, 0, iconSize, iconSize)
            endIcon?.setBounds(0, 0, iconSize, iconSize)

            setCompoundDrawables(startIcon, null, endIcon, null)
            compoundDrawablePadding = iconPadding
        } catch(e: Exception) {
            e.printStackTrace()
            Log.i("TaG","===========================> ${e.message}")
        } finally {
            typedArray.recycle()
        }

    }

    /**
     * Creates a ripple drawable with a lighter version of the button color.
     */

    private fun createRippleDrawable(buttonColor: Int, strokeWidth: Float, cornerRadius: Float): Drawable {
        val outlineDrawable = createOutlineDrawable(buttonColor, strokeWidth, cornerRadius)

        // ✅ White background instead of transparent/light version
        val maskDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            this.cornerRadius = cornerRadius
            setColor(Color.WHITE) // ✅ Background should be white
//            setColor(ColorUtils.setAlphaComponent(buttonColor, 30))
        }

        return RippleDrawable(
            ColorStateList.valueOf(ColorUtils.setAlphaComponent(buttonColor, 50)), // ✅ Light version of buttonColor for ripple effect
            outlineDrawable, // ✅ White Background
            maskDrawable // ✅ Mask should match the background for full ripple coverage
        )
    }


    /**
     * Creates an outlined drawable for the button.
     */
    private fun createOutlineDrawable(strokeColor: Int, strokeWidth: Float, cornerRadius: Float): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setStroke(strokeWidth.toInt(), strokeColor)
            setColor(Color.WHITE)
            this.cornerRadius = cornerRadius
        }
    }

    private fun isAttributeSet(attrs: AttributeSet?, attribute: Int): Boolean {
        val typedArray = context.obtainStyledAttributes(attrs, intArrayOf(attribute))
        val isSet = typedArray.hasValue(0)
        typedArray.recycle()
        return isSet
    }

}


/*font-family: Font Family/Headings;
    font-weight: 500;
    font-size: Font Size/3;
    line-height: Line Heights/9;
    letter-spacing: Letter Spacing/1;
    text-align: center;*/

