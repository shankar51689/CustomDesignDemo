package com.example.customdesigndemo.ui.activitys

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.customdesigndemo.R
import com.example.customdesigndemo.databinding.ActivityProgressViewBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProgressViewActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProgressViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_progress_view)


        binding.startProgress.setOnClickListener {
            lifecycleScope.launch {
                for (i in 0..100 step 1) {
                    binding.cpView.setProgress(i.toFloat())
                    binding.lpView.setLinearProgress(i.toFloat())
                    binding.customProgress.setProgress(i)

                    binding.cp2View.setProgress(i.toFloat())
                    binding.cp2View2.setProgress(i.toFloat())
                    binding.cp2View3.setProgress(i.toFloat())
                    binding.cp2View4.setProgress(i.toFloat())
                    binding.cp2View1.setProgress(i.toFloat())

                    binding.hp1.setProgress(i.toFloat())
                    binding.hp2.setProgress(i.toFloat())
                    binding.hp3.setProgress(i.toFloat())
                    binding.hp4.setProgress(i.toFloat())
                    binding.hp5.setProgress(i.toFloat())

                    delay(30)
                }

            }
        }

    }
}