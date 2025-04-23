package com.example.ux4gdesign2.components.listGroup

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.ux4gdesign2.R
import com.example.ux4gdesign2.components.switchButtons.CustomSwitchButton

class CustomListCard3 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

//    private val icon: ImageView
    private val titleTextView: TextView
    private val supportingTextView: TextView
    private val checkBox: CheckBox
    private val radioBtn: RadioButton
    private val arrowIcon: ImageView
    private val toggleSwitchRight: CustomSwitchButton
    private val cv: CardView
    private val ivIcon: ImageView
    private val ivImage: ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_card3_view, this, true)

//        icon = findViewById(R.id.icon)
        titleTextView       = findViewById(R.id.title)
        supportingTextView  = findViewById(R.id.supportingText)
        checkBox            = findViewById(R.id.checkBoxRight)
        arrowIcon           = findViewById(R.id.arrowIconRight)
        toggleSwitchRight   = findViewById(R.id.toggleSwitchRight)
        radioBtn            = findViewById(R.id.radioButtonRight)
        cv                  = findViewById(R.id.cv)
        ivIcon              = findViewById(R.id.ivIcon)
        ivImage             = findViewById(R.id.ivImage)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CustomListItem)

            // Set attributes from XML
            titleTextView.text      = typedArray.getString(R.styleable.CustomListItem_titleText) ?: "List Item"
            titleTextView.textSize  = typedArray.getDimension(R.styleable.CustomListItem_titleTextFontSize, 14f)
            supportingTextView.text = typedArray.getString(R.styleable.CustomListItem_supportingText) ?: ""
            supportingTextView.textSize = typedArray.getDimension(R.styleable.CustomListItem_supportingTextFontSize, 14f)

            val iconResId = typedArray.getResourceId(R.styleable.CustomListItem_iconSrc, R.drawable.ic_starts4)
            val imageResId = typedArray.getResourceId(R.styleable.CustomListItem_src, R.drawable.ic_starts4)

            if (iconResId != R.drawable.ic_starts4) {
                ivIcon.setImageResource(iconResId)
            } else {
                ivIcon.visibility = GONE
            }

            if (imageResId != R.drawable.ic_starts4) {
                ivImage.setImageResource(imageResId)
            } else {
                ivImage.visibility = GONE
            }

            titleTextView.setTextColor(typedArray.getColor(R.styleable.CustomListItem_titleColor, titleTextView.currentTextColor))
            supportingTextView.setTextColor(typedArray.getColor(R.styleable.CustomListItem_supportingTextColor, supportingTextView.currentTextColor))
            cv.setCardBackgroundColor(typedArray.getColor(R.styleable.CustomListItem_cardBackgroundColor, ContextCompat.getColor(context, R.color.white)))


//            icon.setImageResource(typedArray.getResourceId(R.styleable.CustomListItem_iconSrc, R.drawable.ic_launcher_foreground))

            checkBox.visibility     = if (typedArray.getBoolean(R.styleable.CustomListItem_showCheckBox, false)) VISIBLE else GONE
            arrowIcon.visibility    = if (typedArray.getBoolean(R.styleable.CustomListItem_showArrow, false)) VISIBLE else GONE
            toggleSwitchRight.visibility = if (typedArray.getBoolean(R.styleable.CustomListItem_showSwitch, false)) VISIBLE else GONE
            radioBtn.visibility     = if (typedArray.getBoolean(R.styleable.CustomListItem_showRadioBtn, false)) VISIBLE else GONE
            supportingTextView.visibility = if (supportingTextView.text != "") VISIBLE else GONE

            typedArray.recycle()
        }
    }

    /** Getters for programmatic access **/
    fun setOnArrowClickListener(listener: OnClickListener) {
        return arrowIcon.setOnClickListener(listener)
    }

    /** Setters for programmatic updates **/
    fun setTitleText(text: String) {
        titleTextView.text = text
    }

    fun setSupportingText(text: String) {
        supportingTextView.text = text
    }

    fun setTitleTextColor(color: Int) {
        titleTextView.setTextColor(color)
    }

    fun setSupportingTextColor(color: Int) {
        supportingTextView.setTextColor(color)
    }
}
