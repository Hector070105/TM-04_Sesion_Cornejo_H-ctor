package com.ucsm.calculadora

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var etNumero1: EditText
    private lateinit var etNumero2: EditText
    private lateinit var tvResultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNumero1 = findViewById(R.id.etNumero1)
        etNumero2 = findViewById(R.id.etNumero2)
        tvResultado = findViewById(R.id.tvResultado)

        val btnSuma = findViewById<Button>(R.id.btnSuma)
        val btnResta = findViewById<Button>(R.id.btnResta)
        val btnMultiplicacion = findViewById<Button>(R.id.btnMultiplicacion)
        val btnDivision = findViewById<Button>(R.id.btnDivision)
        val btnPotencia = findViewById<Button>(R.id.btnPotencia)
        val btnRaiz = findViewById<Button>(R.id.btnRaiz)
        val btnRaiz2 = findViewById<Button>(R.id.btnRaiz2)

        btnSuma.setOnClickListener {
            operar("suma")
        }

        btnResta.setOnClickListener {
            operar("resta")
        }

        btnMultiplicacion.setOnClickListener {
            operar("multiplicacion")
        }

        btnDivision.setOnClickListener {
            operar("division")
        }

        btnPotencia.setOnClickListener {
            operar("potencia")
        }

        btnRaiz.setOnClickListener {
            calcularRaiz()
        }

        btnRaiz2.setOnClickListener {
            calcularRaiz2()
        }
    }

    private fun obtenerNumero1(): Double? {
        return etNumero1.text.toString().toDoubleOrNull()
    }

    private fun obtenerNumero2(): Double? {
        return etNumero2.text.toString().toDoubleOrNull()
    }

    private fun operar(operacion: String) {
        val numero1 = obtenerNumero1()
        val numero2 = obtenerNumero2()

        if (numero1 == null || numero2 == null) {
            Toast.makeText(this, "Ingrese ambos números correctamente", Toast.LENGTH_SHORT).show()
            return
        }

        val resultado = when (operacion) {
            "suma" -> numero1 + numero2
            "resta" -> numero1 - numero2
            "multiplicacion" -> numero1 * numero2
            "division" -> {
                if (numero2 == 0.0) {
                    Toast.makeText(this, "No se puede dividir entre cero", Toast.LENGTH_SHORT).show()
                    return
                } else {
                    numero1 / numero2
                }
            }
            "potencia" -> numero1.pow(numero2)
            else -> 0.0
        }

        tvResultado.text = "Resultado: $resultado"
    }

    private fun calcularRaiz() {
        val numero1 = obtenerNumero1()

        if (numero1 == null) {
            Toast.makeText(this, "Ingrese el primer número correctamente", Toast.LENGTH_SHORT).show()
            return
        }

        if (numero1 < 0) {
            Toast.makeText(this, "No se puede calcular raíz de un número negativo", Toast.LENGTH_SHORT).show()
            return
        }

        val resultado = sqrt(numero1)
        tvResultado.text = "Resultado: $resultado"
    }
    private fun calcularRaiz2() {
        val numero2 = obtenerNumero2()

        if (numero2 == null) {
            Toast.makeText(this, "Ingrese el primer número correctamente", Toast.LENGTH_SHORT).show()
            return
        }

        if (numero2 < 0) {
            Toast.makeText(this, "No se puede calcular raíz de un número negativo", Toast.LENGTH_SHORT).show()
            return
        }

        val resultado = sqrt(numero2)
        tvResultado.text = "Resultado: $resultado"
    }
}