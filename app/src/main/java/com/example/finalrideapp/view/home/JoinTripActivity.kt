package com.example.finalrideapp.view.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finalrideapp.R
import com.mapbox.mapboxsdk.Mapbox
import kotlinx.android.synthetic.main.activity_join_trip.*

class JoinTripActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this, getString(R.string.MAPBOX_ACCESS_TOKEN))
        setContentView(R.layout.activity_join_trip)

        btnJoinTrip.setOnClickListener {
            startActivity(Intent(this, JoinTripSuccessActivity::class.java))
            finish()
        }
    }
}
