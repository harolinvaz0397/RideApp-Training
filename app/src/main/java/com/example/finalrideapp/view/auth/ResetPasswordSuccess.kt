package com.example.finalrideapp.view.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.finalrideapp.R
import kotlinx.android.synthetic.main.activity_reset_password_success.*

class ResetPasswordSuccess : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password_success)

        var resetSuccessToolbar: Toolbar = findViewById(R.id.resetSuccessToolbar) as Toolbar
        setSupportActionBar(resetSuccessToolbar)
        supportActionBar?.title = " "
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnResetSuccessDone.setOnClickListener() {
            val nextLoginPage = Intent(this, Login::class.java)
            startActivity(nextLoginPage)
        }
    }
}
