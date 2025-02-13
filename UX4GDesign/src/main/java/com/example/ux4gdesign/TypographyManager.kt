package com.example.ux4gdesign

import android.content.Context
import android.widget.TextView

object TypographyManager {

    fun getTextStyle(context: Context, textView: TextView, style: TextStyle) {
        when (style) {
            TextStyle.Display -> applyStyle(textView, R.style.UX4GTheme_Display)
            TextStyle.H1 -> applyStyle(textView, R.style.UX4GTheme_H1)
            TextStyle.BodyLarge -> applyStyle(textView, R.style.UX4GTheme_BodyLarge)
            TextStyle.Caption -> applyStyle(textView, R.style.UX4GTheme_Caption)
        }
    }

    private fun applyStyle(textView: TextView, styleRes: Int) {
        textView.setTextAppearance(styleRes)
    }
}

enum class TextStyle {
    Display, H1, BodyLarge, Caption
}

// How to Used this
//TypographyManager.getTextStyle(context, myTextView, TextStyle.H1)