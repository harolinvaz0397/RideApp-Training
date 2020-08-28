package com.example.finalrideapp.view.auth

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.finalrideapp.R
import com.example.finalrideapp.model.preferences.PreferenceProvider
import com.example.finalrideapp.view.home.HomeActivity
import kotlinx.android.synthetic.main.activity_register_profile_success.*

class RegisterProfileSuccess : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_profile_success)

        val toolbarRegisterProfileSuccess = findViewById(R.id.tbRegisterProfileSuccess) as Toolbar
        setSupportActionBar(toolbarRegisterProfileSuccess)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        //val picturePath = getIntent().getStringExtra("picture")
        val picturePath = PreferenceProvider(this).getPath()

        if (picturePath != null) {

            //Log.d("path success", picturePath)

            val imageView2 = findViewById<ImageView>(R.id.imageView2)
            imageView2.visibility = View.INVISIBLE
            val cardviewProfileImage = findViewById<CardView>(R.id.cardviewProfileImage)
            cardviewProfileImage.visibility = View.VISIBLE
            val scaledBitmap: Bitmap = BitmapFactory.decodeFile(picturePath)
            val imageviewProfileImage = findViewById(R.id.imageviewProfileImage) as ImageView
            imageviewProfileImage.visibility = View.VISIBLE
            imageviewProfileImage.setImageBitmap(scaledBitmap)
        }

        btnLetGetStart.setOnClickListener {
            val nextHomePage = Intent(this, HomeActivity::class.java)
            startActivity(nextHomePage)
        }
    }
}
