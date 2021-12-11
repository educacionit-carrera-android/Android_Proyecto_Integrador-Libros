package com.educacionit.libros

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_NO_CREATE
import android.content.Intent
import android.content.Intent.ACTION_AIRPLANE_MODE_CHANGED
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class HomeActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var rvLibros: RecyclerView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var fabAgregarLibro: FloatingActionButton

    private var adapter: LibrosAdapter = LibrosAdapter { libro, txtNombreLibro, txtAutor ->
        goToDetalleLibro(
            libro,
            txtNombreLibro,
            txtAutor
        )
    }
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val libroAgregado = result.data?.getBooleanExtra(LIBRO, false) ?: false
            if (libroAgregado) refrescarLibros()
        }
    }
    private val airplaneStateReceiver = AirplaneStateReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupToolbar()
        configurarNavigationView()
        configurarFAB()
        saludarUsuario()
        setupAdapter()
        refrescarLibros()
        registerReceiver(airplaneStateReceiver, IntentFilter(ACTION_AIRPLANE_MODE_CHANGED));
        createSyncAlarm()
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Mis Libros"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun configurarNavigationView() {
        drawerLayout = findViewById(R.id.drawerLayout)
        val drawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        navigationView = findViewById(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuAboutMe -> goToAboutMe()
                R.id.menuCerrarSesion -> cerrarSesion()
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    private fun configurarFAB() {
        fabAgregarLibro = findViewById(R.id.fabAgregarLibro)
        fabAgregarLibro.setOnClickListener { goToAgregarLibro() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_favoritos) {
            goToFavoritos()
        } else if (item.itemId == R.id.item_mapa) {
            goToMapa()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToFavoritos() {
        startActivity(
            Intent(
                this, LibrosFavoritosActivity::class.java
            )
        )
    }

    private fun goToAgregarLibro() {
        startForResult.launch(
            Intent(this@HomeActivity, AgregarLibroActivity::class.java)
        )
    }

    private fun goToDetalleLibro(libro: Libro, txtNombre: TextView, txtAutor: TextView) {
        val intent = Intent(this, DetalleLibroActivity::class.java)
        intent.putExtra(LIBRO, libro)

        val pair1: Pair<View, String> = Pair.create(txtNombre, getString(R.string.nombre_libro))
        val pair2: Pair<View, String> = Pair.create(txtAutor, getString(R.string.nombre_autor))
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            pair1,
            pair2,
        )

        startActivity(intent, options.toBundle());
    }

    private fun goToAboutMe() {
        startActivity(Intent(this, AboutMeActivity::class.java))
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun goToMapa() {
        startActivity(Intent(this, MapsActivity::class.java))
    }

    private fun cerrarSesion() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setupAdapter() {
        rvLibros = findViewById(R.id.rvLibros)
        rvLibros.adapter = adapter
    }

    private fun refrescarLibros() {
        lifecycleScope.launch(Dispatchers.IO) {
            val libros = LibrosRepository(this@HomeActivity).getLibros()
            withContext(Dispatchers.Main) {
                adapter.libros = libros
            }
        }
    }

    private fun createSyncAlarm() {
        val intent = Intent(this, SyncDataReceiver::class.java)
        val alarmExists = PendingIntent.getBroadcast(this, 0, intent, FLAG_NO_CREATE) != null
        if (!alarmExists) {
            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
            val calendar: Calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            calendar.set(Calendar.HOUR_OF_DAY, 8)
            calendar.set(Calendar.MINUTE, 0)
            alarmManager.setInexactRepeating(
                AlarmManager.RTC,
                calendar.timeInMillis,
                86400000,
                pendingIntent
            )
        }
    }

    override fun onDestroy() {
        stopService(Intent(this, SyncService::class.java))
        super.onDestroy()
    }

    private fun saludarUsuario() {
        val bundle = intent.extras
        if (bundle != null) {
            val usuario = bundle.getString("USUARIO")
            Toast.makeText(this@HomeActivity, "Bienvenido $usuario", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val LIBRO = "Libro"
    }
}