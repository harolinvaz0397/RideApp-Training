package com.example.finalrideapp.view.splash

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.finalrideapp.R
import com.google.android.material.tabs.TabLayout

class AppTourActivity : AppCompatActivity() {

    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_tour)

        tabLayout = findViewById<TabLayout>(R.id.tablayout)
        viewPager = findViewById<ViewPager>(R.id.viewPager)


        val adapter = AppTourAdapter(
            this,
            supportFragmentManager,
            tabLayout!!.tabCount
        )
        viewPager!!.adapter = adapter

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            @SuppressLint("RestrictedApi")
            override fun onTabSelected(tab: TabLayout.Tab) {

                if(tab.position == 2)
                {
                    supportActionBar?.hide()
                    viewPager!!.currentItem = tab.position
                }
                else {
                    supportActionBar?.show()
                    viewPager!!.currentItem = tab.position
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }
}
