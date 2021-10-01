package com.educacionit.libros

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var rvLibros: RecyclerView
    private var adapter: LibrosAdapter = LibrosAdapter {
        Toast.makeText(this@HomeActivity, it.nombre, Toast.LENGTH_SHORT).show()
    }
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val nuevoLibro = result.data?.getSerializableExtra(LIBRO) as? Libro
            nuevoLibro?.let { agregarNuevoLibroAdapter(it) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupToolbar()
        saludarUsuario()
        setupAdapter()
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Mis Libros"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_agregar) {
            goToAgregarLibro()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToAgregarLibro() {
        startForResult.launch(
            Intent(this@HomeActivity, AgregarLibroActivity::class.java)
        )
    }

    private fun setupAdapter() {
        rvLibros = findViewById(R.id.rvLibros)
        rvLibros.adapter = adapter
        adapter.libros = getLibros()
    }

    private fun getLibros(): List<Libro> {
        return listOf(
            Libro(1, "Harry Potter", "J.K. Rowling"),
            Libro(2, "Game of Thrones", "George Martin"),
            Libro(3, "Maze Runner", "James Dashner")
        )
    }

    private fun agregarNuevoLibroAdapter(nuevoLibro: Libro) {
        val libros = adapter.libros
        libros.add(nuevoLibro)
        adapter.libros = libros
    }

    private fun saludarUsuario() {
        val bundle = intent.extras
        if (bundle != null) {
            val usuario = bundle.getString("USUARIO")
            Toast.makeText(this@HomeActivity, "Bienvenido $usuario", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val LIBRO = "Libro"
    }
}