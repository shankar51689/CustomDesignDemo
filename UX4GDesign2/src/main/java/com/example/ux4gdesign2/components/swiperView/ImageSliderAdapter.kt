package com.example.ux4gdesign2.components.swiperView

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ImageSliderAdapter(private val urls: List<String>) :
    RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(val imageView: ImageView) : RecyclerView.ViewHolder(imageView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val image = ImageView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        return ImageViewHolder(image)
    }

    override fun getItemCount(): Int = urls.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Glide.with(holder.imageView.context)
            .load(urls[position])
            .into(holder.imageView)
    }
}
