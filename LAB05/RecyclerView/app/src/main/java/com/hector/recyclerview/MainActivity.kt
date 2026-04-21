package com.hector.recyclerview

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        val lista = listOf(
            Producto("Laptop", 5, 3500.0),
            Producto("Mouse", 10, 50.0),
            Producto("Teclado", 8, 120.0)
        )

        guardarProductos(this, lista)

        val listaRecuperada = obtenerProductos(this)

        adapter = ProductoAdapter(listaRecuperada)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}