package com.example.seguimiento

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SegundaActividad : AppCompatActivity() {
    private var destino: String? = null
    companion object {
        const val TIMER_RUNTIME = 10000
    }

    private var nbActivo: Boolean = false
    private var nProgressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        destino = intent.getStringExtra("destino")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_segunda_actividad)

        nProgressBar = findViewById(R.id.progressBar)

        val timerThread = object : Thread() {
            override fun run() {
                nbActivo = true
                try {
                    var espera1 = 0
                    while (nbActivo && (espera1 < TIMER_RUNTIME)) {
                        sleep(200)
                        if (nbActivo) {
                            espera1 += 200
                            actualizarProgress(espera1)
                        }
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    onContinuar()
                }
            }
        }
        timerThread.start()
    }

    private fun actualizarProgress(timePassed: Int) {
        nProgressBar?.let {
            val progress = it.max * timePassed / TIMER_RUNTIME
            it.progress = progress
        }
    }
    private fun onContinuar() {
        runOnUiThread {
            Toast.makeText(this, "Carga completa", Toast.LENGTH_SHORT).show()

            val intent = when (destino) {
                "conversion" -> Intent(this, ConversionActivity::class.java)
                "drawer" -> Intent(this, DrawerActivity::class.java)
                else -> Intent(this, ConversionActivity::class.java)
            }

            startActivity(intent)
            finish()
        }
    }
}
