package com.example.ux4gdesign2.components.editTexts

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.ux4gdesign2.R
import com.example.ux4gdesign2.components.editTexts.CustomEditText.State

class CustomMultiLineEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val label: TextView
    private val editText: EditText
    private val charCount: TextView
    private val container: LinearLayoutCompat
    private var strokeColor: Int = Color.GRAY
    private var strokeWidth: Float = 2f

    private var errorColor: Int   = ContextCompat.getColor(context, R.color.UX4G_danger)
    private var warningColor: Int = ContextCompat.getColor(context, R.color.UX4G_warning)
    private var successColor: Int = ContextCompat.getColor(context, R.color.UX4G_success)
    private var defaultColor: Int = ContextCompat.getColor(context, R.color.UX4G_neutral_300)

    var state: State = State.DEFAULT
        set(value) {
            field = value
            updateState()
        }

    init {
        orientation = VERTICAL
        inflate(context, R.layout.custom_multiline_edittext, this)

        label     = findViewById(R.id.label)
        editText  = findViewById(R.id.edit_text)
        charCount = findViewById(R.id.char_count)
        container = findViewById(R.id.llContainer)


        setupAttributes(attrs)
        setupEditTextListener()
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomMultiLineEditText)

        val heightAttr = attrs?.getAttributeValue(
            "http://schemas.android.com/apk/res/android", "layout_height"
        )

        Log.i("TaG","=-=-=-=-=-=-=-=-=-=-=-=-=>$heightAttr")
        if (heightAttr == "-2") { // wrap-content
            container.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
//            editText.height = ViewGroup.LayoutParams.WRAP_CONTENT
        } else if (heightAttr == "-1") { // match-parent
            container.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
//            editText.height = ViewGroup.LayoutParams.MATCH_PARENT
        } else {
            val typedArray = context.obtainStyledAttributes(attrs, intArrayOf(android.R.attr.layout_height))
            val heightValue = typedArray.getDimensionPixelSize(0, ViewGroup.LayoutParams.WRAP_CONTENT)
            if(heightValue > 0) {
//                editText.height = heightValue
                container.layoutParams.height = heightValue
            }
            typedArray.recycle()
        }

        label.text          = typedArray.getString(R.styleable.CustomMultiLineEditText_mlet_labelText) ?: "Label"
        label.setTextSize(TypedValue.COMPLEX_UNIT_PX,typedArray.getDimension(R.styleable.CustomMultiLineEditText_mlet_labelText_size, 28f))
//            label.textSize      = typedArray.getDimension(R.styleable.CustomMultiLineEditText_mlet_labelText_size, 12f)
        editText.hint       = typedArray.getString(R.styleable.CustomMultiLineEditText_mlet_hintText) ?: "Placeholder"
        editText.textSize   = typedArray.getDimension(R.styleable.CustomMultiLineEditText_mlet_textSize, 14f)
        strokeColor         = typedArray.getColor( R.styleable.CustomMultiLineEditText_mlet_borderColor, Color.GRAY )
        strokeWidth         = typedArray.getDimension( R.styleable.CustomMultiLineEditText_mlet_strokeWidth, 2f)

        updateStrokeColor(strokeColor)
        typedArray.recycle()
    }

    private fun setupEditTextListener() {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                charCount.text = "${s?.length ?: 0}/200"
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun updateState() {
        when (state) {
            State.DEFAULT -> {
                setStrokeColor(defaultColor)
            }
            State.ERROR -> {
                setStrokeColor(errorColor)
            }
            State.WARNING -> {
                setStrokeColor(warningColor)
            }
            State.SUCCESS -> {
                setStrokeColor(successColor)
            }
        }
    }

    fun setStrokeColor(color: Int) {
        strokeColor = color
        updateStrokeColor(color)
    }

    private fun updateStrokeColor(color: Int) {
        val background = GradientDrawable().apply {
            setStroke(strokeWidth.toInt(), color)
            cornerRadius = 12f
        }
        editText.background = background
    }

    // ========================== LISTENER SUPPORT ==========================

    fun addTextChangedListener(watcher: TextWatcher) {
        editText.addTextChangedListener(watcher)
    }

    override fun setOnFocusChangeListener(listener: OnFocusChangeListener) {
        editText.onFocusChangeListener = listener
    }

    fun setOnEditorActionListener(listener: TextView.OnEditorActionListener) {
        editText.setOnEditorActionListener(listener)
    }

    override fun setOnKeyListener(listener: OnKeyListener) {
        editText.setOnKeyListener(listener)
    }

    override fun setOnTouchListener(listener: OnTouchListener) {
        editText.setOnTouchListener(listener)
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        editText.setOnClickListener(listener)
    }

    enum class State {
        DEFAULT, ERROR, WARNING, SUCCESS
    }

    fun getText(): Editable? = editText.text
    fun setText(text: CharSequence?) = editText.setText(text)
    fun setHint(hint: CharSequence?) { editText.hint = hint }
    fun getCharCount(): Int = charCount.text.toString().split("/")[0].toInt()
}

