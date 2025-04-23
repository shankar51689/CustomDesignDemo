package com.example.customdesigndemo.ui.activitys

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.customdesigndemo.R
import com.example.customdesigndemo.databinding.ActivityRangeSlidersBinding
import com.example.ux4gdesign2.components.rangeBar.CustomRangeSlider
import kotlin.math.absoluteValue

class RangeSlidersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRangeSlidersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_range_sliders)

        binding.customRange.setOnValueChangeListener {
            Log.i("TaG","${it.absoluteValue}")
        }

        binding.rangeSilder2.setOnRangeChangeListener(object : CustomRangeSlider.OnRangeChangeListener {
            override fun onValuesChanged(leftValue: Float, rightValue: Float) {
                Log.i("TaG","-=-=-=-=--=-=-=-==-==>${leftValue} == ${rightValue}")
            }

        })
    }
}