package com.example.ux4gdesign2.components.buttons

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.animation.ScaleAnimation
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.TextViewCompat
import com.example.ux4gdesign2.R
import com.example.ux4gdesign2.components.Utility.dpToPx
import com.example.ux4gdesign2.components.Utility.getThemeColor
import com.example.ux4gdesign2.components.Utility.isPaddingSet
import com.google.android.material.button.MaterialButton
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

class FillButton2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null ,
    defStyleAttr: Int = android.R.attr.buttonStyle
) : MaterialButton(context, attrs, defStyleAttr) {

    init {
        stateListAnimator = null // Remove click elevation effect

        insetTop       = 0
        insetBottom    = 0
        minHeight      = 0
        minimumHeight  = 0

        // ✅ Default Text Size (Always 14sp if not explicitly set)
        if (!isAttributeSet(attrs, android.R.attr.textSize)) {
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        }
        if (!isAttributeSet(attrs, android.R.attr.textAppearance)) {
//            setTextAppearance(R.style.UX4GTheme_L1) // Apply default typography
        }
        setupClickAnimation()
        applyCustomStyle(attrs)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun applyCustomStyle(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.FillButton, 0, 0).apply {
            try {

                // ✅ Preserve User-Defined Text Color (If Already Set)
                val userTextColor = textColors ?: null

                // ✅ Apply Text Appearance First
//                val textStyleRes = getResourceId(R.styleable.FillButton_style, R.style.UX4GTheme_L1)
//                TextViewCompat.setTextAppearance(this@FillButton2, textStyleRes)

                // ✅ Ensure User-Defined Text Color Takes Precedence
                if (userTextColor != null) {
                    setTextColor(userTextColor)
                }

                // ✅ Set padding between drawable and text
                val drawablePadding = getDimensionPixelSize(R.styleable.FillButton_drawablePadding, 8.dpToPx(context))
                setCompoundDrawablePadding(drawablePadding)

                // ✅ Handle Corner Radius
                val cornerRadius = getDimension(R.styleable.FillButton_cornerRadius, resources.getDimension(R.dimen.ux4g_button_radius_8px))
                val shapeModel   = ShapeAppearanceModel.Builder().run {
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

                background = rippleDrawable // Now ripple effect works perfectly without extra argument!

                // ✅ Handle Drawable Start & End
                val drawableStart = getDrawable(R.styleable.FillButton_drawableStart)?.mutate()
                val drawableEnd   = getDrawable(R.styleable.FillButton_drawableEnd)?.mutate()

                val iconSize = getDimensionPixelSize(R.styleable.FillButton_iconSize, 14.dpToPx(context))
                drawableStart?.let { setDrawableSize(it, iconSize) }
                drawableEnd?.let { setDrawableSize(it, iconSize) }

                // ✅ Ensure Default Text Color is White if Not Set
                if (!isAttributeSet(attrs, android.R.attr.textColor)) {
                    setTextColor(ContextCompat.getColor(context, android.R.color.white))
                }

                // ✅ Apply Text Color to Icons
                val colorStateList = textColors
                drawableStart?.setTintList(colorStateList)
                drawableEnd?.setTintList(colorStateList)

                setCompoundDrawablesRelative(drawableStart, null, drawableEnd, null)

            } finally {
                recycle()
            }
        }

        // ✅ Preserve User-Defined Padding
        val leftPadding     = paddingLeft.takeIf { isPaddingSet(context, attrs, android.R.attr.paddingLeft) } ?: 10.dpToPx(context)
        val topPadding      = paddingTop.takeIf { isPaddingSet(context, attrs, android.R.attr.paddingTop) } ?: 8.dpToPx(context)
        val rightPadding    = paddingRight.takeIf { isPaddingSet(context, attrs, android.R.attr.paddingRight) } ?: 10.dpToPx(context)
        val bottomPadding   = paddingBottom.takeIf { isPaddingSet(context, attrs, android.R.attr.paddingBottom) } ?: 8.dpToPx(context)

        setPadding(leftPadding, topPadding, rightPadding, bottomPadding)
    }

    private fun setDrawableSize(drawable: Drawable, size: Int) {
        val wrappedDrawable = DrawableCompat.wrap(drawable)
        wrappedDrawable.setBounds(0, 0, size, size)
    }

    private fun isAttributeSet(attrs: AttributeSet?, attribute: Int): Boolean {
        val typedArray  = context.obtainStyledAttributes(attrs, intArrayOf(attribute))
        val isSet       = typedArray.hasValue(0)
        typedArray.recycle()
        return isSet
    }

    private fun Int.withAlpha(alpha: Int): Int {
        return (this and 0x00FFFFFF) or (alpha shl 24)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupClickAnimation() {
        setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    shrinkAnimation(v)
                    false // Allow other click events to be processed
                }
                MotionEvent.ACTION_UP -> {
                    expandAnimation(v)
                    performClick() // Manually trigger the click event
                    false // Allow setOnClickListener to be called
                }
                MotionEvent.ACTION_CANCEL -> {
                    expandAnimation(v)
                    true
                }
                else -> false
            }
        }
    }



    private fun shrinkAnimation(view: View) {
        val shrink = ScaleAnimation(
            1f, 0.95f,  // Scale from 100% to 95%
            1f, 0.95f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f
        )
        shrink.duration = 100
        shrink.fillAfter = true
        view.startAnimation(shrink)
    }

    private fun expandAnimation(view: View) {
        val expand = ScaleAnimation(
            0.95f, 1f,  // Scale from 95% to 100%
            0.95f, 1f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f
        )
        expand.duration = 100
        expand.fillAfter = true
        view.startAnimation(expand)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

}
