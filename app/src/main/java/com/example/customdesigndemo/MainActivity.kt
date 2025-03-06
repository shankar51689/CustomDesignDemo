package com.example.customdesigndemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.GravityCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import com.example.customdesigndemo.databinding.ActivityMainBinding
import com.google.android.material.chip.ChipGroup
//import com.ms.compose.ux4gdeisgn2.ui.components.DialogUtils
//import com.ms.compose.ux4gdeisgn2.ui.components.chips.CustomChipView
//import com.ms.compose.ux4gdeisgn2.ui.components.editTexts.CustomEditText
//import com.ms.compose.ux4gdeisgn2.ui.components.editTexts.DrawableClickListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


//import com.example.ux4gdesign.TextStyle
//import com.example.ux4gdesign.TypographyManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

       /* binding.mySwitchBtn.setOnCheckedChangeListener { buttonView, isChecked ->
            Log.i("TaG","====================>${isChecked}")
        }*/

        // Enable the drawer toggle icon
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(android.R.drawable.ic_menu_sort_by_size)

        //setClickListener()
    }

    /*@SuppressLint("ClickableViewAccessibility")
    private fun setClickListener() {

        *//*val chipContainer: ChipGroup = findViewById(R.id.chip_group)
        val chip = CustomChipView(this)
        chip.setChipText("Dynamic Chip")
        chip.setChipIcon(com.ms.compose.ux4gdeisgn2.R.drawable.ic_plus)
        chipContainer.addView(chip)*//*

        val customEditText = findViewById<CustomEditText>(R.id.customEditText)

        customEditText.setTextWatcher(object : android.text.TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })



        binding.ftError.setOnClickListener {
            customEditText.state = CustomEditText.State.ERROR
            customEditText.setMessage("Error Message")

            val input = customEditText.text
        }

        binding.ftSuccess.setOnClickListener {
            customEditText.state = CustomEditText.State.SUCCESS
            customEditText.setMessage("Success Message")

            val input = customEditText.text
        }

        binding.ftDefault.setOnClickListener {
            customEditText.state = CustomEditText.State.WARNING
            customEditText.setMessage("Warning Message")

            val input = customEditText.text
        }

        binding.btn3View.setOnClickListener {
            *//*DialogUtils.showMaterialDialog(
                this,
                "Confirm Action",
                "Are you sure you want to delete this item?",
                positiveButtonText = "Delete",
                negativeButtonText = "Cancel",
                positiveAction = {
                    // Handle positive button action (e.g., delete item)
                    Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show()
                },
                negativeAction = {
                    // Handle negative button action
                    Toast.makeText(this, "Action canceled", Toast.LENGTH_SHORT).show()
                }
            )*//*

        }

        binding.startProgress.setOnClickListener {
            lifecycleScope.launch {
                for (i in 0..80 step 1) {
                    binding.cpView.setProgress(i.toFloat())
                    binding.lpView.setLinearProgress(i.toFloat())
                    delay(30)
                }

            }
        }

        binding.btn2View.setOnClickListener{
            DialogUtils.showCustomDialog(
                this,
                binding.btn2View,
                "Custom Dialog Title ",
                "This is a custom dialog message.",
                icon = AppCompatResources.getDrawable(this, R.drawable.ic_launcher_background),
                positiveButtonText = "OK",
                negativeButtonText = "Cancel",
                positiveAction = {
                    // Handle positive button action
                    Toast.makeText(this, "Action confirmed", Toast.LENGTH_SHORT).show()
                },
                negativeAction = {
                    // Handle negative button action
                    Toast.makeText(this, "Action canceled", Toast.LENGTH_SHORT).show()
                }
            )

        }

        binding.customEditText.drawableClickListener = object : DrawableClickListener {
            override fun onDrawableStartClick() {
                customEditText.state = CustomEditText.State.DEFAULT
                customEditText.setMessage("Description")
            }

            override fun onDrawableEndClick() {
                customEditText.text.clear()
            }
        }



    }*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START) // Open drawer when clicking icon
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}