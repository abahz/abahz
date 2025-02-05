package com.abahz.abahz.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.abahz.abahz.databases.MyUtils

class Adapters(fm: FragmentActivity) : FragmentStateAdapter(fm) {
    override fun createFragment(position: Int): Fragment = MyUtils.fragmentLists[position]
    override fun getItemCount(): Int = MyUtils.fragmentLists.size
}