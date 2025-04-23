package com.example.customdesigndemo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.customdesigndemo.databinding.ActivityMainBinding
import com.example.customdesigndemo.ui.activitys.ButtonViewsActivity
import com.example.customdesigndemo.ui.activitys.CircularImageViewActivity
import com.example.customdesigndemo.ui.activitys.DropdownComponentsActivity
import com.example.customdesigndemo.ui.activitys.EditFieldsActivity
import com.example.customdesigndemo.ui.activitys.ExpandedCardView
import com.example.customdesigndemo.ui.activitys.ListsCardsActivity
import com.example.customdesigndemo.ui.activitys.LoaderViewsActivity
import com.example.customdesigndemo.ui.activitys.ProgressViewActivity
import com.example.customdesigndemo.ui.activitys.RangeSlidersActivity
import com.example.customdesigndemo.ui.activitys.SwiperViewActivity
import com.example.customdesigndemo.ui.activitys.SwitchButtonsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setClickListener()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setClickListener() {

        with(binding) {
            btnView.setOnClickListener {
                nextActivityIntent(ButtonViewsActivity::class.java)
            }

            circularImageView.setOnClickListener {
                nextActivityIntent(CircularImageViewActivity::class.java)
            }

            efView.setOnClickListener {
                nextActivityIntent(EditFieldsActivity::class.java)
            }

            loaderView.setOnClickListener {
                nextActivityIntent(LoaderViewsActivity::class.java)
            }

            progressView.setOnClickListener {
                nextActivityIntent(ProgressViewActivity::class.java)

            }

            rangeSliderView.setOnClickListener {
                nextActivityIntent(RangeSlidersActivity::class.java)
                
            }

            switchBtnView.setOnClickListener {
                nextActivityIntent(SwitchButtonsActivity::class.java)
            }

            listCardsView.setOnClickListener {
                nextActivityIntent(ListsCardsActivity::class.java)
            }

            expandedView.setOnClickListener {
                nextActivityIntent(ExpandedCardView::class.java)
            }

            swiperView.setOnClickListener {
                nextActivityIntent(SwiperViewActivity::class.java)
            }

            dropdownView.setOnClickListener {
                nextActivityIntent(DropdownComponentsActivity::class.java)
            }
        }


    }


    private fun <T> nextActivityIntent(nextActivity: Class<out T>) = startActivity(Intent(this,nextActivity))

}