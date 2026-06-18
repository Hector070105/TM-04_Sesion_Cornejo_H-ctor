package com.ucsm.escaneo

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.ucsm.escaneo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val imagePicker =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding.imageView.setImageURI(it)
                scanBarcode(it)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.selectImageButton.setOnClickListener {
            imagePicker.launch("image/*")
        }
    }

    private fun scanBarcode(uri: Uri) {
        try {
            val image = InputImage.fromFilePath(this, uri)
            val scanner = BarcodeScanning.getClient()

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.isEmpty()) {
                        binding.resultText.text = "Resultado: No se detectó ningún código."
                    } else {
                        val result = StringBuilder()

                        for (barcode in barcodes) {
                            result.append("Valor detectado: ${barcode.rawValue}\n")
                            result.append("Tipo: ${barcode.valueType}\n\n")
                        }

                        binding.resultText.text = result.toString()
                    }
                }
                .addOnFailureListener { e ->
                    binding.resultText.text = "Error: ${e.message}"
                }

        } catch (e: Exception) {
            Toast.makeText(this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show()
        }
    }
}