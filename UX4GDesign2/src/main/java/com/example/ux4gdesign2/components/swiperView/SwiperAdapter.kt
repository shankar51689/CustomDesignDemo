package com.example.ux4gdesign2.components.swiperView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ux4gdesign2.R
import com.bumptech.glide.request.target.Target

/**
 * Adapter for a RecyclerView that displays a swiper view with images.
 * Supports infinite scrolling and efficient image loading using Glide.
 *
 * @property context The context in which the adapter is used.
 * @property images A list of image resource IDs to be displayed in the swiper.
 */
class SwiperAdapter(
    private val context: Context,
    private val images: List<Int>
) : RecyclerView.Adapter<SwiperAdapter.ViewHolder>() {

    // Listener for item click events
    private var itemClickListener: ((Int) -> Unit)? = null

    /**
     * ViewHolder class for holding the views of each item in the RecyclerView.
     *
     * @param view The root view of the item layout.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView) // ImageView to display the image
    }

    /**
     * Inflates the layout for each item in the RecyclerView.
     *
     * @param parent The parent ViewGroup.
     * @param viewType The type of the view (not used here).
     * @return A ViewHolder containing the inflated layout.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.swiper_item, parent, false)
        return ViewHolder(view)
    }

    /**
     * Binds data to the views in the ViewHolder for the given position.
     *
     * @param holder The ViewHolder containing the views.
     * @param position The position of the item in the RecyclerView.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Calculate the actual position in the image list for infinite scrolling
        val actualPosition = when {
            position == 0 -> images.size - 1 // First fake item shows last real item
            position > images.size -> 0 // Last fake item shows first real item
            else -> position - 1
        }

        // Load the image into the ImageView using Glide
        Glide.with(context)
            .load(images[actualPosition])
            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            .into(holder.imageView)

        // Set a click listener for the item
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(actualPosition)
        }
    }

    /**
     * Returns the total number of items in the RecyclerView.
     * Adds 2 extra items for infinite scrolling (clones of the first and last items).
     *
     * @return The total number of items.
     */
    override fun getItemCount(): Int {
        return if (images.size > 1) images.size + 2 else images.size
    }

    /**
     * Sets a listener for item click events.
     *
     * @param listener A lambda function to be invoked when an item is clicked.
     */
    fun setOnItemClickListener(listener: (Int) -> Unit) {
        itemClickListener = listener
    }
}