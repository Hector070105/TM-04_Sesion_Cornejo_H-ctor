package com.example.laboratorio04

import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.time.Duration.Companion.days

class DateP : AppCompatActivity() {

    private lateinit var timePicker: TimePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_p)
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
    fun onClick2(view: View) {
        val hour = timePicker.hour
        val minute = timePicker.minute

        Toast.makeText(
            this,
            "Reserva registrada: $hour:$minute",
            Toast.LENGTH_SHORT
        ).show()
    }
}