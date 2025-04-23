package com.example.customdesigndemo.ui.activitys

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.customdesigndemo.R
import com.example.customdesigndemo.databinding.ActivityButtons2Binding
import com.example.customdesigndemo.databinding.ActivityMainBinding
import com.example.ux4gdesign2.components.editTexts.CustomEditText

class ButtonViewsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityButtons2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_buttons2)




    }
}