package com.educacionit.libros.io

import android.content.Context
import com.educacionit.libros.DBHelper
import com.educacionit.libros.Libro
import com.educacionit.libros.io.network.LibrosApi
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.dao.Dao
import java.sql.SQLException

class LibrosRepository(
    context: Context,
    val librosApi: LibrosApi,
) : ILibrosRepository {

    private lateinit var dao: Dao<Libro, Int>

    init {
        val helper = OpenHelperManager.getHelper(context, DBHelper::class.java)
        try {
            dao = helper.getDao(Libro::class.java)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    suspend fun getLibros(): List<Libro> {
        return dao.queryForAll()
    }

    fun agregarLibro(libro: Libro) {
        dao.create(libro)
    }

    override suspend fun getLibrosFavoritos(
        onGetLibrosSuccess: (libros: List<Libro>) -> Unit,
        onGetLibrosError: (errorMessage: String) -> Unit,
    ) {
        try {
            val librosFavoritos = librosApi.getLibrosFavoritos().map { it.toLibro() }
            onGetLibrosSuccess(librosFavoritos)
        } catch (exception: Exception) {
            onGetLibrosError(exception.message ?: "No se pudieron obtener los libros favoritos")
        }
    }
}