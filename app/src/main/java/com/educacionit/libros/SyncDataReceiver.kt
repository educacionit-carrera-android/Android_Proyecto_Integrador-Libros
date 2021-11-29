package com.educacionit.libros

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class SyncDataReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("SyncDataReceiver", "Alarma recibida")
        context?.startService(Intent(context, SyncService::class.java))
    }
}
