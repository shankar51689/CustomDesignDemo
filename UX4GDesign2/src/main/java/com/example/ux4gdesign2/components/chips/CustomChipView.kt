package com.example.ux4gdesign2.components.chips

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.ux4gdesign2.R
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class CustomChipView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val textView: TextView
    private val leadingIconView: ImageView
    private val trailingIconView: ImageView
    private var isSelectedState = false

    init {
        orientation = HORIZONTAL
        setPadding(16, 8, 16, 8)
        background = ContextCompat.getDrawable(context, R.drawable.chip_background)

        LayoutInflater.from(context).inflate(R.layout.view_custom_chip, this, true)
        textView = findViewById(R.id.chip_text)
        leadingIconView = findViewById(R.id.chip_leading_icon)
        trailingIconView = findViewById(R.id.chip_trailing_icon)
    }

    fun setLabel(text: String) {
        textView.text = text
    }

    fun setLeadingIcon(icon: Drawable?) {
        if (icon != null) {
            leadingIconView.setImageDrawable(icon)
            leadingIconView.visibility = View.VISIBLE
        } else {
            leadingIconView.visibility = View.GONE
        }
    }

    fun setTrailingIcon(icon: Drawable?, onClick: (() -> Unit)? = null) {
        if (icon != null) {
            trailingIconView.setImageDrawable(icon)
            trailingIconView.visibility = View.VISIBLE
            trailingIconView.setOnClickListener { onClick?.invoke() }
        } else {
            trailingIconView.visibility = View.GONE
        }
    }

    fun setSelectedState(selected: Boolean) {
        isSelectedState = selected
        background = if (selected) {
            ContextCompat.getDrawable(context, R.drawable.chip_selected_background)
        } else {
            ContextCompat.getDrawable(context, R.drawable.chip_background)
        }
    }

    fun toggleSelection() {
        setSelectedState(!isSelectedState)
    }
}
