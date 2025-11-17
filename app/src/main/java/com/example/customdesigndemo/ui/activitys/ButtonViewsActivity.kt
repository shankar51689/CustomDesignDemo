package com.example.customdesigndemo.ui.activitys

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.customdesigndemo.R
import com.example.customdesigndemo.databinding.ActivityButtons2Binding
import com.example.customdesigndemo.databinding.ActivityMainBinding
import com.example.ux4gdesign2.components.buttons.FillButton
import com.example.ux4gdesign2.components.editTexts.CustomEditText

class ButtonViewsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityButtons2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_buttons2)


        with(binding){

            btnSubmit.setOnClickListener {

                btnSubmit.showLoader() // default size & color
                btnSubmit.showLoader(Color.WHITE)
                btnSubmit.postDelayed({
                    btnSubmit.hideLoader()
                }, 2000)
            }
//            btnSubmit.hideLoader()

            with(bt1) {
                setOnLongClickListener {
                    Log.i("TaG","bt1 ---- Long Clicked")
                    testClick("bt1 ---- Long Clicked")
                    true
                }

                setOnClickListener {
                    Log.i("TaG","bt1 ----  Clicked")

                    testClick("bt1 ----  Clicked")
                }

            }
            with(bt2) {
                setOnLongClickListener {
                    Log.i("TaG","bt2 ---- Long Clicked")
                    testClick("bt2 ---- Long Clicked")
                    true
                }

                setOnClickListener {
                    Log.i("TaG","bt2 ----  Clicked")

                    testClick("bt2 ----  Clicked")
                }

            }
            with(bt3) {
                setOnLongClickListener {
                    Log.i("TaG","bt3 ---- Long Clicked")
                    testClick("bt3 ---- Long Clicked")
                    true
                }

                setOnClickListener {
                    Log.i("TaG","bt3 ----  Clicked")

                    testClick("bt3 ----  Clicked")
                }

            }
            with(bt4) {
                setOnLongClickListener {
                    Log.i("TaG","bt1 ---- Long Clicked")
                    testClick("bt4 ---- Long Clicked")
                    true
                }

                setOnClickListener {
                    Log.i("TaG","bt1 ----  Clicked")

                    testClick("bt4 ----  Clicked")
                }

            }
            with(bt5) {
                setOnLongClickListener {
                    Log.i("TaG","bt1 ---- Long Clicked")
                    testClick("bt5 ---- Long Clicked")
                    true
                }

                setOnClickListener {
                    Log.i("TaG","bt1 ----  Clicked")

                    testClick("bt5 ----  Clicked")
                }

            }
            with(bt6) {
                setOnLongClickListener {
                    Log.i("TaG","bt1 ---- Long Clicked")
                    testClick("bt6 ---- Long Clicked")
                    true
                }

                setOnClickListener {
                    Log.i("TaG","bt1 ----  Clicked")

                    testClick("bt6 ----  Clicked")
                }

            }


            with(bt7) {
                setOnLongClickListener {
                    Log.i("TaG","bt1 ---- Long Clicked")
                    testClick("bt7 ---- Long Clicked")
                    true
                }

                setOnClickListener {
                    Log.i("TaG","bt1 ----  Clicked")

                    testClick("bt7 ----  Clicked")
                }

            }
            with(bt8) {
                setOnLongClickListener {
                    Log.i("TaG","bt1 ---- Long Clicked")
                    testClick("bt8 ---- Long Clicked")
                    true
                }

                setOnClickListener {
                    Log.i("TaG","bt1 ----  Clicked")

                    testClick("bt8 ----  Clicked")
                }
            }
            with(bt9) {
                setOnLongClickListener {
                    Log.i("TaG","bt1 ---- Long Clicked")
                    testClick("bt9 ---- Long Clicked")
                    true
                }

                setOnClickListener {
                    Log.i("TaG","bt1 ----  Clicked")

                    testClick("bt9 ----  Clicked")
                }

            }
        }


    }

    private fun testClick(s:String) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show()
    }
}

