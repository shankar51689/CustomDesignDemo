package com.example.ux4gdesign2.components.swiperView

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.ux4gdesign2.R

class AutoImageSlider @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val viewPager: ViewPager2
    private val dotsContainer: LinearLayout
    private val handler = Handler(Looper.getMainLooper())
    private var autoScrollInterval: Long = 3000
    private var activeColor: Int = Color.BLACK
    private var inactiveColor: Int = Color.GRAY
    private var sliderRunnable: Runnable? = null
    private var imageUrls: List<String> = emptyList()

    init {
        LayoutInflater.from(context).inflate(R.layout.auto_image_slider, this, true)

        viewPager = findViewById(R.id.imageSlider)
        dotsContainer = findViewById(R.id.dotsContainer)

        context.theme.obtainStyledAttributes(attrs, R.styleable.AutoImageSlider, 0, 0).apply {
            try {
                autoScrollInterval = getInteger(R.styleable.AutoImageSlider_autoScrollInterval, 3000).toLong()
                activeColor = getColor(R.styleable.AutoImageSlider_dotActiveColor, Color.BLACK)
                inactiveColor = getColor(R.styleable.AutoImageSlider_dotInactiveColor, Color.GRAY)
            } finally {
                recycle()
            }
        }
    }

    fun setImageUrls(urls: List<String>) {
        imageUrls = urls
        viewPager.adapter = ImageSliderAdapter(imageUrls)
        setupDots()
        setCurrentDot(0)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                setCurrentDot(position)
            }
        })

        startAutoScroll()
    }

    private fun setupDots() {
        dotsContainer.removeAllViews()
        for (i in imageUrls.indices) {
            val dot = View(context).apply {
                layoutParams = LinearLayout.LayoutParams(24, 8).apply {
                    setMargins(8, 0, 8, 0)
                }
                background = createDotDrawable(inactiveColor)
            }
            dotsContainer.addView(dot)
        }
    }

    private fun setCurrentDot(index: Int) {
        for (i in 0 until dotsContainer.childCount) {
            dotsContainer.getChildAt(i).background = createDotDrawable(if (i == index) activeColor else inactiveColor)
        }
    }

    private fun startAutoScroll() {
        sliderRunnable = object : Runnable {
            override fun run() {
                if (imageUrls.isNotEmpty()) {
                    val next = (viewPager.currentItem + 1) % imageUrls.size
                    viewPager.setCurrentItem(next, true)
                    handler.postDelayed(this, autoScrollInterval)
                }
            }
        }
        handler.postDelayed(sliderRunnable!!, autoScrollInterval)
    }

    private fun createDotDrawable(color: Int): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setSize(24, 8)
            setColor(color)
        }
    }

    override fun onDetachedFromWindow() {
        handler.removeCallbacks(sliderRunnable!!)
        super.onDetachedFromWindow()
    }
}
