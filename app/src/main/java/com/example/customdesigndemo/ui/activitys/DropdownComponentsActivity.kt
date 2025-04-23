package com.example.customdesigndemo.ui.activitys

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.customdesigndemo.R
import com.example.ux4gdesign2.components.dropdownComponent.CustomDropdownView
import com.example.ux4gdesign2.components.dropdownComponent.MaterialStyledDropdown

class DropdownComponentsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dropdown_components)

        val dropdown = findViewById<CustomDropdownView>(R.id.customDropdown)
        dropdown.setMenuItems(listOf("Option 1", "Option 2", "Option 3"))

        dropdown.setOnItemClickListener { selectedItem ->
            Log.i("TaG", "Clicked: =-=-=-=-=-==-=-=?> $selectedItem")
        }

        val dropdown2 = findViewById<MaterialStyledDropdown>(R.id.customMaterialDropdown)

//        dropdown2.setHint("Choose item")
//        dropdown2.setItems(listOf("menu item", "menu item\nmenu item", "menu item\nmenu item\nmenu item"))
        dropdown2.setItems(listOf("Item 1", "Item 2", "Item 3","Item 1", "Item 2", "Item 3","Item 1", "Item 2", "Item 3"))
        dropdown2.setOnItemSelectedListener { selected ->
            Log.i("TaG", "Clicked:2 =-=-=-=-=-==-=-=?> $selected")
            Toast.makeText(this, "Selected: $selected", Toast.LENGTH_SHORT).show()
        }
    }
}