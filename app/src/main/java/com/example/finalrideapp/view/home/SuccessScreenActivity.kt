package com.example.finalrideapp.view.home

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.example.finalrideapp.R
import kotlinx.android.synthetic.main.activity_success_screen.*
import timber.log.Timber


class SuccessScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_screen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        val upArrow =  ContextCompat.getDrawable(this,R.drawable.ic_arrow_back_black_24dp);
        //upArrow?.setColorFilter(ContextCompat.getColor(this, R.color.upArrowBlack), PorterDuff.Mode.SRC_ATOP);
        supportActionBar?.setHomeAsUpIndicator(upArrow);

        buttonOK.setOnClickListener{
            //val fragment = MyGarageFragment()

            val intentHomeActivity = Intent(this, HomeActivity::class.java)
            //intentHomeActivity.putExtra("Activity Name", 1)
            startActivity(intentHomeActivity)

        }
    }


}
