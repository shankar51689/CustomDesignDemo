package com.example.customdesigndemo.ui.activitys

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.customdesigndemo.R
import com.example.customdesigndemo.databinding.ActivityCircularImageViewBinding
import kotlin.math.log

class CircularImageViewActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCircularImageViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_circular_image_view)


        val drawable: Drawable = ContextCompat.getDrawable(this, R.drawable.ic_launcher_background)!!


        with(binding) {
            aiv.setOnClickListener { Log.i("TaG", "onCreate: aiv ") }
//            aiv.setImageBitmap(drawableToBitmap(drawable))

//            aiv.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_launcher_background))

            civ1.setOnClickListener { Log.i("TaG", "onCreate: civ1 ") }
            civ2.setOnClickListener { Log.i("TaG", "onCreate: civ2 ") }

//            civ1.setImageBitmap(drawableToBitmap(drawable))
//            civ2.setImageBitmap(drawableToBitmap(drawable))

//            civ1.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_launcher_background))
//            civ2.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_launcher_background))
        }

    }

    fun drawableToBitmap(drawable: Drawable): Bitmap {
        val width = drawable.intrinsicWidth.takeIf { it > 0 } ?: 1
        val height = drawable.intrinsicHeight.takeIf { it > 0 } ?: 1

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }

}