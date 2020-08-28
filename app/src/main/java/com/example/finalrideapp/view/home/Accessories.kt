package com.example.finalrideapp.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalrideapp.Adapters.AccessoriesAdapter
import com.example.finalrideapp.R

class Accessories : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accessories)

        var toolbar = findViewById(R.id.tbAccessories) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val rview = findViewById<View>(R.id.rvAccessories) as RecyclerView
        val picture = intArrayOf(R.drawable.bmap1,R.drawable.bmap2,R.drawable.bmap4,R.drawable.bmap3,R.drawable.bmap1,R.drawable.bmap2,R.drawable.bmap4,R.drawable.bmap3)


        val name = arrayOf("Probiker Gloves","Mike Gloves","Adidas Tubular Gloves","Nike Airmax 1","Probiker Gloves","Mike Gloves","Adidas Tubular Gloves","Nike Airmax 1")
        val price = arrayOf("Rs.189.00","Rs 219.00","Rs.189.00","Rs.189.00","Rs.189.00","Rs 219.00","Rs.189.00","Rs.189.00")
        val date = arrayOf("22 SEP", "28 SEP","29 SEP", "29 SEP","22 SEP", "28 SEP","29 SEP", "29 SEP")

        val lManager = GridLayoutManager(this,2, RecyclerView.VERTICAL,false)
        rview.layoutManager = lManager

        rview.adapter = AccessoriesAdapter(picture,name,price,date,this)
    }
}
