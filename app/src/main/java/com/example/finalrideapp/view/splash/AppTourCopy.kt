package com.example.finalrideapp.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finalrideapp.view.auth.Login
import com.example.finalrideapp.R
import kotlinx.android.synthetic.main.activity_app_tour_copy.*

class AppTourCopy : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_tour_copy)

        btnYes.setOnClickListener() {
            val nextLoginPage = Intent(this, Login::class.java)
            startActivity(nextLoginPage)
        }

        btnNo.setOnClickListener() {
            btnNo.setBackgroundResource(R.drawable.yes_no_btn_pressed)
            val nextLoginPage = Intent(this, Login::class.java)
            startActivity(nextLoginPage)
        }
    }

    override fun onBackPressed() {

    }
}
