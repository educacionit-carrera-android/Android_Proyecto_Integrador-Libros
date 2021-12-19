package com.educacionit.libros.view

import com.educacionit.libros.Libro

interface LibrosFavoritosView {
    fun actualizarLibros(libros: List<Libro>)
    fun mostrarProgressBar()
    fun ocultarProgressBar()
    fun mostrarMensaje(mensaje: String)
}