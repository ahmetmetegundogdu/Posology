package com.gnd.posology

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.gnd.posology.database.MedicineWithTime
import com.gnd.posology.database.medicinetime.MedicineTime
import java.time.LocalTime
import java.time.temporal.ChronoField

class DoseAlarmSchedular(val context: Context): AlarmSchedular {
    val alarmManager=context.getSystemService(AlarmManager::class.java)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun schedule(item: MedicineWithTime) {
        val firstTime=item.medicine_times.first().time
        val intent= Intent(context, AlarmReceiver::class.java).apply {
            putExtra("MEDICINE_ID",item.medicine.medicineId)
        }

        val pendingIntent= PendingIntent.getBroadcast(context,item.medicine.medicineId.hashCode(),intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)



        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,firstTime,pendingIntent
        )
    }

    override fun cancel(item: MedicineWithTime) {
        TODO("Not yet implemented")
    }
}