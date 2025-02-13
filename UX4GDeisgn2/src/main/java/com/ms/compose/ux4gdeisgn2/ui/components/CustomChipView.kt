package com.ms.compose.ux4gdeisgn2.ui.components


import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.ms.compose.ux4gdeisgn2.R
import com.ms.compose.ux4gdeisgn2.databinding.CustomViewChipBinding

class CustomChipView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: CustomViewChipBinding = CustomViewChipBinding.inflate(LayoutInflater.from(context), this)

    init {
        orientation = HORIZONTAL
        setChipStyle()
        applyAttributes(attrs)
    }

    private fun setChipStyle(chipBackGroundColor : Int? = null) {
        var background = GradientDrawable().apply {
            setColor(chipBackGroundColor ?: ContextCompat.getColor(context, R.color.colorPrimary))
            cornerRadius = resources.getDimension(R.dimen.chip_corner_radius)
        }
        setBackground(background)
//        binding.chipContainer.background = background
    }

    private fun applyAttributes(attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CustomChipView)
            val text = typedArray.getString(R.styleable.CustomChipView_chipText)
            val iconResId = typedArray.getResourceId(R.styleable.CustomChipView_chipIcon, 0)
            val chipBackGroundColor = typedArray.getColor(R.styleable.CustomChipView_chipBackgroundColor, ContextCompat.getColor(context, R.color.colorPrimary))

            setChipStyle(chipBackGroundColor)
//            setBackgroundColor(chipBackGroundColor)

            typedArray.recycle()


            text?.let { setTextView(it) }
//            chipBackGroundColor.let { color ->  binding.chipContainer.setBackgroundColor(color)}
            if (iconResId != 0) {
                setIcon(iconResId)
                binding.chipIconCard.visibility = View.VISIBLE
            } else {
                binding.chipIconCard.visibility = View.GONE
            }
        }
    }

    var text: String
        get() = binding.chipText.text.toString()
        set(value) {
            binding.chipText.text = value
        }

    private fun setTextView(text: String) {
        binding.chipText.text = text
    }

    fun setIcon(iconRes: Int) {
        binding.chipIcon.setImageResource(iconRes)
    }

    fun setOnChipClickListener(listener: () -> Unit) {
        setOnClickListener { listener() }
    }
}
