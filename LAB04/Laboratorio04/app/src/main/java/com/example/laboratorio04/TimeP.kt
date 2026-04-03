package com.example.laboratorio04

import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TimeP : AppCompatActivity() {

    private lateinit var timePicker: TimePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_p)

        timePicker = findViewById(R.id.timePicker)
        timePicker.setIs24HourView(true)
    }

    fun onClick(view: View) {
        val hour = timePicker.hour
        val minute = timePicker.minute

        Toast.makeText(
            this,
            "Hora seleccionada $hour:$minute",
            Toast.LENGTH_SHORT
        ).show()
    }
}