package com.educacionit.libros

import android.content.Context
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.dao.Dao
import java.sql.SQLException

class LibrosRepository(context: Context) {

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
}