package com.davidhsu.newssideproject.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


/**
 *
 * @author : DavidHsu on 2019/03/28
 *
 */
class MainActivityViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val fragments = ArrayList<Fragment>()

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    fun addFragment(fragment: Fragment) = fragments.apply{
        add(fragment)
    }
}
