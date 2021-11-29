package com.educacionit.libros

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_AIRPLANE_MODE_CHANGED
import android.provider.Settings
import android.widget.Toast

class AirplaneStateReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == ACTION_AIRPLANE_MODE_CHANGED) {
            if (isAirplaneModeOff(context)) {
                Toast.makeText(
                    context, "Modo de avión activado. " +
                            "Esto puede afectar algunas funcionalidades de la aplicación",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun isAirplaneModeOff(context: Context?): Boolean {
        return Settings.Global.getInt(
            context?.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON,
            0
        ) == 1
    }
}
