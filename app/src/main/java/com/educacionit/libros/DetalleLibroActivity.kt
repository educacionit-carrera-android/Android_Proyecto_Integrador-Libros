package com.educacionit.libros

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetalleLibroActivity : AppCompatActivity() {

    private lateinit var txtLibro: TextView
    private lateinit var txtAutor: TextView
    private var libro: Libro? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_libro)

        libro = intent.getSerializableExtra(HomeActivity.LIBRO) as Libro

        setupUI()
        initTextViews()
    }

    private fun setupUI() {
        txtLibro = findViewById(R.id.txtLibro)
        txtAutor = findViewById(R.id.txtAutor)
    }

    private fun initTextViews() {
        libro?.let {
            txtLibro.text = it.nombre
            txtAutor.text = it.autor
        }
    }
}