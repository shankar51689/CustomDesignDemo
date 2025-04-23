package com.example.customdesigndemo.ui.activitys

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.customdesigndemo.R
import com.example.customdesigndemo.databinding.ActivitySwiperViewBinding

class SwiperViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySwiperViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_swiper_view)


        with(binding) {
            swiper.setImages(listOf(
                R.drawable.test_image,
                R.drawable.test_image2,
                R.drawable.test_image,
                R.drawable.test_image,
                R.drawable.test_image3
            ))
            swiper.setAutoPlay(true)
            swiper.setInfiniteScroll(true)
            swiper.setOnImageClickListener { position ->
                Toast.makeText(this@SwiperViewActivity, "Clicked on image at position: $position", Toast.LENGTH_SHORT).show()
            }

            swiper.setAutoPlay(true, interval = 3000L) // Longer interval
            swiper.setInfiniteScroll(true)

        }


    }

}