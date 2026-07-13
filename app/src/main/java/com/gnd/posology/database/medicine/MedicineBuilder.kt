package com.gnd.posology.database.medicine



data class MedicineBuilder(
    var medicine_name: String="",
    var start_day:Long=0,
    var end_day: Long=0,
    var usage_note: String="",
    var unit:String=""){
    fun build(): Medicine{
        return Medicine(medicine_name,start_day,end_day,usage_note,unit)
    }
}