package com.educacionit.libros

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.educacionit.libros.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LibrosFavoritosActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var progressBar: ProgressBar
    private lateinit var rvLibrosFavoritos: RecyclerView
    private var adapter: LibrosAdapter = LibrosAdapter { libro, _, _ ->
        mostrarMensaje(libro.nombre)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_libros_favoritos)

        setupUI()
        obtenerLibrosFavoritos()
    }

    private fun setupUI() {
        setupToolbar()
        progressBar = findViewById(R.id.progressBar)
        setupAdapter()
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Favoritos"
    }

    private fun setupAdapter() {
        rvLibrosFavoritos = findViewById(R.id.rvLibrosFavoritos)
        rvLibrosFavoritos.adapter = adapter
    }

    fun mostrarProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    fun ocultarProgressBar() {
        progressBar.visibility = View.GONE
    }

    fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    private fun obtenerLibrosFavoritos() {
        mostrarProgressBar()
        lifecycleScope.launch(Dispatchers.IO) {
            mostrarProgressBar()
            try {
                val libros = RetrofitClient.librosApi.getLibrosFavoritos().map { it.toLibro() }
                withContext(Dispatchers.Main) {
                    adapter.libros = libros
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    mostrarMensaje(e.message ?: "No se pudieron obtener los libros favoritos")
                }
                e.printStackTrace()
            } finally {
                withContext(Dispatchers.Main) {
                    ocultarProgressBar()
                }
            }
        }
    }
}