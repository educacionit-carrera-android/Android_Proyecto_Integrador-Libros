package com.educacionit.libros

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView

class LibrosFavoritosActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var progressBar: ProgressBar
    private lateinit var rvLibrosFavoritos: RecyclerView
    private var adapter: LibrosAdapter = LibrosAdapter {
        mostrarMensaje(it.nombre)
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
        // TODO completar con llamado a la api
    }
}