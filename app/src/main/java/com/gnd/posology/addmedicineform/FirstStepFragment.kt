package com.gnd.posology.addmedicineform

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.gnd.posology.FormViewModel
import com.gnd.posology.R
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


class FirstStepFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first_step, container, false)
    }

    lateinit var fragmentNav: FloatingActionButton
    lateinit var input_medicine_name: TextInputEditText
    lateinit var date_range_holder: TextInputEditText
    lateinit var input_med_note: TextInputEditText
    lateinit var dropdownMenu: AutoCompleteTextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        date_range_holder=view.findViewById(R.id.date_picker)
        val viewModel: FormViewModel by activityViewModels()
        dropdownMenu=view.findViewById(R.id.medicine_unit_menu)
        var unitValue=""
        dropdownMenu.setOnItemClickListener { parent, _, position,_->
            unitValue=parent.getItemAtPosition(position).toString()
        }
        val medicineUnits = listOf(
            "mg",      // Miligram
            "ml",      // Mililitre
            "g",       // Gram
            "ölçek",   // Genellikle şuruplar için kullanılan kaşık/kap ölçeği
            "damla",   // Damla sayısı
            "tablet",  // Adet bazlı
            "kapsül",   // Adet bazlı
            "mcg",     // Mikrogram
            "IU",      // International Unit (Uluslararası Birim)
            "mmol",    // Milimol
            "mEq"     // Miliequivalent

        )
        val adapter= ArrayAdapter(requireContext(),R.layout.dropdown_item,medicineUnits)

        dropdownMenu.setAdapter(adapter)
        date_range_holder.setOnClickListener {
            val dateRangePicker= MaterialDatePicker.Builder.dateRangePicker().setTitleText("Tarih aralığı seç.").build()
            dateRangePicker.addOnPositiveButtonClickListener { selection->
                val dateFirst=selection.first
                val dateSecond=selection.second
                viewModel.setDateRange(dateFirst,dateSecond)
                date_range_holder.setText("${formatDate(dateFirst)}-${formatDate(dateSecond)}")
            }
            dateRangePicker.show(parentFragmentManager,"Date_picker")
        }


        fragmentNav=view.findViewById(R.id.fragment_nav_next)
        input_medicine_name=view.findViewById(R.id.med_name)
        input_med_note=view.findViewById(R.id.med_usage_note)
        input_medicine_name.setText(viewModel.getName())

        fragmentNav.setOnClickListener {
            var med_name: String=input_medicine_name.text.toString().trim()
            var med_note: String=input_med_note.text.toString().trim()
            if (med_name=="" || med_name.equals("") || med_name==null){
                input_medicine_name.error="Lütfen ilacınızın ismini giriniz."
                input_medicine_name.requestFocus()
            }
            else if(unitValue=="" || unitValue.equals("")){
                dropdownMenu.error="Lütfen ilaç birimi seçiniz."
                dropdownMenu.requestFocus()
            }
            else{
                viewModel.setUnit(unitValue.trim())
                viewModel.setName(med_name)
                viewModel.setGeneralNote(med_note)
                findNavController().navigate(R.id.action_firstStepFragment_to_secondStepFragment)


            }
        }


    }
    fun formatDate(ms: Long): String{
        val date= Date(ms)
        val format= SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        format.timeZone= TimeZone.getTimeZone("UTC")
        return format.format(date)
    }

}