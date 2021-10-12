package com.educacionit.libros

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class AgregarLibroFragment : Fragment() {

    private lateinit var etNombreLibro: EditText
    private lateinit var etAutor: EditText
    private lateinit var btnGuardar: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agregar_libro, container, false)
        setupUI(view)

        return view
    }


    private fun setupUI(view: View) {
        etNombreLibro = view.findViewById(R.id.etNombreLibro)
        etAutor = view.findViewById(R.id.etAutor)
        btnGuardar = view.findViewById(R.id.btnGuardar)
        btnGuardar.setOnClickListener { guardarLibro() }
    }

    private fun guardarLibro() {
        if (datosValidos()) {
            val libro = Libro()
            libro.nombre = etNombreLibro.text.toString()
            libro.autor = etAutor.text.toString()
            guardarLibro(libro)
            requireActivity().setResult(
                AppCompatActivity.RESULT_OK, Intent().putExtra(HomeActivity.LIBRO, true)
            )
            requireActivity().finish()
        } else {
            Toast.makeText(
                requireContext(),
                "Completar todos los campos",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun guardarLibro(libro: Libro) {
        LibrosRepository(requireContext()).agregarLibro(libro)
    }

    private fun datosValidos(): Boolean {
        var datosValidos = true
        if (etNombreLibro.text.toString().isEmpty() || etAutor.text.toString().isEmpty()) {
            datosValidos = false
        }
        return datosValidos
    }
}