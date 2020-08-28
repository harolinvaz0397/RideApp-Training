package com.example.finalrideapp.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.example.finalrideapp.R
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalrideapp.fragment.FourthFragment
import com.example.finalrideapp.fragment.ThirdFragment

class OwnerManualPage3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_manual_page3)

        var toolbar = findViewById(R.id.tbManualCopyEdit) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getSupportFragmentManager().beginTransaction()
            .replace(R.id.PersonalDetailsFragment, ThirdFragment())
            .commit()

        getSupportFragmentManager().beginTransaction()
            .replace(R.id.BikeDetailsFragment, FourthFragment())
            .commit()

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.manual_copy_page3,menu)
        return super.onCreateOptionsMenu(menu)
    }

}
