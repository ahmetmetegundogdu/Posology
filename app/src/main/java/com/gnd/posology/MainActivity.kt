package com.gnd.posology

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.gnd.posology.fragments.FragmentAdapter
import com.gnd.posology.fragments.MeFragment
import com.gnd.posology.fragments.TherapyFragment
import com.gnd.posology.fragments.TodayFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var viewPager: ViewPager2
    lateinit var tabLayout: TabLayout

    lateinit var fragmentAdapter: FragmentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewPager=findViewById(R.id.viewpager)
        tabLayout=findViewById(R.id.tabLayout)
        val fragmentList=mutableListOf(TodayFragment(), TherapyFragment(), MeFragment())
        fragmentAdapter= FragmentAdapter(fragmentList, supportFragmentManager, lifecycle)
        viewPager.adapter=fragmentAdapter
        TabLayoutMediator(tabLayout,viewPager){tab,position->
            when{
                position==0->tab.text="Today"
                position==1->tab.text="Therapy"
                position==2->tab.text="Me"
            }
        }.attach()
    }
}