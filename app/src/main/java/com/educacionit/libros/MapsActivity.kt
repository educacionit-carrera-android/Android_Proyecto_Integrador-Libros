package com.educacionit.libros

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.educacionit.libros.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(),
    OnMapReadyCallback { // Implementa interfaz -> onMapReady()

    private lateinit var mMap: GoogleMap // Instancia del mapa para realizar acciones sobre él
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtiene la instancia del fragmento del mapa y define un listener para ser notificado
        // de cuando esté disponible para ser utilizado
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    // Callback que es llamado cuando el mapa está listo para ser utilizado
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val capitalFederal = LatLng(-34.6118529, -58.4560401)
        val ateneoMarker = LatLng(-34.5959839, -58.3942276)
        val bibliotecaNacionalMarker = LatLng(-34.5844482, -58.3980291)

        mMap.addMarker(crearMarcador(ateneoMarker, "El Ateneo"))
        mMap.addMarker(crearMarcador(bibliotecaNacionalMarker, "Biblioteca Nacional Mariano Moreno"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(capitalFederal, 12F))
    }

    private fun crearMarcador(latLng: LatLng, title: String): MarkerOptions {
        return MarkerOptions()
            .position(latLng)
            .title(title)
    }
}