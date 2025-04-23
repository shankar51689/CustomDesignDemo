package com.example.customdesigndemo.ui.activitys

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.customdesigndemo.R
import com.example.customdesigndemo.databinding.ActivityEditFieldsBinding
import com.example.ux4gdesign2.components.editTexts.CustomEditText
import com.example.ux4gdesign2.components.editTexts.CustomMultiLineEditText
import com.example.ux4gdesign2.components.editTexts.DrawableClickListener

class EditFieldsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditFieldsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_edit_fields)


        val customEditText = findViewById<CustomEditText>(R.id.customEditText)

        customEditText.setTextWatcher(object : android.text.TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })


        binding.customEditText.drawableClickListener = object : DrawableClickListener {
            override fun onDrawableStartClick() {
                customEditText.state = CustomEditText.State.DEFAULT
                customEditText.setMessage("Description")
            }

            override fun onDrawableEndClick() {
                customEditText.text.clear()
            }
        }

        binding.customMultiLineEditText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                Log.i("TaG","$s")
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.customMultiLineEditText.setOnFocusChangeListener { v, hasFocus ->

           /* if (hasFocus) {
                binding.customMultiLineEditText.state = CustomMultiLineEditText.State.SUCCESS
            }*/

        }


        /*binding.ftError.setOnClickListener {
            customEditText.state = CustomEditText.State.ERROR
            customEditText.setMessage("Error Message")

            val input = customEditText.text
        }

        binding.ftSuccess.setOnClickListener {
            customEditText.state = CustomEditText.State.SUCCESS
            customEditText.setMessage("Success Message")

            val input = customEditText.text
        }

        binding.ftDefault.setOnClickListener {
            customEditText.state = CustomEditText.State.WARNING
            customEditText.setMessage("Warning Message")

            val input = customEditText.text
        }*/
    }
}