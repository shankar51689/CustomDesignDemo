package com.example.ux4gdesign2.components.listGroup

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.ux4gdesign2.R
import com.example.ux4gdesign2.components.switchButtons.CustomSwitchButton

class CustomListCard1 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

//    private val icon: ImageView
    private val titleTextView: TextView
    private val supportingTextView: TextView
    private val titleTextView2: TextView
    private val supportingTextView2: TextView
    private val checkBox: CheckBox
    private val radioBtn: RadioButton
    private val arrowIcon: ImageView
    private val toggleSwitch: CustomSwitchButton
    private val cv: CardView
    private val container1 : LinearLayout
    private val container2 : LinearLayout
    private var setComponentsOnLeft = false

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_card1_view, this, true)

//        icon = findViewById(R.id.icon)
        titleTextView       = findViewById(R.id.title)
        supportingTextView  = findViewById(R.id.supportingText)
        titleTextView2      = findViewById(R.id.title2)
        supportingTextView2 = findViewById(R.id.supportingText2)
        checkBox            = findViewById(R.id.checkBoxRight)
        arrowIcon           = findViewById(R.id.arrowIconRight)
        toggleSwitch        = findViewById(R.id.toggleSwitchRight)
        radioBtn            = findViewById(R.id.radioButtonRight)
        cv                  = findViewById(R.id.cv)
        container1          = findViewById(R.id.container1)
        container2          = findViewById(R.id.container2)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CustomListItem)

            // Set attributes from XML
            setComponentsOnLeft = typedArray.getBoolean(R.styleable.CustomListItem_setComponentsOnLeft, false)
            if (setComponentsOnLeft) {
                titleTextView2.text      = typedArray.getString(R.styleable.CustomListItem_titleText) ?: "List Item"
                titleTextView2.textSize  = typedArray.getDimension(R.styleable.CustomListItem_titleTextFontSize, 14f)
                supportingTextView2.text = typedArray.getString(R.styleable.CustomListItem_supportingText) ?: ""
                supportingTextView2.textSize = typedArray.getDimension(R.styleable.CustomListItem_supportingTextFontSize, 14f)
                titleTextView2.setTextColor(typedArray.getColor(R.styleable.CustomListItem_titleColor, titleTextView.currentTextColor))
                supportingTextView2.setTextColor(typedArray.getColor(R.styleable.CustomListItem_supportingTextColor, supportingTextView.currentTextColor))
                container1.visibility = GONE
                container2.visibility = VISIBLE

            } else {
                titleTextView.text      = typedArray.getString(R.styleable.CustomListItem_titleText) ?: "List Item"
                titleTextView.textSize  = typedArray.getDimension(R.styleable.CustomListItem_titleTextFontSize, 14f)
                supportingTextView.text = typedArray.getString(R.styleable.CustomListItem_supportingText) ?: ""
                supportingTextView.textSize = typedArray.getDimension(R.styleable.CustomListItem_supportingTextFontSize, 14f)
                titleTextView.setTextColor(typedArray.getColor(R.styleable.CustomListItem_titleColor, titleTextView.currentTextColor))
                supportingTextView.setTextColor(typedArray.getColor(R.styleable.CustomListItem_supportingTextColor, supportingTextView.currentTextColor))
                container2.visibility = GONE
                container1.visibility = VISIBLE
            }


            cv.setCardBackgroundColor(typedArray.getColor(R.styleable.CustomListItem_cardBackgroundColor, ContextCompat.getColor(context, R.color.white)))


//            icon.setImageResource(typedArray.getResourceId(R.styleable.CustomListItem_iconSrc, R.drawable.ic_launcher_foreground))

            checkBox.visibility     = if (typedArray.getBoolean(R.styleable.CustomListItem_showCheckBox, false)) VISIBLE else GONE
            arrowIcon.visibility    = if (typedArray.getBoolean(R.styleable.CustomListItem_showArrow, false)) VISIBLE else GONE
            toggleSwitch.visibility = if (typedArray.getBoolean(R.styleable.CustomListItem_showSwitch, false)) VISIBLE else GONE
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
        if (setComponentsOnLeft) {
            titleTextView2.text = text
        } else {
            titleTextView.text = text
        }
    }

    fun setSupportingText(text: String) {
        if (setComponentsOnLeft) {
            supportingTextView2.text = text
        } else {
            supportingTextView.text = text
        }
    }

    fun setTitleTextColor(color: Int) {
        if (setComponentsOnLeft) {
            titleTextView2.setTextColor(color)
        } else {
            titleTextView.setTextColor(color)
        }
    }

    fun setSupportingTextColor(color: Int) {
        if (setComponentsOnLeft) {
            supportingTextView2.setTextColor(color)
        } else {
            supportingTextView.setTextColor(color)
        }
    }
}
