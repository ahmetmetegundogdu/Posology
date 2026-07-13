package com.gnd.posology

import com.gnd.posology.database.MedicineWithTime

interface AlarmSchedular {
    fun schedule(item: MedicineWithTime,)
    fun cancel(item: MedicineWithTime)
}