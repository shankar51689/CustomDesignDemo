package com.ms.compose.ux4gdeisgn2.ui.components.editTexts

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.text.Editable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.ms.compose.ux4gdeisgn2.R

class CustomEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val label: TextView
    private val editText: EditText
    private val llContainer: LinearLayoutCompat
    private val container: LinearLayoutCompat
    private val message: TextView
    private val messageIcon: ImageView

    private var errorColor: Int   = ContextCompat.getColor(context, R.color.UX4G_danger)
    private var warningColor: Int = ContextCompat.getColor(context, R.color.UX4G_warning)
    private var successColor: Int = ContextCompat.getColor(context, R.color.UX4G_success)
    private var defaultColor: Int = ContextCompat.getColor(context, R.color.UX4G_neutral_300)

    private var borderColor: Int        = defaultColor
    private var borderWidth: Float      = 1f
    private var cetCornerRadius: Float  = 10f

    var drawableClickListener: DrawableClickListener? = null


    var state: State = State.DEFAULT
        set(value) {
            field = value
            updateState()
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_edit_text, this, true)

        label       = findViewById(R.id.label)
        editText    = findViewById(R.id.editText)
        messageIcon = findViewById(R.id.iv)
        message     = findViewById(R.id.message)
        llContainer = findViewById(R.id.ll)
        container   = findViewById(R.id.container)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomEditText,
            defStyleAttr,
            0
        ).apply {
            try {

                // Label setup
                label.text = getString(R.styleable.CustomEditText_cet_labelText)
                label.setTextSize(TypedValue.COMPLEX_UNIT_PX, getDimension(R.styleable.CustomEditText_cet_labelTextSize, 14f))

                label.setTextColor(getColor(R.styleable.CustomEditText_cet_labelTextColor, ContextCompat.getColor(context, android.R.color.black)))

                // EditText setup
                editText.hint = getString(R.styleable.CustomEditText_hintText)
                editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getDimension(R.styleable.CustomEditText_hintTextSize, 18f))
                editText.setHintTextColor(getColor(R.styleable.CustomEditText_hintTextColor,
                    ContextCompat.getColor(context, android.R.color.darker_gray)))

                // State colors
                errorColor      = getColor(R.styleable.CustomEditText_errorColor, errorColor)
                warningColor    = getColor(R.styleable.CustomEditText_warningColor, warningColor)
                successColor    = getColor(R.styleable.CustomEditText_successColor, successColor)
                defaultColor    = getColor(R.styleable.CustomEditText_defaultColor, defaultColor)

                borderWidth     = getDimension(R.styleable.CustomEditText_cet_border_width, 1f)
                borderColor     = getColor(R.styleable.CustomEditText_cet_border_color, defaultColor)

                cetCornerRadius = getDimension(R.styleable.CustomEditText_cet_cornerRadius, 10f)

                val drawableStart   = getDrawable(R.styleable.CustomEditText_cet_drawableStart)
                val drawableEnd     = getDrawable(R.styleable.CustomEditText_cet_drawableEnd)

                editText.setCompoundDrawablesWithIntrinsicBounds(drawableStart, null, drawableEnd, null)

                // Ensure dynamic width & height handling
                post {
                    updateLayoutParams<LayoutParams> {
                        width = if (width > 0) width else LayoutParams.MATCH_PARENT
                        height = if (height > 48) height else LayoutParams.WRAP_CONTENT
//                        height = LayoutParams.WRAP_CONTENT
                    }
                }

            } catch(e: Exception) {
                e.printStackTrace()
                Log.i("TaG","-=-=-=-=-=-=->${e.message}")
            }finally {
                recycle()
                updateState()
                setListener()
            }
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun setListener() {
        editText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) { // Detect touch event

                val drawableStart = editText.compoundDrawables[0] // Start drawable
                val drawableEnd   = editText.compoundDrawables[2] // End drawable

                drawableEnd?.let {
                    val drawableWidth = it.bounds.width()
                    if (event.rawX >= (editText.right - drawableWidth - editText.paddingEnd)) {
                        // Right drawable (drawableEnd) clicked
                        drawableClickListener?.onDrawableEndClick()
                        state = State.DEFAULT
                        llContainer.visibility = View.GONE
                        return@setOnTouchListener true
                    }
                }

                drawableStart?.let {
                    val drawableWidth = it.bounds.width()
                    if (event.rawX <= (editText.left + drawableWidth + editText.paddingStart + 50)) {
                        drawableClickListener?.onDrawableStartClick()
                        return@setOnTouchListener true
                    }
                }
                }
            false // Pass event to other listeners if not handled
        }
    }

    private fun updateState() {
        when (state) {
            State.DEFAULT -> {
                borderColor = defaultColor
                message.setTextColor(defaultColor)
                messageIcon.setImageResource(R.drawable.ic_desc)
                message.isVisible = true
            }
            State.ERROR -> {
                borderColor = errorColor
                message.setTextColor(errorColor)
                messageIcon.setImageResource(R.drawable.ic_error)
                message.isVisible = true
            }
            State.WARNING -> {
                borderColor = warningColor
                message.setTextColor(warningColor)
                messageIcon.setImageResource(R.drawable.ic_warning)
                message.isVisible = true
            }
            State.SUCCESS -> {
                borderColor = successColor
                message.setTextColor(successColor)
                messageIcon.setImageResource(R.drawable.ic_success)
                message.isVisible = true
            }
        }
        updateBorder()
    }

    fun setMessage(text: String) {
        message.text = text
        llContainer.visibility = View.VISIBLE
    }

    var text: Editable = editText.text

    enum class State {
        DEFAULT, ERROR, WARNING, SUCCESS
    }

    private fun updateBorder() {
        val borderDrawable = GradientDrawable().apply {
            setColor(ContextCompat.getColor(context, R.color.UX4G_neutral_50))
            setStroke(borderWidth.toInt(), borderColor)
            cornerRadius = cetCornerRadius
        }
        container.background    = borderDrawable
//        container.minimumHeight = 48.dpToPx(context)
    }

    val editTextView: EditText
        get() = editText


    fun setTextWatcher(textWatcher: android.text.TextWatcher) {
        return editText.addTextChangedListener(textWatcher)
    }

}
