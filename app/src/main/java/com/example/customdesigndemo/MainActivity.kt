package com.example.customdesigndemo

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.example.customdesigndemo.databinding.ActivityMainBinding
//import com.ms.compose.ux4gdeisgn2.ui.components.DialogUtils

//import com.example.ux4gdesign.TextStyle
//import com.example.ux4gdesign.TypographyManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

//        val textView: TextView = findViewById<TextView>(R.id.tvComponent)

//        TypographyManager.getTextStyle(this, textView, TextStyle.H1)


        /*binding.tv.text
//        binding.myChip.text
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)*/

        /*binding.ccv.setOnClickListener {
            Log.i("TaG","------------------------")
            *//*val isDraggable = if (binding.ccv.isDragging){
                false
            } else {
                true
            }
            binding.ccv.setDraggable(isDraggable)*//*
        }*/

        // Enable the drawer toggle icon
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(android.R.drawable.ic_menu_sort_by_size)

//        setClickListener()
    }

    /*private fun setClickListener() {
        binding.btn3.setOnClickListener {
            DialogUtils.showMaterialDialog(
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
            )

        }

        binding.btn2.setOnClickListener{
            DialogUtils.showCustomDialog(
                this,
                binding.btn3,
                "Custom Dialog Title",
                "This is a custom dialog message.",
                icon = getDrawable(R.drawable.ic_launcher_background),
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
    }*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START) // Open drawer when clicking icon
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}