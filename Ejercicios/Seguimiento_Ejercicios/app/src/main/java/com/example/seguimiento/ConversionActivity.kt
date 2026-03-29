package com.example.seguimiento

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConversionActivity : AppCompatActivity() {

    private lateinit var txtSoles: EditText
    private lateinit var txtResultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversion)

        txtSoles = findViewById(R.id.txtSoles)
        txtResultado = findViewById(R.id.txtResultado)
    }

    fun convertir(view: View) {
        val soles = txtSoles.text.toString().toDoubleOrNull() ?: 0.0
        val dolares = soles / 3.8
        txtResultado.text = "Dólares: $dolares"
    }
}