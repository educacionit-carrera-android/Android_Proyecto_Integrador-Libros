package com.educacionit.libros

data class LibroResponse(
    val id: Int,
    val nombre: String,
    val autor: String
) {
    fun toLibro(): Libro = Libro(
        id,
        nombre,
        autor
    )
}