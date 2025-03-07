package com.example.ux4gdesign2.components.Utility

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import androidx.annotation.AttrRes

fun Context.getThemeColor(@AttrRes attr: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attr, typedValue, true)
    return typedValue.data
}

fun getSolidColorFromGradientDrawable(drawable: GradientDrawable): Int {
    return try {
        val field = GradientDrawable::class.java.getDeclaredField("mSolidColor")
        field.isAccessible = true
        field.getInt(drawable)
    } catch (e: Exception) {
        e.printStackTrace()
        Color.TRANSPARENT // Default if extraction fails
    }
}

fun Int.dpToPx(context: Context): Int = (this * context.resources.displayMetrics.density).toInt()


/**
 * Helper function to check if padding attribute is set in XML.
 */
fun isPaddingSet(context: Context, attrs: AttributeSet?, paddingAttr: Int): Boolean {
    return attrs?.getAttributeValue("http://schemas.android.com/apk/res/android", context.resources.getResourceEntryName(paddingAttr)) != null
}