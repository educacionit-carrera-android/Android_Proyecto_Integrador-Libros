package com.educacionit.libros.network

import com.educacionit.libros.LibroResponse
import retrofit2.http.GET

interface LibrosApi {
    @GET("librosFavoritos")
    suspend fun getLibrosFavoritos(): List<LibroResponse>
}