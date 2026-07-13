package com.gnd.posology.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gnd.posology.MedicineFormActivity
import com.gnd.posology.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class TherapyFragment : Fragment() {
    lateinit var addMedBtn: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_therapy, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addMedBtn=view.findViewById(R.id.add_med_btn)
        addMedBtn.setOnClickListener {
            val intent= Intent(requireContext(), MedicineFormActivity::class.java)
            startActivity(intent)
        }
    }


}