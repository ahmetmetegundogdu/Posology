package com.gnd.posology.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gnd.posology.DataManagerViewModel
import com.gnd.posology.R
import com.gnd.posology.RVTodayScheduleAdapter
import com.gnd.posology.database.medicine.MedicineDAO
import com.gnd.posology.database.medicinetime.MedicineTimeDAO


class TodayFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var rvAdapter: RVTodayScheduleAdapter

    val viewModel: DataManagerViewModel by  activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchData()
        Log.d("Deneme",viewModel.medicineList.value.size.toString())
        viewModel.dailyMedTimeList.observe(viewLifecycleOwner) { dailyList ->
            // Veri geldiğinde burası otomatik tetiklenir
            rvAdapter.updateData(dailyList, viewModel.medicineList.value ?: emptyList())
        }
        viewModel.medicineList.value.forEach {
            Log.d("Object",it.medicine_name)
        }
        recyclerView=view.findViewById(R.id.rv_schedule_list)
        rvAdapter= RVTodayScheduleAdapter(viewModel.dailyMedTimeList.value,viewModel.medicineList.value)
        recyclerView.layoutManager= LinearLayoutManager(requireContext())
        recyclerView.adapter=rvAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_today, container, false)

    }



}