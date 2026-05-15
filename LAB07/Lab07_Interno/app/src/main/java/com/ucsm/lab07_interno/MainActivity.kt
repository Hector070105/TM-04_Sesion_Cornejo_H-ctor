package com.ucsm.lab07_interno

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ucsm.lab07_interno.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    companion object {
        private const val FILE_NAME = "textfile.txt"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //binding.btnSave.setOnClickListener { onClickSave() }
        //binding.btnLoad.setOnClickListener { onClickLoad() }
        binding.btnSave.setOnClickListener {
            guardarArchivoExterno()
        }

        binding.btnLoad.setOnClickListener {
            leerArchivoExterno()
        }
    }
    private fun onClickSave() {
        val texto = binding.editText.text.toString()
        try {
            // Almacenamiento Interno usando 'use' (se cierra automáticamente)
            openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use { fos ->
                fos.write(texto.toByteArray())
            }
            Toast.makeText(
                this,
                "Archivo grabado satisfactoriamente!",
                Toast.LENGTH_SHORT
            ).show()
            binding.editText.setText("")
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error al guardar: ${e.message}",
                Toast.LENGTH_SHORT).show()
        }
    }
    private fun onClickLoad() {
        try {
            // Forma 1: usando openFileInput + bufferedReader
            val contenido = openFileInput(FILE_NAME).bufferedReader().use { reader ->
                reader.readText()
            }
            // Forma 2 (alternativa, más concisa): usando filesDir
            // val contenido = File(filesDir, FILE_NAME).readText()
            binding.editText.setText(contenido)
            Toast.makeText(
                this,
                "Archivo cargado satisfactoriamente!",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: java.io.FileNotFoundException) {
            Toast.makeText(this, "El archivo no existe aún", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error al cargar: ${e.message}",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun guardarArchivoExterno() {
        val texto = "Ejemplo de almacenamiento externo con Scoped Storage"

        try {
            val archivo = File(getExternalFilesDir(null), "archivo_externo.txt")
            archivo.writeText(texto)

            Toast.makeText(
                this,
                "Archivo externo guardado correctamente",
                Toast.LENGTH_SHORT
            ).show()

        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Error al guardar: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun leerArchivoExterno() {
        try {
            val archivo = File(getExternalFilesDir(null), "archivo_externo.txt")

            if (archivo.exists()) {
                val contenido = archivo.readText()

                Toast.makeText(
                    this,
                    contenido,
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "El archivo no existe",
                    Toast.LENGTH_SHORT
                ).show()
            }

        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Error al leer: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}