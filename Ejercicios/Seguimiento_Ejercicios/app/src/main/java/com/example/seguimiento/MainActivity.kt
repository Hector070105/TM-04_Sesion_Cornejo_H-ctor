package com.example.seguimiento

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.seguimiento.databinding.ActivityMainBinding
import android.view.View

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = "Ciclo de Vida"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "En el evento onCreate()")

        binding.btnMostrar.setOnClickListener {
            val intent = Intent(this, SegundaActividad::class.java)
            startActivity(intent)
        }
    }

    fun onClick(view: View) {
        val intent = Intent(this, SegundaActividad::class.java)
        startActivity(intent)
    }
    fun irConversion(view: View) {
        val intent = Intent(this, SegundaActividad::class.java)
        intent.putExtra("destino", "conversion")
        startActivity(intent)
    }

    fun irDrawer(view: View) {
        val intent = Intent(this, SegundaActividad::class.java)
        intent.putExtra("destino", "drawer")
        startActivity(intent)
    }

    override fun onStart()   { super.onStart();   Log.d(TAG, "En el evento onStart()") }
    override fun onRestart(){ super.onRestart(); Log.d(TAG, "En el evento onRestart()") }
    override fun onResume() { super.onResume();  Log.d(TAG, "En el evento onResume()") }
    override fun onPause()  { super.onPause();   Log.d(TAG, "En el evento onPause()") }
    override fun onStop()   { super.onStop();    Log.d(TAG, "En el evento onStop()") }
    override fun onDestroy(){ super.onDestroy(); Log.d(TAG, "En el evento onDestroy()") }
}