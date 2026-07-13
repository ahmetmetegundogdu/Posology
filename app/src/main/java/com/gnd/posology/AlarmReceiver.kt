package com.gnd.posology

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver(context: Context,intent: Intent): BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val medicineId=intent.getStringExtra("MEDICINE_ID")
    }

}