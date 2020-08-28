package com.example.finalrideapp.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.finalrideapp.R
import com.example.finalrideapp.model.preferences.PreferenceProvider
import com.example.finalrideapp.util.isTokenExpire
import com.example.finalrideapp.view.auth.Login
import com.example.finalrideapp.view.auth.OtpVerify
import com.example.finalrideapp.view.auth.Register
import com.example.finalrideapp.view.home.HomeActivity
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar?.hide()

        val time = PreferenceProvider(this).getTime().toString()
        val currentTime = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US).format(Date()).toString()

        var isTokenExpire: Boolean = false
        if (time != "null") {
            isTokenExpire = isTokenExpire(time, currentTime)
        }

        val status = PreferenceProvider(this).getAccessToken().toString()
        if (status == "null") {
            Handler().postDelayed({startActivity(Intent(this, AppTourActivity::class.java))
                finish() }
                , 3000)
        } else if (status != "null" && isTokenExpire == true) {
            Handler().postDelayed({startActivity(Intent(this, Login::class.java))
                finish() }
                , 3000)
        }else {
            Handler().postDelayed( { startActivity(Intent(this, HomeActivity::class.java))
                finish() }
                , 3000)
        }


    }
}

/*
if (PreferenceProvider(this).getDetails() == null) {
            Handler().postDelayed({startActivity(Intent(this, AppTourActivity::class.java))
                finish() }
                , 3000)
        }

        Handler().postDelayed({startActivity(Intent(this, HomeActivity::class.java))
            finish() }
            , 3000)





           Handler().postDelayed({startActivity(Intent(this, AppTourActivity::class.java))
            finish() }
            , 3000)

 */
