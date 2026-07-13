package com.gnd.posology.database

import androidx.room.Embedded
import androidx.room.Relation
import com.gnd.posology.database.medicine.Medicine
import com.gnd.posology.database.medicinetime.MedicineTime

data class MedicineWithTime(
    @Embedded var medicine: Medicine,
    @Relation(
        parentColumn = "medicineId",
        entityColumn = "medicineId"
    ) var medicine_times: List<MedicineTime>
) {
}