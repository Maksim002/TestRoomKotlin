package com.example.testroomkotlin.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class PhotoPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm){

    private val fragmentList: MutableList<Fragment> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    fun addFragment(fragment: Fragment){
        fragmentList.add(fragment)
    }
}