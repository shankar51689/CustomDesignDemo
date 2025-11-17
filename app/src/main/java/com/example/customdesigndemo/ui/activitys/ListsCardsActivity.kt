package com.example.customdesigndemo.ui.activitys

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Switch
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.customdesigndemo.R
import com.example.customdesigndemo.databinding.ActivityListsCardsBinding

class ListsCardsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListsCardsBinding

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_lists_cards)

        // add click listeners to the cards
        with(binding) {
            /*cd1.setOnClickListener {
                Toast.makeText(this@ListsCardsActivity, "Card 1 Clicked", Toast.LENGTH_SHORT).show()
            }
            cd2.setOnClickListener {
                Toast.makeText(this@ListsCardsActivity, "Card 2 Clicked", Toast.LENGTH_SHORT).show()
            }
            cd3.setOnClickListener {
                Toast.makeText(this@ListsCardsActivity, "Card 3 Clicked", Toast.LENGTH_SHORT).show()
            }
            cd4.setOnClickListener {
                Toast.makeText(this@ListsCardsActivity, "Card 4 Clicked", Toast.LENGTH_SHORT).show()
            }
            cd5.setOnClickListener {
                Toast.makeText(this@ListsCardsActivity, "Card 5 Clicked", Toast.LENGTH_SHORT).show()
            }
            cd6.setOnClickListener {
                Toast.makeText(this@ListsCardsActivity, "Card 6 Clicked", Toast.LENGTH_SHORT).show()
            }
            cd7.setOnClickListener {
                Toast.makeText(this@ListsCardsActivity, "Card 7 Clicked", Toast.LENGTH_SHORT).show()
            }
            cd8.setOnClickListener {
                Toast.makeText(this@ListsCardsActivity, "Card 8 Clicked", Toast.LENGTH_SHORT).show()
            }
            cd9.setOnClickListener {
                Toast.makeText(this@ListsCardsActivity, "Card 9 Clicked", Toast.LENGTH_SHORT).show()
            }
            cd10.setOnClickListener {
                Toast.makeText(this@ListsCardsActivity, "Card 10 Clicked", Toast.LENGTH_SHORT).show()
            }*/

        }
        // toggle button click listener
        with(binding){
            cd1.setOnSwitchToggleListener {
                Toast.makeText(this@ListsCardsActivity, "Switch is now cd1 ${it}", Toast.LENGTH_SHORT).show()
            }
            cd8.setOnSwitchToggleListener {
                Toast.makeText(this@ListsCardsActivity, "Switch is now cd8 ${it}", Toast.LENGTH_SHORT).show()
            }
            cd11.setOnSwitchToggleListener {
                Toast.makeText(this@ListsCardsActivity, "Switch is now cd11 ${it}", Toast.LENGTH_SHORT).show()
            }
            cd15.setOnSwitchToggleListener {
                Toast.makeText(this@ListsCardsActivity, "Switch is now cd15 ${it}", Toast.LENGTH_SHORT).show()
            }
        }

        //checkbox click listener
        with(binding) {
            cd2.setOnCheckBoxClickListener {
                Toast.makeText(this@ListsCardsActivity, "Checkbox is now cd2 ${it}", Toast.LENGTH_SHORT).show()
            }
            cd7.setOnCheckBoxClickListener {
                Toast.makeText(this@ListsCardsActivity, "Checkbox is now cd7 ${it}", Toast.LENGTH_SHORT).show()
            }
            cd14.setOnCheckBoxClickListener {
                Toast.makeText(this@ListsCardsActivity, "Checkbox is now cd14 ${it}", Toast.LENGTH_SHORT).show()
            }
            cd16.setOnCheckBoxClickListener {
                Toast.makeText(this@ListsCardsActivity, "Checkbox is now cd16 ${it}", Toast.LENGTH_SHORT).show()
            }
        }

        with(binding){
            cd4.setOnRadioButtonClickListener {
                Toast.makeText(this@ListsCardsActivity, "RadioButton is now cd4 ${it}", Toast.LENGTH_SHORT).show()
            }
            cd5.setOnRadioButtonClickListener {
                Toast.makeText(this@ListsCardsActivity, "RadioButton is now cd5 ${it}", Toast.LENGTH_SHORT).show()
            }
            cd12.setOnRadioButtonClickListener {
                Toast.makeText(this@ListsCardsActivity, "RadioButton is now cd12 ${it}", Toast.LENGTH_SHORT).show()
            }

        }
        // arrow click listener
        with(binding) {
            cd3.setOnArrowClickListener {
                Toast.makeText(this@ListsCardsActivity, "Arrow is now cd3", Toast.LENGTH_SHORT).show()
            }
            cd6.setOnArrowClickListener {
                Toast.makeText(this@ListsCardsActivity, "Arrow is now cd6", Toast.LENGTH_SHORT).show()
            }
            cd13.setOnArrowClickListener {
                Toast.makeText(this@ListsCardsActivity, "Arrow is now cd11", Toast.LENGTH_SHORT).show()
            }
            cd15.setOnArrowClickListener {
                Toast.makeText(this@ListsCardsActivity, "Arrow is now cd15", Toast.LENGTH_SHORT).show()
            }
        }


//        binding.cd3.setOnArrowClickListener{
//            Log.d("TaG", "onCreate: Arrow Clicked")
//        }
//
//
//        with(binding) {
//            binding.cd5.setOnClickListener {
//                binding.cd5.setTitleCircleText("MS")
//            }
//            cd5.setOnArrowClickListener {
//
//                Toast.makeText(this@ListsCardsActivity, "Arrow clicked!", Toast.LENGTH_SHORT).show()
//            }
//
//            cd5.setOnCheckBoxClickListener { isChecked ->
//                Toast.makeText(this@ListsCardsActivity, "Checkbox is ${isChecked}", Toast.LENGTH_SHORT).show()
//            }
//
//            cd5.setOnRadioButtonCheckedListener { isChecked ->
//                Toast.makeText(this@ListsCardsActivity, "RadioButton is now $isChecked", Toast.LENGTH_SHORT).show()
//            }
//
//            cd5.setOnRadioButtonClickListener { it ->
//                Log.d("TAG", "onCreate: Radio Button Clicked $it")
//
//            }
//
//            cd5.setOnSwitchToggleListener { isChecked ->
//
//                Toast.makeText(this@ListsCardsActivity, "Switch is $isChecked", Toast.LENGTH_SHORT).show()
//            }
//        }


        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
    }
}