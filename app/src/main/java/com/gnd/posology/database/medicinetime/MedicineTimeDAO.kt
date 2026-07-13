package com.gnd.posology.database.medicinetime

import androidx.room.Dao
import androidx.room.Insert
import io.reactivex.rxjava3.core.Completable

@Dao
interface MedicineTimeDAO {
    @Insert
    fun insert(medicineTime: MedicineTime): Completable

}