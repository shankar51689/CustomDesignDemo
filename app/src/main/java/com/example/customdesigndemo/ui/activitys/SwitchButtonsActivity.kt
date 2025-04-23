package com.example.customdesigndemo.ui.activitys

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.customdesigndemo.R
import com.example.customdesigndemo.databinding.ActivitySwitchButotnsBinding

class SwitchButtonsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySwitchButotnsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_switch_butotns)

        binding.mySwitchBtn.setOnCheckedChangeListener { buttonView, isChecked ->
            Log.i("TaG","====================>${isChecked}")
        }
    }
}