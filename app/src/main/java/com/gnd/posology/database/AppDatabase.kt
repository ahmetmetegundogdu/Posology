package com.gnd.posology.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gnd.posology.database.medicine.Medicine
import com.gnd.posology.database.medicine.MedicineDAO
import com.gnd.posology.database.medicinetime.MedicineTime
import com.gnd.posology.database.medicinetime.MedicineTimeDAO

@Database(entities = [Medicine::class, MedicineTime::class], version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun medicineDao(): MedicineDAO
    abstract fun medicineTimeDao(): MedicineTimeDAO
}