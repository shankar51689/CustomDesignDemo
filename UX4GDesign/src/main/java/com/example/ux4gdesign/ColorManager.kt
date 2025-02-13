package com.example.ux4gdesign

import android.content.Context
import android.util.TypedValue

object ColorManager {

    fun getThemeColor(context: Context, attr: Int): Int {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(attr, typedValue, true)
        return typedValue.data
    }

    // Usage in kotlin
    /*val primaryColor = getThemeColor(context, R.attr.colorPrimary)
    button.setBackgroundColor(primaryColor)*/

}