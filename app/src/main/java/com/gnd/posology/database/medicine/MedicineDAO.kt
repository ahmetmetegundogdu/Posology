package com.gnd.posology.database.medicine

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.gnd.posology.database.MedicineWithTime
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface MedicineDAO {
    @Insert
    fun insert(medicine: Medicine): Completable
    @Delete
    fun delete(medicine: Medicine): Completable
    @Transaction
    @Query("SELECT * FROM medicine_table")
    fun getMedicineWithTime(): Flowable<List<MedicineWithTime>>

    @Transaction
    @Query("SELECT * FROM medicine_table WHERE medicineId=:id")
    suspend fun getByIdMedicineWithTime(id: String): MedicineWithTime
}