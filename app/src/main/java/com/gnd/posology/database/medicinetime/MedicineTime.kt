package com.gnd.posology.database.medicinetime

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicine_time_db")
data class MedicineTime(
    var medicineName:String,
    var time:Long,
    var amount: Double,
    var medicineId: String,
    val usageNote: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int=0
}