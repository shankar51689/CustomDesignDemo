package com.example.ux4gdesign2.components.buttons

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.TextViewCompat
import com.example.ux4gdesign2.R
import com.example.ux4gdesign2.components.Utility.dpToPx
import com.example.ux4gdesign2.components.Utility.getThemeColor
import com.google.android.material.button.MaterialButton
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

class FillButton3 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.buttonStyle
) : MaterialButton(context, attrs, defStyleAttr) {

    // -----------------------------------
    // LOADER + ICON PRESERVATION
    // -----------------------------------
    private var loaderDrawable: LoaderDrawable? = null
    private var isLoaderVisible = false

    private var loaderSizeDp = 20

    private var originalStart: Drawable? = null
    private var originalEnd: Drawable? = null

    init {
        stateListAnimator = null

        insetTop = 0
        insetBottom = 0
        minHeight = 0
        minimumHeight = 0

        if (!isAttributeSet(attrs, android.R.attr.textSize)) {
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        }

        try {
            applyCustomStyle(attrs)
        } catch (e: Exception) {
            Log.e("FillButton3", "Error applying custom style ${e.message}")
        }
    }

    // -----------------------------------
    //  LOADER DRAWABLE (BUILT-IN)
    // -----------------------------------
    private inner class LoaderDrawable(var loaderColor: Int) : Drawable() {

        private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        }

        private var rotation = 0f
        private val animator = ValueAnimator.ofFloat(0f, 360f)

        init {
            animator.apply {
                duration = 1200L
                repeatCount = ValueAnimator.INFINITE
                interpolator = android.view.animation.LinearInterpolator()
                addUpdateListener {
                    rotation = animatedValue as Float
                    invalidateSelf()
                }
                start()
            }
        }

        override fun draw(canvas: Canvas) {
            val b = bounds
            val size = minOf(b.width(), b.height()).toFloat()
            val strokeWidth = size / 8f

            paint.strokeWidth = strokeWidth

            val radius = size / 2f - strokeWidth / 2f
            val cx = b.exactCenterX()
            val cy = b.exactCenterY()

            val tailColor = ColorUtils.setAlphaComponent(loaderColor, (0.2f * 255).toInt())

            val gradient = SweepGradient(
                cx, cy,
                intArrayOf(tailColor, loaderColor),
                floatArrayOf(0f, 1f)
            )

            val matrix = Matrix()
            matrix.postRotate(rotation, cx, cy)
            gradient.setLocalMatrix(matrix)

            paint.shader = gradient

            canvas.drawCircle(cx, cy, radius, paint)
        }

        override fun setAlpha(alpha: Int) {}
        override fun setColorFilter(colorFilter: ColorFilter?) {}
        override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
    }

    // -----------------------------------
    //  PUBLIC API: SHOW LOADER
    // -----------------------------------

    fun showLoader(
        color: Int = textColors.defaultColor,
        sizeDp: Int = loaderSizeDp
    ) {
        if (isLoaderVisible) return

        isLoaderVisible = true
        loaderSizeDp = sizeDp

        val sizePx = sizeDp.dpToPx(context)

        loaderDrawable = LoaderDrawable(color).apply {
            setBounds(0, 0, sizePx, sizePx)
        }

        // If user has drawableStart → combine loader + icon
        if (originalStart != null) {
            val combined = combineDrawables(loaderDrawable!!, originalStart!!)
            setCompoundDrawablesRelative(combined, null, originalEnd, null)
        } else {
            // Normal case: only loader
            setCompoundDrawablesRelative(loaderDrawable, null, originalEnd, null)
        }

        compoundDrawablePadding = 8.dpToPx(context)
        isEnabled = false
    }

    fun hideLoader() {
        if (!isLoaderVisible) return

        isLoaderVisible = false
        loaderDrawable = null

        // Restore original icons
        setCompoundDrawablesRelative(originalStart, null, originalEnd, null)

        isEnabled = true
    }

    fun setLoaderColor(color: Int) {
        loaderDrawable?.loaderColor = color
        loaderDrawable?.invalidateSelf()
    }

    // -----------------------------------
    //  MERGE LOADER + ORIGINAL ICON
    // -----------------------------------
    private fun combineDrawables(loader: Drawable, icon: Drawable): Drawable {

        val size = loader.intrinsicWidth + icon.intrinsicWidth + 12.dpToPx(context)
        val height = maxOf(loader.intrinsicHeight, icon.intrinsicHeight)

        val bitmap = Bitmap.createBitmap(size, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        loader.setBounds(0, 0, loader.intrinsicWidth, loader.intrinsicHeight)
        loader.draw(canvas)

        val left = loader.intrinsicWidth + 12.dpToPx(context)

        icon.setBounds(left, 0, left + icon.intrinsicWidth, icon.intrinsicHeight)
        icon.draw(canvas)

        return BitmapDrawable(resources, bitmap)
    }

    // -----------------------------------
    // ORIGINAL STYLE LOGIC (UNCHANGED)
    // -----------------------------------
    private fun applyCustomStyle(attrs: AttributeSet?) {

        context.theme.obtainStyledAttributes(attrs, R.styleable.FillButton, 0, 0).apply {
            try {

                val userTextColor = textColors ?: null

                val textStyleRes = getResourceId(R.styleable.FillButton_style, R.style.UX4GTheme_L1)
                TextViewCompat.setTextAppearance(this@FillButton3, textStyleRes)

                if (userTextColor != null) setTextColor(userTextColor)

                val drawablePadding = getDimensionPixelSize(
                    R.styleable.FillButton_drawablePadding,
                    5.dpToPx(context)
                )
                setCompoundDrawablePadding(drawablePadding)

                val cornerRadius = getDimension(
                    R.styleable.FillButton_cornerRadius,
                    resources.getDimension(R.dimen.ux4g_button_radius_8px)
                )

                val shapeModel = ShapeAppearanceModel.Builder()
                    .setAllCornerSizes(cornerRadius)
                    .build()

                val shapeDrawable = MaterialShapeDrawable(shapeModel)

                val backgroundColor = getColor(
                    R.styleable.FillButton_fb_background,
                    context.getThemeColor(R.attr.colorPrimary)
                )
                shapeDrawable.fillColor = ColorStateList.valueOf(backgroundColor)

                val rippleColor = textColors.defaultColor.withAlpha(50)
                val rippleDrawable = RippleDrawable(
                    ColorStateList.valueOf(rippleColor),
                    shapeDrawable,
                    shapeDrawable
                )

                background = rippleDrawable

                // SAVE original icons
                originalStart = getDrawable(R.styleable.FillButton_drawableStart)?.mutate()
                originalEnd = getDrawable(R.styleable.FillButton_drawableEnd)?.mutate()

                val iconSize = getDimensionPixelSize(
                    R.styleable.FillButton_iconSize,
                    14.dpToPx(context)
                )

                originalStart?.let { setDrawableSize(it, iconSize) }
                originalEnd?.let { setDrawableSize(it, iconSize) }

                val colorStateList = textColors
                originalStart?.setTintList(colorStateList)
                originalEnd?.setTintList(colorStateList)

                setCompoundDrawablesRelative(originalStart, null, originalEnd, null)

            } finally {
                recycle()
            }
        }
    }

    private fun setDrawableSize(drawable: Drawable, size: Int) {
        val wrapped = DrawableCompat.wrap(drawable)
        wrapped.setBounds(0, 0, size, size)
    }

    private fun isAttributeSet(attrs: AttributeSet?, attribute: Int): Boolean {
        val typedArray = context.obtainStyledAttributes(attrs, intArrayOf(attribute))
        val isSet = typedArray.hasValue(0)
        typedArray.recycle()
        return isSet
    }

    private fun Int.withAlpha(alpha: Int): Int {
        return (this and 0x00FFFFFF) or (alpha shl 24)
    }
}
