package com.example.finalrideapp.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.finalrideapp.R
import com.example.finalrideapp.fragment.MyGarageFragment
import com.example.finalrideapp.fragment.WelcomeFragment
import kotlinx.android.synthetic.main.activity_home.*
import timber.log.Timber
import java.util.*

class HomeActivity : AppCompatActivity() {

    private lateinit var navigationController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_home)

        /*
        navBar.setOnNavigationItemSelectedListener { item ->
            var fragment: Fragment? = null
            when (item.itemId) {
                R.id.nav_trips_fragment -> fragment = WelcomeFragment()
                R.id.nav_my_garage_fragment -> fragment = MyGarageFragment()
            }

            supportFragmentManager
                .beginTransaction()
                .addToBackStack(fragment!!.tag)
                .replace(R.id.host_fragment, fragment)
                .commit()

            true
        }

         */


        //get nav controller
        navigationController = Navigation.findNavController(this, R.id.host_fragment)

        //Setting the navigation controller to Bottom Nav
        navBar.setupWithNavController(navigationController)

        //set with action bar
        NavigationUI.setupActionBarWithNavController(this,navigationController)


    }

    override fun onPause() {
        super.onPause()

    }

    override fun onStop() {
        super.onStop()

    }
   /* override fun onBackPressed() {

        var fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 1) {
            Log.d("Message", fragmentManager.backStackEntryCount.toString())
            Log.d("Message:","popping backstack")
            fragmentManager.popBackStack()
        }
        else{
           Log.d("Message:","nothing on backStack")
            super.onBackPressed()
        }
    }*/
}
