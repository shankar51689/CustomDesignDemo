package com.example.ux4gdesign2.components.chips

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.ux4gdesign2.R
import com.google.android.material.chip.Chip

class CustomChipView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Chip(context, attrs, defStyleAttr) {

    init {
        setupAttributes(attrs)
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CustomChipView)

            val text = typedArray.getString(R.styleable.CustomChipView_chipText) ?: "Label"
            val iconResId = typedArray.getResourceId(R.styleable.CustomChipView_chipIcon, -1)
            val backgroundColor = typedArray.getColor(
                R.styleable.CustomChipView_chipBackgroundColor,
                ContextCompat.getColor(context, R.color.purple_500)
            )
            val textColor = typedArray.getColor(
                R.styleable.CustomChipView_chipTextColor,
                ContextCompat.getColor(context, android.R.color.white)
            )
            val isSelectable = typedArray.getBoolean(R.styleable.CustomChipView_chipSelectable, false)
            val isDeletable = typedArray.getBoolean(R.styleable.CustomChipView_chipDeletable, false)

            typedArray.recycle()

            // Set chip properties
            text?.let { setText(it) }
            setTextColor(textColor)
            chipBackgroundColor = ColorStateList.valueOf(backgroundColor)


            // Set icon if provided
            if (iconResId != -1) {
                setChipIcon(iconResId)
            }

            // Make chip selectable
            isCheckable = isSelectable
            isCheckedIconVisible = isSelectable

            // Add delete button
            if (isDeletable) {
                isChipIconVisible = true
                setOnCloseIconClickListener { visibility = View.GONE }
            }
        }
    }

    fun setChipText(text: String) {
        this.text = text
    }

    fun setChipIcon(iconResId: Int) {
        this.chipIcon = ContextCompat.getDrawable(context, iconResId)
        this.isChipIconVisible = true // Corrected method
    }
}
