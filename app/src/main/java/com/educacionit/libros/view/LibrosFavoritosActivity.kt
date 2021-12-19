package com.educacionit.libros.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.educacionit.libros.Libro
import com.educacionit.libros.LibrosAdapter
import com.educacionit.libros.R
import com.educacionit.libros.io.LibrosRepository
import com.educacionit.libros.io.network.RetrofitClient
import com.educacionit.libros.presenter.LibrosFavoritosPresenter
import com.educacionit.libros.presenter.LibrosFavoritosPresenterImp

class LibrosFavoritosActivity : AppCompatActivity(), LibrosFavoritosView {

    private lateinit var toolbar: Toolbar
    private lateinit var progressBar: ProgressBar
    private lateinit var rvLibrosFavoritos: RecyclerView
    private val adapter: LibrosAdapter by lazy {
        LibrosAdapter { libro, _, _ ->
            mostrarMensaje(libro.nombre)
        }
    }
    private val presenter: LibrosFavoritosPresenter by lazy {
        LibrosFavoritosPresenterImp(
            this,
            LibrosRepository(this, RetrofitClient.librosApi)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_libros_favoritos)

        setupUI()
        presenter.doGetLibrosFavoritos()
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

    override fun mostrarProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun ocultarProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    override fun actualizarLibros(libros: List<Libro>) {
        adapter.libros = libros
    }

    override fun onDestroy() {
        presenter.cleanPresenter()
        super.onDestroy()
    }
}