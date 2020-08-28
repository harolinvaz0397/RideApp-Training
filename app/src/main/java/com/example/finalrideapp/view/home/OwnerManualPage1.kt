package com.example.finalrideapp.view.home

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.finalrideapp.R

class OwnerManualPage1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_manual_page1)
        /*
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "Owners manual"
         */

        var toolbar = findViewById(R.id.tbManualCopy) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this, OwnerManualPage2::class.java)
            startActivity(intent)
        }

    }
}