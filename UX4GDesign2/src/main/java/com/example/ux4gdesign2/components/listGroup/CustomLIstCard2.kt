package com.example.ux4gdesign2.components.listGroup

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.ux4gdesign2.R
import com.example.ux4gdesign2.components.switchButtons.CustomSwitchButton
import androidx.core.content.withStyledAttributes
import com.example.ux4gdesign2.components.CircularImageView

class CustomListCard2 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

//    private val icon: ImageView
    private val titleTextView: TextView
    private val supportingTextView: TextView
    private val checkBox: CheckBox
    private val radioBtn: RadioButton
    private val arrowIcon: ImageView
    private val toggleSwitch: CustomSwitchButton
    private val cv: CardView
    private val tvTitleCircle: AppCompatTextView
    private val civ : CircularImageView

    private var isRadioBtnChecked: Boolean = false

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_card2_view, this, true)

//        icon = findViewById(R.id.icon)
        titleTextView       = findViewById(R.id.title)
        supportingTextView  = findViewById(R.id.supportingText)
        checkBox            = findViewById(R.id.checkBoxRight)
        arrowIcon           = findViewById(R.id.arrowIconRight)
        toggleSwitch        = findViewById(R.id.toggleSwitchRight)
        radioBtn            = findViewById(R.id.radioButtonRight)
        cv                  = findViewById(R.id.cv)
        tvTitleCircle       = findViewById<AppCompatTextView>(R.id.tvTitleCircle)
        civ                 = findViewById<CircularImageView>(R.id.civ)

        attrs?.let {
            context.withStyledAttributes(it, R.styleable.CustomListItem) {

                // Set attributes from XML
                titleTextView.text          = getString(R.styleable.CustomListItem_titleText) ?: "List Item"
                titleTextView.textSize      = getDimension(R.styleable.CustomListItem_titleTextFontSize, 14f)
                supportingTextView.text     = getString(R.styleable.CustomListItem_supportingText) ?: ""
                supportingTextView.textSize = getDimension(R.styleable.CustomListItem_supportingTextFontSize, 14f)
                cv.radius                   = getDimension(R.styleable.CustomListItem_cardCornerRadius, 0f)
                val showTitleCircle         = getBoolean(R.styleable.CustomListItem_showTitleCircle, false)
                val showImageCircle         = getBoolean(R.styleable.CustomListItem_showImageCircle, false)

                if (showTitleCircle) {
                    tvTitleCircle.visibility = VISIBLE
                    civ.visibility = GONE
                } else {
                    tvTitleCircle.visibility = GONE
                }

                if (showImageCircle) {
                    civ.visibility = VISIBLE
                    tvTitleCircle.visibility = GONE
                } else {
                    civ.visibility = GONE
                }

                titleTextView.setTextColor(
                    getColor(
                        R.styleable.CustomListItem_titleColor,
                        titleTextView.currentTextColor
                    )
                )
                supportingTextView.setTextColor(
                    getColor(
                        R.styleable.CustomListItem_supportingTextColor,
                        supportingTextView.currentTextColor
                    )
                )
                cv.setCardBackgroundColor(
                    getColor(
                        R.styleable.CustomListItem_cardBackgroundColor,
                        ContextCompat.getColor(context, R.color.white)
                    )
                )


//            icon.setImageResource(typedArray.getResourceId(R.styleable.CustomListItem_iconSrc, R.drawable.ic_launcher_foreground))

                checkBox.visibility             = if (getBoolean(R.styleable.CustomListItem_showCheckBox, false ) ) VISIBLE else GONE
                arrowIcon.visibility            = if (getBoolean(R.styleable.CustomListItem_showArrow, false)) VISIBLE else GONE
                toggleSwitch.visibility         = if (getBoolean(R.styleable.CustomListItem_showSwitch, false)) VISIBLE else GONE
                radioBtn.visibility             = if (getBoolean(R.styleable.CustomListItem_showRadioBtn, false )) VISIBLE else GONE
                supportingTextView.visibility   = if (supportingTextView.text != "") VISIBLE else GONE

            }
        }
    }

    /** Getters for programmatic access **/
    fun setOnArrowClickListener(listener: OnClickListener) {
        return arrowIcon.setOnClickListener(listener)
    }

    fun setOnCheckBoxClickListener(listener: (Boolean) -> Unit) {

        checkBox.setOnCheckedChangeListener { a, isChecked ->
            listener(isChecked)
        }
    }

    fun setOnRadioButtonCheckedListener(listener: (Boolean) -> Unit) {
        radioBtn.setOnCheckedChangeListener { a, isChecked ->
           listener(isChecked)
        }
    }

    fun setOnRadioButtonClickListener(listener: (Boolean) -> Unit) {
        radioBtn.setOnClickListener {
            isRadioBtnChecked = !isRadioBtnChecked
            radioBtn.isChecked = isRadioBtnChecked
            listener(radioBtn.isChecked)
        }
    }

    fun setOnSwitchToggleListener(listener: (Boolean) -> Unit) {
        toggleSwitch.setOnCheckedChangeListener { _, isChecked ->
            listener(isChecked)
        }
    }


    /** Setters for programmatic updates **/
    fun setTitleText(text: String) {
        titleTextView.text = text
    }

    fun setSubTitleText(text: String) {
        supportingTextView.text = text
    }

    fun setTitleTextColor(color: Int) {
        titleTextView.setTextColor(color)
    }

    fun setSubTitleTextColor(color: Int) {
        supportingTextView.setTextColor(color)
    }

    fun setImageResource(resId: Int) {
        civ.setImageResource(resId)
    }

    fun setShowGreenDot(isActive: Boolean) {
        civ.setShowGreenDot(isActive)
    }

    fun setTitleCircleText(text: String) {
        tvTitleCircle.text = text
    }

    fun setImageSize(size: Int) {
        val layoutParams = civ.layoutParams
        layoutParams.width = size
        layoutParams.height = size
        civ.layoutParams = layoutParams
    }

   /* fun setInteractionListener(listener: OnCustomListCardInteractionListener) {
        arrowIcon.setOnClickListener { listener.onArrowClick() }
        checkBox.setOnClickListener { listener.onCheckBoxClick(checkBox.isChecked) }
        radioBtn.setOnClickListener {
            radioBtn.isChecked = !radioBtn.isChecked
            listener.onRadioButtonClick(radioBtn.isChecked)
        }
    }*/

}

/*interface OnCustomListCardInteractionListener {
    fun onArrowClick()
    fun onCheckBoxClick(isChecked: Boolean)
    fun onRadioButtonClick(isChecked: Boolean)
}*/

