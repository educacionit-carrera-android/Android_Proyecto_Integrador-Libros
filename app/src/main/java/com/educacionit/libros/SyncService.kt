package com.educacionit.libros

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SyncService : Service() {

    private lateinit var job: Job
    private val librosRepository: LibrosRepository by lazy { LibrosRepository(applicationContext) }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("SyncService", "Servicio iniciado")
        job = GlobalScope.launch(Dispatchers.IO) {
            sendDataToServer(librosRepository.getLibros())
            stopSelf()
        }

        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? = null

    private fun sendDataToServer(libros: List<Libro>) {
        Log.i("SyncService", "Datos enviados al servidor")
    }

    override fun onDestroy() {
        job.cancel()
        Log.i("SyncService", "Servicio detenido")
        super.onDestroy()
    }
}