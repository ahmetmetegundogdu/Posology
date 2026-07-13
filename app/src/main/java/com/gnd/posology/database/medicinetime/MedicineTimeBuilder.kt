package com.gnd.posology.database.medicinetime

data class MedicineTimeBuilder(var medicineName:String="",var time:Long=0,
                               var amount: Double=0.0,
                               var usageNote: String="") {

    fun build(medicineId: String): MedicineTime{
        return MedicineTime(medicineName,time,amount,medicineId,usageNote)
    }
}