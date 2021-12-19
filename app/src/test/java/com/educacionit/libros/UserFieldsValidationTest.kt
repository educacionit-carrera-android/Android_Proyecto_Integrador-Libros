package com.educacionit.libros

import org.junit.Assert
import org.junit.Test

class UserFieldsValidationTest {

    private val userFieldsValidation = UserFieldsValidation()

    @Test
    fun `isUserValid debe devolver false si el usuario es vacio`() {
        val usuario = ""
        val contrasenia = "123"

        val isUserValid = userFieldsValidation.isUserValid(usuario, contrasenia)

        Assert.assertFalse(isUserValid)
    }

    @Test
    fun `isUserValid debe devolver false si la contrasenia es vacia`() {
        val usuario = "Nico"
        val contrasenia = ""

        val isUserValid = userFieldsValidation.isUserValid(usuario, contrasenia)

        Assert.assertFalse(isUserValid)
    }

    @Test
    fun `isUserValid debe devolver false si el usuario y la contrasenia son vacias`() {
        val usuario = ""
        val contrasenia = ""

        val isUserValid = userFieldsValidation.isUserValid(usuario, contrasenia)

        Assert.assertFalse(isUserValid)
    }

    @Test
    fun `isUserValid debe devolver false si el usuario y la contrasenia estan completos pero longitud contrasneia menor a 3`() {
        val usuario = ""
        val contrasenia = "12"

        val isUserValid = userFieldsValidation.isUserValid(usuario, contrasenia)

        Assert.assertFalse(isUserValid)
    }

    @Test
    fun `isUserValid debe devolver true si el usuario y la contrasenia estan completos y longitud contrasenia mayor a 3`() {
        val usuario = "Nico"
        val contrasenia = "123"

        val isUserValid = userFieldsValidation.isUserValid(usuario, contrasenia)

        Assert.assertFalse(isUserValid)
    }
}