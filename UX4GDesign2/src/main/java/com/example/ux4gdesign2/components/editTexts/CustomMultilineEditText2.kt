package com.example.ux4gdesign2.components.editTexts

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.ux4gdesign2.R

class CustomMultilineEditText2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var label: TextView
    private lateinit var editText: EditText
    private lateinit var charCount: TextView

    private var maxCharacterCount   = 200
    private var strokeColor: Int    = Color.GRAY // Default stroke color

    init {
        orientation = VERTICAL
        inflate(context, R.layout.view_custom_multiline_edittext, this)
        initializeViews()
        setupListeners()
        setupAttributes(attrs)
    }

    private fun initializeViews() {
        label       = findViewById(R.id.tvLabel)
        editText    = findViewById(R.id.etMultiline)
        charCount   = findViewById(R.id.tvCharCount)
        updateCharacterCount(0)
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomMultiLineEditText)

        try {

            label.text          = typedArray.getString(R.styleable.CustomMultiLineEditText_mlet_labelText) ?: "Label"
            editText.textSize   = typedArray.getFloat(R.styleable.CustomMultiLineEditText_mlet_textSize, 12f)
            editText.hint       = typedArray.getString(R.styleable.CustomMultiLineEditText_mlet_hintText) ?: "Placeholder"
            strokeColor         = typedArray.getColor( R.styleable.CustomMultiLineEditText_mlet_borderColor, ContextCompat.getColor(context, R.color.UX4G_neutral_800) )

        } finally {
            typedArray.recycle()
        }

        updateStrokeColor(strokeColor)
    }

    private fun setupListeners() {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateCharacterCount(s?.length ?: 0)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun updateCharacterCount(count: Int) {
        charCount.text = "$count/$maxCharacterCount"
    }

    fun setLabelText(text: String) {
        label.text = text
    }

    fun setHintText(hint: String) {
        editText.hint = hint
    }

    fun setMaxCharacterCount(count: Int) {
        maxCharacterCount = count
        updateCharacterCount(0)
    }

    fun setStrokeColor(color: Int) {
        strokeColor = color
        updateStrokeColor(color)
    }

    private fun updateStrokeColor(color: Int) {
        val background = GradientDrawable().apply {
//            setColor(Color.WHITE)
            setStroke(3, color)
            cornerRadius = 12f
        }
        editText.background = background
    }

    fun getText(): String {
        return editText.text.toString()
    }

    fun setText(text: String) {
        editText.setText(text)
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

//    fun getText(): Editable? = editText.text
    fun setText(text: CharSequence?) = editText.setText(text)
    fun setHint(hint: CharSequence?) { editText.hint = hint }
    fun getCharCount(): Int = charCount.text.toString().split("/")[0].toInt()
}
