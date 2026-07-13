package com.gnd.posology.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(val fragmentList: List<Fragment>,val fragmentManager: FragmentManager,val lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle   ) {
    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getItemCount(): Int {
        return fragmentList.count()
    }
}