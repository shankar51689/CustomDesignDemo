package com.example.ux4gdesign2.components.dropdownComponent

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.ux4gdesign2.R

class CustomDropdownView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val container: LinearLayout

    private var onItemClick: ((String) -> Unit)? = null
    private var menuItems: List<String> = emptyList()

    init {
        LayoutInflater.from(context).inflate(R.layout.view_custom_dropdown, this, true)
        container = findViewById(R.id.dropdownContainer)
        orientation = VERTICAL
    }

    fun setMenuItems(items: List<String>) {
        menuItems = items
        buildMenu()
    }

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClick = listener
    }

    private fun buildMenu() {
        container.removeAllViews()
        menuItems.forEach { item ->
            val textView = TextView(context).apply {
                text = item
                setPadding(16, 8, 16, 8)
                setTextColor(Color.BLACK)
                background = ContextCompat.getDrawable(context, R.drawable.bg_menu_item)
                layoutParams = MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    bottomMargin = 8
                }
                setOnClickListener {
                    onItemClick?.invoke(item)
                }
            }
            container.addView(textView)
        }
    }
}
