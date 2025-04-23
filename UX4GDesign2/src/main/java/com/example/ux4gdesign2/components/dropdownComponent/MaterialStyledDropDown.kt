package com.example.ux4gdesign2.components.dropdownComponent

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import com.example.ux4gdesign2.R
import com.google.android.material.textfield.MaterialAutoCompleteTextView


class MaterialStyledDropdown @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val autoCompleteTextView: MaterialAutoCompleteTextView
    private val iconView: ImageView
    private val container: LinearLayout
    private var onItemSelected: ((String) -> Unit)? = null
    private var isOpen = false

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_material_dropdown, this, true)
        autoCompleteTextView = view.findViewById(R.id.dropdownView)
        iconView = view.findViewById(R.id.iconView)
        container = view.findViewById(R.id.container)

        // Default backgrounds
        autoCompleteTextView.setDropDownBackgroundDrawable(
            ContextCompat.getDrawable(context, R.drawable.bg_dropdown_popup)
        )
        /*ViewCompat.setBackground(
            autoCompleteTextView,
            ContextCompat.getDrawable(context, R.drawable.bg_dropdown_border)
        )*/

        // Read custom attributes
        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomListItem, 0, 0).apply {
            try {
                getString(R.styleable.CustomListItem_dropdown_hintText)?.let {
                    autoCompleteTextView.hint = it
                }


                autoCompleteTextView.setHintTextColor(getColor(R.styleable.CustomListItem_dropdown_hintColor,
                    ContextCompat.getColor(context, R.color.UX4G_neutral_600)))


                autoCompleteTextView.setTextColor(getColor(R.styleable.CustomListItem_dropdown_textColor,
                    ContextCompat.getColor(context, R.color.UX4G_neutral)))


                getDimension(R.styleable.CustomListItem_dropdown_textSize, -1f).takeIf { it != -1f }?.let {
                    autoCompleteTextView.textSize = it
                }

                getResourceId(R.styleable.CustomListItem_fontFamily, -1).takeIf { it != -1 }?.let {
                    ResourcesCompat.getFont(context, it)?.let { tf ->
                        autoCompleteTextView.typeface = tf
                    }
                }

                getDrawable(R.styleable.CustomListItem_dropdownIcon)?.let {
                    iconView.setImageDrawable(it)
                }

                getColor(R.styleable.CustomListItem_iconColor, -1).takeIf { it != -1 }?.let {
                    iconView.setColorFilter(it)
                }

                getResourceId(R.styleable.CustomListItem_fieldBackground, -1).takeIf { it != -1 }?.let {
                    ViewCompat.setBackground(autoCompleteTextView, ContextCompat.getDrawable(context, it))
                }

                getResourceId(R.styleable.CustomListItem_popupBackground, -1).takeIf { it != -1 }?.let {
                    autoCompleteTextView.setDropDownBackgroundDrawable(ContextCompat.getDrawable(context, it))
                }

            } finally {
                recycle()
            }
        }

        autoCompleteTextView.setOnClickListener {
            toggleDropdown()
        }

        iconView.setOnClickListener {
            toggleDropdown()
        }

        autoCompleteTextView.setOnDismissListener {
            rotateArrow(false)
            isOpen = false
        }

        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            autoCompleteTextView.clearFocus()
            onItemSelected?.invoke(autoCompleteTextView.adapter.getItem(position).toString())
            rotateArrow(false)
            isOpen = false
        }
    }

    private fun toggleDropdown() {
        if (isOpen) {
            autoCompleteTextView.dismissDropDown()
        } else {
            autoCompleteTextView.showDropDown()
        }
        rotateArrow(!isOpen)
        isOpen = !isOpen
    }

    private fun rotateArrow(shouldRotate: Boolean) {
        iconView.animate().rotation(if (shouldRotate) 180f else 0f).setDuration(250).start()
    }

    // Public APIs
    fun setItems(items: List<String>) {
        val adapter = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, items)
        autoCompleteTextView.setAdapter(adapter)
    }

    fun setOnItemSelectedListener(callback: (String) -> Unit) {
        this.onItemSelected = callback
    }

    fun setHint(hint: String) {
        autoCompleteTextView.hint = hint
    }

    fun setText(text: String) {
        autoCompleteTextView.setText(text, false)
    }

    fun getText(): String = autoCompleteTextView.text.toString()

    fun setEnabledState(enabled: Boolean) {
        autoCompleteTextView.isEnabled = enabled
    }

    // Customizations
    fun setHintColor(color: Int) {
        autoCompleteTextView.setHintTextColor(color)
    }

    fun setTextColor(color: Int) {
        autoCompleteTextView.setTextColor(color)
    }

    fun setFieldBackground(drawable: Drawable) {
        ViewCompat.setBackground(autoCompleteTextView, drawable)
    }

    fun setPopupBackground(drawable: Drawable) {
        autoCompleteTextView.setDropDownBackgroundDrawable(drawable)
    }

    fun setIconColor(color: Int) {
        iconView.setColorFilter(color)
    }

    fun setIconDrawable(drawable: Drawable) {
        iconView.setImageDrawable(drawable)
    }
}
