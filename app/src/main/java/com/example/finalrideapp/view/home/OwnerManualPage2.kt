package com.example.finalrideapp.view.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.example.finalrideapp.R
import com.example.finalrideapp.fragment.FirstFragment
import com.example.finalrideapp.fragment.SecondFragment

class OwnerManualPage2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_manual_page2)

        var toolbar = findViewById(R.id.tbManualCopy) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var viewPager = findViewById(R.id.viewpager) as ViewPager
        setupViewPager(viewPager)

        var tabLayout = findViewById(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(viewPager)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.manual_copy_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuEdit -> {
                var ownerManualPage3 = Intent(this, OwnerManualPage3::class.java)
                startActivity(ownerManualPage3)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(FirstFragment(), "Personal Details")
        adapter.addFragment(SecondFragment(), "Bike Details")
        viewPager.adapter = adapter
    }
    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }
}
