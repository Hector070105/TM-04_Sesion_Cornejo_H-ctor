package com.ucsm.basededatos

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ucsm.basededatos.data.AppDatabase
import com.ucsm.basededatos.data.Articulo
import com.ucsm.basededatos.data.ArticuloDao
import com.ucsm.basededatos.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collectLatest
import com.ucsm.basededatos.data.ArticuloRepository
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    //Actividad private lateinit var dao: ArticuloDao

    private lateinit var repository: ArticuloRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Obtener instancia del DAO (Room)
        //Actividad dao = AppDatabase.getInstance(this).articuloDao()
        val dao = AppDatabase.getInstance(this).articuloDao()
        repository = ArticuloRepository(dao)
        // Listeners
        binding.btnRegistrar.setOnClickListener { registrar() }
        binding.btnBuscar.setOnClickListener { buscar() }
        binding.btnModificar.setOnClickListener { modificar() }
        binding.btnEliminar.setOnClickListener { eliminar() }

        observarArticulos()
    }
    private fun toast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
    private fun limpiarCampos() {
        binding.txtCodigo.setText("")
        binding.txtDescripcion.setText("")
        binding.txtPrecio.setText("")
    }

    private fun registrar() {
        val codigo = binding.txtCodigo.text.toString()
        val descripcion = binding.txtDescripcion.text.toString()
        val precio = binding.txtPrecio.text.toString()
        if (codigo.isEmpty() || descripcion.isEmpty() || precio.isEmpty()) {
            toast("Debe llenar todos los campos")
            return
        }
        val articulo = Articulo(
            codigo = codigo.toInt(),
            descripcion = descripcion,
            precio = precio.toDouble()
        )
        lifecycleScope.launch {
            try {
                //Actividad dao.insertar(articulo)
                repository.insertar(articulo)
                limpiarCampos()
                toast("Registro exitoso")
            } catch (e: Exception) {
                toast("Error al registrar: ${e.message}")
            }
        }
    }
    private fun buscar() {
        val codigo = binding.txtCodigo.text.toString()
        if (codigo.isEmpty()) {
            toast("Debe introducir el código del artículo")
            return
        }
        lifecycleScope.launch {
            val articulo = repository.buscarPorCodigo(codigo.toInt())
                //Actividad dao.buscarPorCodigo(codigo.toInt())
            if (articulo != null) {
                binding.txtDescripcion.setText(articulo.descripcion)
                binding.txtPrecio.setText(articulo.precio.toString())
            } else {
                toast("No existe el artículo")
            }
        }
    }
    private fun eliminar() {
        val codigo = binding.txtCodigo.text.toString()
        if (codigo.isEmpty()) {
            toast("Debe introducir el código del artículo")
            return
        }
        lifecycleScope.launch {
            val filasEliminadas = repository.eliminarPorCodigo(codigo.toInt())
                //Actividad dao.eliminarPorCodigo(codigo.toInt())
            limpiarCampos()
            if (filasEliminadas == 1) {
                toast("Artículo eliminado exitosamente")
            } else {
                toast("El artículo no existe")
            }
        }
    }
    private fun modificar() {
        val codigo = binding.txtCodigo.text.toString()
        val descripcion = binding.txtDescripcion.text.toString()
        val precio = binding.txtPrecio.text.toString()
        if (codigo.isEmpty() || descripcion.isEmpty() || precio.isEmpty()) {
            toast("Debe llenar todos los campos")
            return
        }
        val articulo = Articulo(
            codigo = codigo.toInt(),
            descripcion = descripcion,
            precio = precio.toDouble()
        )
        lifecycleScope.launch {
            val filasActualizadas = repository.actualizar(articulo)
                //Actividad dao.actualizar(articulo)
            if (filasActualizadas == 1) {
                toast("Artículo modificado correctamente")
            } else {
                toast("El artículo no existe")
            }
        }
    }
    private fun observarArticulos() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                //Actividad dao.listarTodos()
                repository.listarTodos().collectLatest { lista ->
                    val texto = lista.joinToString("\n") { articulo ->
                        "Código: ${articulo.codigo} - ${articulo.descripcion} - S/ ${articulo.precio}"
                    }

                    if (texto.isNotEmpty()) {
                        Toast.makeText(this@MainActivity, texto, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}