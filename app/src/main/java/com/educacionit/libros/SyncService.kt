package com.educacionit.libros

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class SyncService : Service() {

    private lateinit var executor: ScheduledExecutorService
    private lateinit var job: Job
    private val librosRepository: LibrosRepository by lazy { LibrosRepository(applicationContext) }
    private val executeSync = Runnable {
        job = GlobalScope.launch(Dispatchers.IO) {
            sendDataToServer(librosRepository.getLibros())
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("SyncService", "Servicio iniciado")
        executor = Executors.newScheduledThreadPool(1)
        executor.scheduleWithFixedDelay(executeSync, 1L, 5L, TimeUnit.MINUTES)

        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? = null

    private fun sendDataToServer(libros: List<Libro>) {
        Log.i("SyncService", "Datos enviados al servidor")
    }

    override fun onDestroy() {
        job.cancel()
        executor.shutdown()
        Log.i("SyncService", "Servicio detenido")
        super.onDestroy()
    }
}