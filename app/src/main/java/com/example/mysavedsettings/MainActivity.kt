package com.example.mysavedsettings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Switch

class MainActivity : AppCompatActivity() {

    lateinit var name: EditText
    lateinit var pick_class: EditText
    lateinit var breakfast: RadioButton
    lateinit var lunch: RadioButton
    lateinit var dinner: RadioButton
    lateinit var snack: RadioButton

    val PREF_NAME = "prefs"
    val PREF_DARK_THEME = "dark_theme"


    override fun onCreate(savedInstanceState: Bundle?) {

        val SP = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        val useDarkTheme = SP.getBoolean(PREF_DARK_THEME, false)

        if (useDarkTheme){
            setTheme(R.style.ThemeOverlay_AppCompat_Dark)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name = findViewById(R.id.nameEdit)
        pick_class = findViewById(R.id.classEdit)

        breakfast = findViewById(R.id.breakfastButton)
        lunch = findViewById(R.id.lunchButton)
        dinner = findViewById(R.id.dinnerButton)
        snack = findViewById(R.id.snackButton)

        breakfast.setOnCheckedChangeListener {
                view, isChecked ->  mealChoice(isChecked)
        }

       // val mealChoice = findViewById<RadioGroup>(R.id.mealGroup)


        val toggle = findViewById<Switch>(R.id.switch1)
        toggle.isChecked = useDarkTheme
        toggle.setOnCheckedChangeListener {
                view, isChecked -> toggleTheme(isChecked)
        }
    }

    override fun onResume() {
        super.onResume()

        val SP = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        //look for the key value pairs
        val key1 = SP.getString("name", "")
        val key2 = SP.getString("pick_class", "")
        val key3 = SP.getBoolean("mealChoice", false)

        //assign them to the widgets
        name!!.setText(key1)
        pick_class!!.setText(key2)
    }

    override fun onPause() {
        super.onPause()

        //opened in private mode for writing
        val SP = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val myEdit = SP.edit()

        //write to the file
        myEdit.putString("name", name!!.text.toString())
        myEdit.putString("pick_class", pick_class!!.text.toString())
        //you could use commit here
        myEdit.apply()
    }

    private fun toggleTheme(darkTheme: Boolean){
        val editor = getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit()

        editor.apply{
            putBoolean(PREF_DARK_THEME, darkTheme)
            apply()
        }

        val intent = intent
        finish()
        startActivity(intent)
    }

    private fun mealChoice(choice: Boolean){
        val editor = getSharedPreferences("breakfast", MODE_PRIVATE).edit()

        editor.apply{
            putBoolean("breakfast", choice)
            apply()
        }

    }
}