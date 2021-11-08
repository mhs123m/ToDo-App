package com.twq.todoapp.Fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(Activity:FragmentActivity):FragmentStateAdapter(Activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return TodayFragment()
            1 -> return WeekFragment()
        }
        return TodayFragment()
    }
}