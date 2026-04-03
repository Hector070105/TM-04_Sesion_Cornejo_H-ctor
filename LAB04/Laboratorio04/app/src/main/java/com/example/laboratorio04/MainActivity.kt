package com.example.laboratorio04

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAuto = findViewById<Button>(R.id.btnAutoComplete)
        val btnTime = findViewById<Button>(R.id.btnTimePicker)
        val btnDate = findViewById<Button>(R.id.btnDatePicker)

        btnAuto.setOnClickListener {
            startActivity(Intent(this, Auto::class.java))
        }

        btnTime.setOnClickListener {
            startActivity(Intent(this, TimeP::class.java))
        }
        btnDate.setOnClickListener {
            startActivity(Intent(this, DateP::class.java))
        }
    }
}