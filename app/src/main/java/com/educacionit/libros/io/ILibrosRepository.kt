package com.educacionit.libros.io

import com.educacionit.libros.Libro

interface ILibrosRepository {
    suspend fun getLibrosFavoritos(
        onGetLibrosSuccess: (libros: List<Libro>) -> Unit,
        onGetLibrosError: (errorMessage: String) -> Unit,
    )
}