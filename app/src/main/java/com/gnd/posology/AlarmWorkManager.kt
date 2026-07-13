package com.gnd.posology

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.gnd.posology.database.AppDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.time.ZonedDateTime

class AlarmWorkManager(context: Context,params: WorkerParameters):CoroutineWorker(context,params) {
    val mDisposable: CompositeDisposable= CompositeDisposable()
    val db= Room.databaseBuilder(applicationContext, AppDatabase::class.java,"tables").build()
    val medicineDAO=db.medicineDao()
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        val id: String=inputData.getString("MEDICINE_ID").toString()
        var currentIndex=inputData.getInt("CURRENT_INDEX",0)
        var newTime:Long=0
        val item=medicineDAO.getByIdMedicineWithTime(id)

        val list=item.medicine_times.sortedBy { it.time }
        var newIndex=currentIndex+1
        val now=ZonedDateTime.now()

        if(now.toInstant().toEpochMilli()<item.medicine.start_day ){
            newTime=item.medicine.start_day+list[0].time
            alarmDirector(id,applicationContext, newTime,newIndex)
        }
        else if(now.toInstant().toEpochMilli()<item.medicine.end_day){
            if(newIndex>=list.size){
                val tomorrow= ZonedDateTime.now().toLocalDate().plusDays(1).atStartOfDay(
                    ZonedDateTime.now().zone).toInstant().toEpochMilli()
                if(tomorrow>=item.medicine.start_day && item.medicine.end_day>=tomorrow){
                    newTime=list[0].time +tomorrow
                    newIndex=1;
                    alarmDirector(id,applicationContext, newTime,newIndex)
                }

            }else{
                val startOfToday=now.toLocalDate().atStartOfDay(ZonedDateTime.now().zone).toInstant().toEpochMilli()
                newTime=list[newIndex].time+startOfToday
                while (true){
                    if(now.toInstant().toEpochMilli()<newTime){
                        alarmDirector(id,applicationContext, newTime,newIndex)
                        break
                    }
                    else{
                        newIndex++
                        if(newIndex>=list.size){
                            val tomorrow= ZonedDateTime.now().toLocalDate().plusDays(1).atStartOfDay(
                                ZonedDateTime.now().zone).toInstant().toEpochMilli()
                            if(tomorrow>=item.medicine.start_day && item.medicine.end_day>=tomorrow){
                                newTime=list[0].time +tomorrow
                                newIndex=1;
                                alarmDirector(id,applicationContext, newTime,newIndex)
                            }
                            break

                        }
                        newTime=list[newIndex].time+startOfToday


                    }
                }
        }




        }


        return Result.success()
    }
    fun alarmDirector(id: String, context: Context, time: Long, currentIndex: Int){

        val alarmManager=context.getSystemService(AlarmManager::class.java)
        val intent= Intent(context, AlarmReceiver::class.java).apply {
            putExtra("CURRENT_INDEX",currentIndex)
            putExtra("MEDICINE_ID",id)
        }
        val pendingIntent= PendingIntent.getBroadcast(context,id.hashCode(),intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,time,pendingIntent)

    }
}