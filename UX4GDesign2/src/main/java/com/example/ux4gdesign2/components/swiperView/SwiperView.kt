package com.example.ux4gdesign2.components.swiperView

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.example.ux4gdesign2.R
import com.google.android.material.tabs.TabLayout

class SwiperView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val viewPager: ViewPager2
    private val tabLayout: TabLayout
    private var adapter: SwiperAdapter? = null
    private var isInfiniteScrollEnabled = true
    private var autoPlayHandler: Handler? = null
    private var autoPlayRunnable: Runnable? = null
    private var imageResources = listOf<Int>()
    private var currentPage = 0

    init {
        LayoutInflater.from(context).inflate(R.layout.swiper_component, this, true)
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabDots)

        // Add TabLayout selection listener
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                if (isInfiniteScrollEnabled) {
                    // For infinite scroll, map to the correct ViewPager position
                    val targetPosition = when (position) {
                        0 -> 1  // first real item
                        imageResources.size - 1 -> imageResources.size  // last real item
                        else -> position + 1
                    }
                    viewPager.setCurrentItem(targetPosition, true)
                } else {
                    viewPager.setCurrentItem(position, true)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })


        // Setup ViewPager2
        viewPager.offscreenPageLimit = 1
        viewPager.clipToPadding = false
        viewPager.setPadding(40, 0, 40, 0)

        // Configure TabLayout for dots
        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.tabGravity = TabLayout.GRAVITY_CENTER
        tabLayout.setSelectedTabIndicator(null as Drawable?)
    }

    private fun setupDots(count: Int) {
        tabLayout.removeAllTabs()
        for (i in 0 until count) {
            val tab = tabLayout.newTab()
            val dotView = LayoutInflater.from(context).inflate(R.layout.dot_tab, null)
            tab.customView = dotView
            tabLayout.addTab(tab, i == 0) // Select first tab initially
        }
    }

    private fun updateDotAppearance(dotView: View, isSelected: Boolean) {
        val dotSelected = dotView.findViewById<ImageView>(R.id.dot_selected)
        val dotUnselected = dotView.findViewById<ImageView>(R.id.dot_unselected)


        dotSelected.visibility = if (isSelected) VISIBLE else INVISIBLE
        dotUnselected.visibility = if (isSelected) INVISIBLE else VISIBLE

    }

    fun setImages(imageResources: List<Int>) {
        this.imageResources = imageResources
        stopAutoPlay()

        if (imageResources.isEmpty()) {
            viewPager.adapter = null
            tabLayout.removeAllTabs()
            return
        }

        adapter = SwiperAdapter(context, imageResources)
        viewPager.adapter = adapter
        setupDots(imageResources.size)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateCurrentPage(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                if (isInfiniteScrollEnabled && state == ViewPager2.SCROLL_STATE_IDLE) {
                    val itemCount = adapter?.itemCount ?: 1
                    when (viewPager.currentItem) {
                        0 -> viewPager.setCurrentItem(itemCount - 2, false)
                        itemCount - 1 -> viewPager.setCurrentItem(1, false)
                    }
                }
            }
        })

        if (isInfiniteScrollEnabled && imageResources.size > 1) {
            viewPager.setCurrentItem(1, false)
            updateCurrentPage(1)
        } else {
            updateCurrentPage(0)
        }
    }

    private fun updateCurrentPage(viewPagerPosition: Int) {
        currentPage = if (isInfiniteScrollEnabled) {
            when (viewPagerPosition) {
                0 -> imageResources.size - 1  // first fake item
                (adapter?.itemCount ?: 1) - 1 -> 0  // last fake item
                else -> viewPagerPosition - 1  // actual items (1-based)
            }
        } else {
            viewPagerPosition
        }

        // Select the corresponding tab programmatically
        if (currentPage in 0 until tabLayout.tabCount) {
            val tab = tabLayout.getTabAt(currentPage)
            tab?.select()
        }

        // Update all dot appearances
        for (i in 0 until tabLayout.tabCount) {
            val tab = tabLayout.getTabAt(i)
            tab?.customView?.let { dotView ->
                updateDotAppearance(dotView, i == currentPage)
            }
        }
    }

    fun setAutoPlay(enable: Boolean, interval: Long = 3000) {
        stopAutoPlay()

        if (enable && imageResources.size > 1) {
            autoPlayHandler = Handler(Looper.getMainLooper())
            autoPlayRunnable = object : Runnable {
                override fun run() {
                    val nextItem = viewPager.currentItem + 1
                    viewPager.setCurrentItem(nextItem, true)
                    autoPlayHandler?.postDelayed(this, interval)
                }
            }
            autoPlayHandler?.postDelayed(autoPlayRunnable!!, interval)
        }
    }


    private fun stopAutoPlay() {
        autoPlayHandler?.removeCallbacks(autoPlayRunnable ?: return)
        autoPlayRunnable = null
    }

    fun setInfiniteScroll(enable: Boolean) {
        isInfiniteScrollEnabled = enable
        if (enable && imageResources.size > 1) {
            viewPager.setCurrentItem(1, false)
        }
    }

    fun setOnImageClickListener(listener: (position: Int) -> Unit) {
        adapter?.setOnItemClickListener(listener)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopAutoPlay()
        viewPager.adapter = null
    }
}