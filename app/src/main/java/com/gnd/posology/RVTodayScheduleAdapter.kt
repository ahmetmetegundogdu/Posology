package com.gnd.posology

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gnd.posology.database.MedicineWithTime
import com.gnd.posology.database.medicine.Medicine
import com.gnd.posology.database.medicinetime.MedicineTime

class RVTodayScheduleAdapter(var data_list: List<MedicineTime>,var medicineList: List<Medicine>):
    RecyclerView.Adapter<RVTodayScheduleAdapter.RVViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RVViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.alarm_rv_view,parent,false)
        return RVViewHolder(view)
    }
    fun updateData(n_data_list: List<MedicineTime>,n_medicineList: List<Medicine>){
        data_list=n_data_list
        medicineList=n_medicineList
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(
        holder: RVViewHolder,
        position: Int
    ) {
        val currentItem=data_list[position]
        val medicine=medicineList.first{currentItem.medicineId==it.medicineId}
        holder.ic_item.setImageResource(R.drawable.ic_medicine)
        holder.name_text.text=medicine.medicine_name+"-"+medicine.unit
        holder.note_text.text=currentItem.usageNote
        holder.time_text.text="x"
        holder.confirmBtn.text="Aldım"
    }

    override fun getItemCount(): Int {
        return data_list.size
    }

    class RVViewHolder(view: View): RecyclerView.ViewHolder(view){
        val ic_item=view.findViewById<ImageView>(R.id.ic_item)
        val name_text=view.findViewById<TextView>(R.id.textNameTitle)
        val note_text=view.findViewById<TextView>(R.id.note_text)
        val time_text=view.findViewById<TextView>(R.id.timeTitle)
        val confirmBtn=view.findViewById<Button>(R.id.confirm_btn)

    }
}