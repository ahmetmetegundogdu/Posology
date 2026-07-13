package com.gnd.posology.addmedicineform

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gnd.posology.AlarmRVAdapter
import com.gnd.posology.FormViewModel
import com.gnd.posology.MainActivity
import com.gnd.posology.R
import com.gnd.posology.database.medicinetime.MedicineTimeBuilder
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.time.LocalTime
import java.time.temporal.ChronoField


class SecondStepFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second_step, container, false)
    }
    lateinit var fragment_nav_next: FloatingActionButton
    lateinit var add_alarm: Button
    lateinit var recyclerView: RecyclerView
    lateinit var rvAdapter: AlarmRVAdapter
    lateinit var fragment_nav_prev: FloatingActionButton
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: FormViewModel by activityViewModels()
        var alarmList: List<MedicineTimeBuilder> = listOf()
        recyclerView=view.findViewById(R.id.time_recyclerview)
        rvAdapter= AlarmRVAdapter(alarmList)
        recyclerView.adapter=rvAdapter
        recyclerView.layoutManager= LinearLayoutManager(requireContext())
        viewModel.alarmList.observe(viewLifecycleOwner){
            newList->
            rvAdapter.updateList(newList)
        }

        fragment_nav_next=view.findViewById(R.id.fragment_nav_next)
        fragment_nav_prev=view.findViewById(R.id.fragment_nav_prev)
        add_alarm=view.findViewById(R.id.add_alarm)
        fragment_nav_next.setOnClickListener {
            val intent= Intent(requireContext(), MainActivity::class.java)
            viewModel.addDataToDatabase()
            startActivity(intent)
        }
        fragment_nav_prev.setOnClickListener {
            findNavController().navigateUp()
        }
        add_alarm.setOnClickListener {
            val dialog= BottomSheetDialog(requireContext())
            val view=layoutInflater.inflate(R.layout.alarm_bottom_sheet,null)
            dialog.setContentView(view)
            val input_dose=view.findViewById<EditText>(R.id.input_medicine_dose)
            val input_note=view.findViewById<EditText>(R.id.input_alarm_note)
            val timePicker=view.findViewById<TimePicker>(R.id.time_picker)
            val unitTypeHolder=view.findViewById<TextView>(R.id.unit_type_holder)
            unitTypeHolder.text=viewModel.getUnit()
            timePicker.setIs24HourView(true)
            val set_alarm=view.findViewById<Button>(R.id.set_med_alarm_btn)
            set_alarm.setOnClickListener {
                val dose=input_dose.text.toString().toDoubleOrNull()

                if (dose==null){
                    input_dose.error="Lütfen geçerli bir değer giriniz!"
                    input_dose.requestFocus()
                    return@setOnClickListener
                }
                val note=input_note.text.toString()

                val time= LocalTime.of(timePicker.hour,timePicker.minute)

                viewModel.createMedTime(time.get(ChronoField.MILLI_OF_DAY).toLong(),dose,note)

                dialog.dismiss()
            }
            dialog.show()

        }
    }


}