package com.educacionit.libros

class UserFieldsValidation {
    fun isUserValid(usuario: String, contrasenia: String): Boolean {
        return usuario.isNotEmpty() && contrasenia.isNotEmpty() && contrasenia.length > 3
    }
}