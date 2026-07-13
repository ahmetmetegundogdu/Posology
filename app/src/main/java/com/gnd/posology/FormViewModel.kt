package com.gnd.posology

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.gnd.posology.database.AppDatabase
import com.gnd.posology.database.medicine.Medicine
import com.gnd.posology.database.medicine.MedicineBuilder
import com.gnd.posology.database.medicinetime.MedicineTime
import com.gnd.posology.database.medicinetime.MedicineTimeBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class FormViewModel(application: Application): AndroidViewModel(application) {
    val mDisposable= CompositeDisposable()
    private val _alarmList= MutableLiveData<List<MedicineTimeBuilder>>(emptyList())
    val alarmList=_alarmList

    var medicationBuilder= MedicineBuilder()
    var medicineDB= Room.databaseBuilder(application, AppDatabase::class.java,"tables" ).fallbackToDestructiveMigration().build()
    var medicineDao=medicineDB.medicineDao()
    var medicineTimeDao=medicineDB.medicineTimeDao()

    fun addDataToDatabase(){
        val medicine=medicationBuilder.build()
        mDisposable.add(medicineDao.insert(medicine).subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread()).subscribe())
        _alarmList.value.forEach {
            val newMedicineTime: MedicineTime=it.build(medicine.medicineId)
            mDisposable.add(medicineTimeDao.insert(newMedicineTime).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()).subscribe())
        }
    }
    fun addAlarm(item: MedicineTimeBuilder){
        val currentList=_alarmList.value.orEmpty().toMutableList()
        currentList.add(item)
        _alarmList.value=currentList
    }

    fun setName(name: String){
        medicationBuilder.medicine_name=name
    }
    fun setUnit(unit: String){
        medicationBuilder.unit=unit
    }
    fun setDateRange(startDay:Long,endDay:Long){
        medicationBuilder.start_day=startDay
        medicationBuilder.end_day=endDay
    }

    fun setGeneralNote(note: String){
        medicationBuilder.usage_note=note
    }



    fun getName(): String{
        return medicationBuilder.medicine_name
    }

    fun getUnit():String{
        return medicationBuilder.unit
    }

    fun createMedTime(time:Long, dose: Double, usageNot: String)  {
        val medicineTimeBuilder= MedicineTimeBuilder(medicationBuilder.medicine_name,time,dose,usageNot)
        addAlarm(medicineTimeBuilder)

    }
}