package com.twq.todoapp.Fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(Activity:FragmentActivity):FragmentStateAdapter(Activity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return AllToDoFragment()
            1 -> return PendingFragment()
            2 -> return DoneFragment()
        }
        return AllToDoFragment()
    }
}