package com.hector.usopreferencias

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
class MainActivity : AppCompatActivity() {
    private lateinit var txtString: EditText
    private lateinit var prefs: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtString = findViewById(R.id.txtString)
        prefs = getSharedPreferences(
            "com.hector.usopreferencias_preferences", MODE_PRIVATE
        )
        findViewById<Button>(R.id.btnCargar).setOnClickListener {
            startActivity(Intent(this, AppPreferenciasActivity::class.java))
        }
        findViewById<Button>(R.id.btnMostrar).setOnClickListener {
            val checkbox = if (prefs.getBoolean("checkboxPref", false)) "marcado" else "no marcado"
            val editText = prefs.getString("editTextPref", "sin valor")
            val secondEdit = prefs.getString("secondEditTextPref", "sin valor")
            Toast.makeText(this,
                "Checkbox: $checkbox\nEditText: $editText\nSecondEdit: $secondEdit",
                Toast.LENGTH_LONG).show()
        }
        findViewById<Button>(R.id.btnModificar).setOnClickListener {
            val nuevoValor = txtString.text.toString()
            prefs.edit().putString("editTextPref", nuevoValor).apply()
            Toast.makeText(this, "Valor modificado: $nuevoValor", Toast.LENGTH_SHORT).show()
        }
    }
}