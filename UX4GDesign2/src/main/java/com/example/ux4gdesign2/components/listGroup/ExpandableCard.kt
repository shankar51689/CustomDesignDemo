package com.example.ux4gdesign2.components.listGroup

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.ux4gdesign2.R
import androidx.core.content.withStyledAttributes

class ExpandableCard @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var isExpanded = false
    private var headerLayout: LinearLayout
    private var contentLayout: LinearLayout
    private var tvTitle: TextView
    private var tvContent: TextView
    private var ivArrow: ImageView
    private var cardContainer: LinearLayout

    private var cardCornerRadius: Float = 8f
    private var cardBackgroundColor: Int = ContextCompat.getColor(context, R.color.UX4G_neutral_50)
    private var cardStrokeWidth: Float = 2f
    private var cardStrokeColor: Int = ContextCompat.getColor(context, R.color.UX4G_neutral_100)

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_expandable_card, this, true)

        headerLayout = findViewById(R.id.headerLayout)
        contentLayout = findViewById(R.id.contentLayout)
        tvTitle = findViewById(R.id.tvTitle)
        tvContent = findViewById(R.id.tvContent)
        ivArrow = findViewById(R.id.ivArrow)
        cardContainer = findViewById(R.id.cardContainer)

        headerLayout.setOnClickListener {
            toggle()
        }

        attrs?.let {
            context.withStyledAttributes(it, R.styleable.CustomListItem) {

                // Text attributes
                tvTitle.text = getString(R.styleable.CustomListItem_titleText) ?: "Title"
                tvContent.text = getString(R.styleable.CustomListItem_supportingText) ?: "Content goes here"

                // Text color
                tvTitle.setTextColor(
                    getColor(
                        R.styleable.CustomListItem_titleColor,
                        tvTitle.currentTextColor
                    )
                )
                tvContent.setTextColor(
                    getColor(
                        R.styleable.CustomListItem_supportingTextColor,
                        tvContent.currentTextColor
                    )
                )

                // Text size
                tvTitle.textSize = getDimension(R.styleable.CustomListItem_titleTextFontSize, 14f)
                tvContent.textSize = getDimension(R.styleable.CustomListItem_supportingTextFontSize, 14f)

                // Card properties
                cardCornerRadius = getDimension(R.styleable.CustomListItem_cardCornerRadius, 8f)
                cardBackgroundColor = getColor(R.styleable.CustomListItem_cardBackgroundColor, cardBackgroundColor)
                cardStrokeWidth = getDimension(R.styleable.CustomListItem_cardStrokeWidth, 2f)
                cardStrokeColor = getColor(R.styleable.CustomListItem_cardStrokeColor, cardStrokeColor)
            }
        }

        applyCustomizations()
    }

    private fun applyCustomizations() {
        val backgroundDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = cardCornerRadius
            setColor(cardBackgroundColor)
            setStroke(cardStrokeWidth.toInt(), cardStrokeColor)
        }
        cardContainer.background = backgroundDrawable
    }

    fun toggle() {
        isExpanded = !isExpanded
        contentLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE
        animateArrow(isExpanded)
    }

    private fun animateArrow(expand: Boolean) {
        val from = if (expand) 0f else -180f
        val to = if (expand) -180f else 0f
        val rotateAnimation = RotateAnimation(
            from, to,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 300
            fillAfter = true
        }
        ivArrow.startAnimation(rotateAnimation)
    }

    fun setTitle(title: String) {
        tvTitle.text = title
    }

    fun setContent(content: String) {
        tvContent.text = content
    }

    fun setCornerRadius(radius: Float) {
        cardCornerRadius = radius
        applyCustomizations()
    }

    fun setCardBackgroundColor(color: Int) {
        cardBackgroundColor = color
        applyCustomizations()
    }

    fun setStrokeWidth(width: Float) {
        cardStrokeWidth = width
        applyCustomizations()
    }

    fun setStrokeColor(color: Int) {
        cardStrokeColor = color
        applyCustomizations()
    }
}
