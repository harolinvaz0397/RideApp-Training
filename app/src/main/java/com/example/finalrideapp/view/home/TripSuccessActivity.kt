package com.example.finalrideapp.view.home

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.finalrideapp.R
import kotlinx.android.synthetic.main.activity_createtrip_success.*


class TripSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createtrip_success)


        val actionbar: ActionBar? =supportActionBar

        actionbar?.elevation = 0F
        actionbar?.setHomeAsUpIndicator(getDrawable(R.drawable.ic_arrow_back_black_24dp))
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setBackgroundDrawable(ColorDrawable(Color.WHITE))

        doneButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

