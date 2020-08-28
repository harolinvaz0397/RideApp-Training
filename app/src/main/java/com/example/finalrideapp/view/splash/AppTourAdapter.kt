package com.example.finalrideapp.view.splash

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.finalrideapp.AppTourFragmentOne
import com.example.finalrideapp.AppTourFragmentThree
import com.example.finalrideapp.AppTourFragmentTwo

class AppTourAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return AppTourFragmentOne()

            1 -> return AppTourFragmentTwo()

            2 -> return AppTourFragmentThree()

            else -> return AppTourFragmentOne()
        }
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }

}