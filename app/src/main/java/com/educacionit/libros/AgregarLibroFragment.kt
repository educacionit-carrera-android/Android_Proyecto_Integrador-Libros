package com.educacionit.libros

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import com.educacionit.libros.io.LibrosRepository
import com.educacionit.libros.io.network.RetrofitClient
import com.squareup.picasso.Picasso

class AgregarLibroFragment : Fragment() {

    private lateinit var etNombreLibro: EditText
    private lateinit var etAutor: EditText
    private lateinit var btnGuardar: Button
    private lateinit var imageViewAgregarLibro: ImageView

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
        imageViewAgregarLibro = view.findViewById(R.id.imageViewAgregarLibro)
        cargarImagen()
    }

    private fun cargarImagen() {
        Picasso.get().isLoggingEnabled = true
        Picasso.get()
            .load("https://cdn.pixabay.com/photo/2018/01/03/09/09/book-3057904_1280.png")
            .error(R.drawable.splash_screen)
            .into(imageViewAgregarLibro)
    }

    private fun guardarLibro() {
        if (datosValidos()) {
            val libro = Libro()
            libro.nombre = etNombreLibro.text.toString()
            libro.autor = etAutor.text.toString()
            guardarLibro(libro)
            mostrarNotification()
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
        LibrosRepository(
            requireContext(),
            RetrofitClient.librosApi,
        ).agregarLibro(libro)
    }

    private fun datosValidos(): Boolean {
        var datosValidos = true
        if (etNombreLibro.text.toString().isEmpty() || etAutor.text.toString().isEmpty()) {
            datosValidos = false
        }
        return datosValidos
    }

    private fun mostrarNotification() {
        val notification = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("Libros")
            .setContentText("Se agregó un libro a la colección")
            .build()

        val notificationManager = requireContext().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        crearCanal(notificationManager)
        notificationManager.notify(1, notification)
    }

    private fun crearCanal(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID,
                "Nuevo libro",
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private companion object {
        const val CHANNEL_ID: String = "1"
    }
}