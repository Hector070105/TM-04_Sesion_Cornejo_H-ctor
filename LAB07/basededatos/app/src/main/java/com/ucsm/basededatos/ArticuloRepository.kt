package com.ucsm.basededatos.data

import kotlinx.coroutines.flow.Flow

class ArticuloRepository(private val dao: ArticuloDao) {

    fun listarTodos(): Flow<List<Articulo>> {
        return dao.listarTodos()
    }

    suspend fun insertar(articulo: Articulo): Long {
        return dao.insertar(articulo)
    }

    suspend fun actualizar(articulo: Articulo): Int {
        return dao.actualizar(articulo)
    }

    suspend fun eliminar(articulo: Articulo): Int {
        return dao.eliminar(articulo)
    }

    suspend fun eliminarPorCodigo(codigo: Int): Int {
        return dao.eliminarPorCodigo(codigo)
    }

    suspend fun buscarPorCodigo(codigo: Int): Articulo? {
        return dao.buscarPorCodigo(codigo)
    }
}