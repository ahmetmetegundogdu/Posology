package com.gnd.posology.database.medicine

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "medicine_table")
data class Medicine(
    val medicine_name: String,
    val start_day:Long,
    val end_day: Long,
    val usage_note: String,
    val unit:String){
    @PrimaryKey
    var medicineId: String= UUID.randomUUID().toString()

}


