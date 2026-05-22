package com.ucsm.repositoriofbej

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var txtNombre: EditText
    private lateinit var txtCarrera: EditText
    private lateinit var txtCurso: EditText
    private lateinit var btnGuardar: Button
    private lateinit var estudiantesRef: DatabaseReference
    private lateinit var txtListaEstudiantes: TextView
    private var estudianteIdSeleccionado: String = ""
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        estudiantesRef = FirebaseDatabase.getInstance().getReference("Estudiantes")

        txtNombre = findViewById(R.id.txtNombre)
        txtCarrera = findViewById(R.id.txtCarrera)
        txtCurso = findViewById(R.id.txtCurso)
        btnGuardar = findViewById(R.id.btnGuardar)

        txtListaEstudiantes = findViewById(R.id.txtListaEstudiantes)
        mostrarEstudiantes()

        btnGuardar.setOnClickListener {
            guardarEstudiante()
        }

        btnActualizar = findViewById(R.id.btnActualizar)
        btnEliminar = findViewById(R.id.btnEliminar)

        btnActualizar.setOnClickListener {
            actualizarEstudiante()
        }

        btnEliminar.setOnClickListener {
            eliminarEstudiante()
        }
    }

    private fun guardarEstudiante() {
        val nombre = txtNombre.text.toString().trim()
        val carrera = txtCarrera.text.toString().trim()
        val curso = txtCurso.text.toString().trim()

        if (nombre.isEmpty() || carrera.isEmpty() || curso.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val id = estudiantesRef.push().key ?: return

        val estudiante = Estudiante(
            estudianteid = id,
            nombre = nombre,
            carrera = carrera,
            curso = curso
        )

        estudiantesRef.child(id).setValue(estudiante)
            .addOnSuccessListener {
                Toast.makeText(this, "Estudiante guardado", Toast.LENGTH_LONG).show()
                txtNombre.text.clear()
                txtCarrera.text.clear()
                txtCurso.text.clear()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun mostrarEstudiantes() {
        estudiantesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lista = StringBuilder()
                lista.append("Estudiantes registrados:\n\n")

                for (dato in snapshot.children) {
                    val estudiante = dato.getValue(Estudiante::class.java)

                    if (estudiante != null) {
                        estudianteIdSeleccionado = estudiante.estudianteid

                        lista.append("ID: ${estudiante.estudianteid}\n")
                        lista.append("Nombre: ${estudiante.nombre}\n")
                        lista.append("Carrera: ${estudiante.carrera}\n")
                        lista.append("Curso: ${estudiante.curso}\n")
                        lista.append("----------------------\n")
                    }
                }

                txtListaEstudiantes.text = lista.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@MainActivity,
                    "Error al leer datos: ${error.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun actualizarEstudiante() {
        val nombre = txtNombre.text.toString().trim()
        val carrera = txtCarrera.text.toString().trim()
        val curso = txtCurso.text.toString().trim()

        if (estudianteIdSeleccionado.isEmpty()) {
            Toast.makeText(this, "No hay estudiante seleccionado", Toast.LENGTH_SHORT).show()
            return
        }

        if (nombre.isEmpty() || carrera.isEmpty() || curso.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val datosActualizados = mapOf<String, Any>(
            "nombre" to nombre,
            "carrera" to carrera,
            "curso" to curso
        )

        estudiantesRef.child(estudianteIdSeleccionado).updateChildren(datosActualizados)
            .addOnSuccessListener {
                Toast.makeText(this, "Estudiante actualizado", Toast.LENGTH_LONG).show()
                txtNombre.text.clear()
                txtCarrera.text.clear()
                txtCurso.text.clear()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun eliminarEstudiante() {
        if (estudianteIdSeleccionado.isEmpty()) {
            Toast.makeText(this, "No hay estudiante seleccionado", Toast.LENGTH_SHORT).show()
            return
        }

        estudiantesRef.child(estudianteIdSeleccionado).removeValue()
            .addOnSuccessListener {
                Toast.makeText(this, "Estudiante eliminado", Toast.LENGTH_LONG).show()
                estudianteIdSeleccionado = ""
                txtNombre.text.clear()
                txtCarrera.text.clear()
                txtCurso.text.clear()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}