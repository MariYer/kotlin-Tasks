package com.mariyer.taskmanager.ui.details.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mariyer.taskmanager.ui.EmptyFragment
import com.mariyer.taskmanager.ui.details.EmployerBasicInfoFragment
import com.mariyer.taskmanager.ui.details.EmployerStaffFragment

class EmployerPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity)  {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> EmployerBasicInfoFragment()
            1 -> EmployerStaffFragment()
            else -> EmptyFragment()
        }
    }
}