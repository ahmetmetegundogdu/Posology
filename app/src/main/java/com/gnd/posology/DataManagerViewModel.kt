package com.gnd.posology

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.gnd.posology.database.AppDatabase
import com.gnd.posology.database.MedicineWithTime
import com.gnd.posology.database.medicine.Medicine
import com.gnd.posology.database.medicinetime.MedicineTime
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.time.LocalDate
import java.time.ZoneOffset

class DataManagerViewModel(application: Application): AndroidViewModel(application) {
    val db= Room.databaseBuilder(application, AppDatabase::class.java,"tables").fallbackToDestructiveMigration().build()
    val medicineDAO=db.medicineDao()
    val mDisposable= CompositeDisposable()
    val dailyMedTimeList= MutableLiveData<List<MedicineTime>>(listOf())
    val medicineList= MutableLiveData<List<Medicine>>(listOf())

    var medicineWithTimeList=MutableLiveData<List<MedicineWithTime>>(listOf())


    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchData(){
         mDisposable.add(medicineDAO.getMedicineWithTime().subscribeOn(Schedulers.io()).observeOn(
             AndroidSchedulers.mainThread()).subscribe({list->
                val tempMedicineList=mutableListOf<Medicine>()
                val tempMedWTimeList=mutableListOf<MedicineWithTime>()
                list.forEach {
                    tempMedWTimeList.add(it)
                    tempMedicineList.add(it.medicine)
                }
                medicineList.value=tempMedicineList
                medicineWithTimeList.value=tempMedWTimeList
                getDailyData()

         }))

    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getDailyData(){
        val today = LocalDate.now()
        val todayStartMillis = today.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
        val tempDailyMedTimeList=mutableListOf<MedicineTime>()
        medicineWithTimeList.value.forEach {
            if(it.medicine.start_day<= todayStartMillis && todayStartMillis <= it.medicine.end_day){
                it.medicine_times.forEach {
                    tempDailyMedTimeList.add(it)
                }
            }
        }
        dailyMedTimeList.value=tempDailyMedTimeList
    }
}