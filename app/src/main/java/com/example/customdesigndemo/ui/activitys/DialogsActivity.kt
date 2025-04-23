package com.example.customdesigndemo.ui.activitys

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import com.example.customdesigndemo.R
import com.example.customdesigndemo.databinding.ActivityDialogsBinding
import com.example.ux4gdesign2.components.DialogUtils

class DialogsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDialogsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dialogs)


        /*binding.btn3View.setOnClickListener {
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

        }*/


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
    }
}