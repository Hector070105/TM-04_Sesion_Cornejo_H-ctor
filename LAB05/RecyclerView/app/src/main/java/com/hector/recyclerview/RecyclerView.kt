package com.hector.recyclerview

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun guardarProductos(context: Context, lista: List<Producto>) {
    val prefs = context.getSharedPreferences("productos", Context.MODE_PRIVATE)
    val editor = prefs.edit()

    val json = Gson().toJson(lista)
    editor.putString("lista_productos", json)
    editor.apply()
}

fun obtenerProductos(context: Context): List<Producto> {
    val prefs = context.getSharedPreferences("productos", Context.MODE_PRIVATE)
    val json = prefs.getString("lista_productos", null)

    return if (json != null) {
        val type = object : TypeToken<List<Producto>>() {}.type
        Gson().fromJson(json, type)
    } else {
        emptyList()
    }
}