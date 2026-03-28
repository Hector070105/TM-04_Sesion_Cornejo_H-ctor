package com.example.seguimiento

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class SegundaActividad : AppCompatActivity() {

    companion object {
        const val TIMER_RUNTIME = 10000
    }

    private var nbActivo: Boolean = false
    private var nProgressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
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
        Log.d("mensajeFinal", "Su barra de progreso acaba de cargar")
    }
}
