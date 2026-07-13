package com.gnd.posology

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gnd.posology.database.medicinetime.MedicineTimeBuilder

class AlarmRVAdapter(var list: List<MedicineTimeBuilder>): RecyclerView.Adapter<AlarmRVAdapter.AlarmRVViewHolder>() {
    fun updateList(newList: List<MedicineTimeBuilder>){
        list=newList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlarmRVViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.time_rv_view,parent,false)
        return AlarmRVViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: AlarmRVViewHolder,
        position: Int
    ) {
        val currentItem=list[position]
        holder.medicine_time.text=currentItem.time.toString()
        holder.medicine_dose.text=currentItem.amount.toString()
        holder.med_usage_note.text=currentItem.usageNote

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class AlarmRVViewHolder(val itemView: View): RecyclerView.ViewHolder(itemView){
        val medicine_time=itemView.findViewById<TextView>(R.id.medicine_time)
        val medicine_dose=itemView.findViewById<TextView>(R.id.medicine_dose)
        val med_usage_note=itemView.findViewById<TextView>(R.id.med_usage_note)

    }
}