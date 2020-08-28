package com.example.finalrideapp.view.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.finalrideapp.R
import com.example.finalrideapp.databinding.ActivityOtpVerifyBinding
import com.example.finalrideapp.model.db.AppDatabase
import com.example.finalrideapp.model.network.ApiClient
import com.example.finalrideapp.model.preferences.PreferenceProvider
import com.example.finalrideapp.model.repositories.NewUserRepository
import com.example.finalrideapp.viewmodel.OtpVerifyViewModel
import com.example.finalrideapp.viewmodel.OtpVerifyViewModelFactory
import kotlinx.android.synthetic.main.activity_otp_verify.*
import kotlinx.android.synthetic.main.activity_owner_manual_page1.view.*

class OtpVerify : AppCompatActivity(), AuthListener {

    private var viewModel: OtpVerifyViewModel? = null
    private var binding: ActivityOtpVerifyBinding? = null
    lateinit var backActivity: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_otp_verify)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        val api = ApiClient(this)
        val db = AppDatabase(this)
        val prefs = PreferenceProvider(this)
        val repository = NewUserRepository(api, db, prefs)
        val factory = OtpVerifyViewModelFactory(repository)

        viewModel = ViewModelProviders.of(this, factory).get(OtpVerifyViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp_verify)
        binding!!.otpViewModel = viewModel

        viewModel!!.authListener = this

        //setContentView(R.layout.activity_otp_verify)

        var toolbar = findViewById(R.id.tbOtpVerify) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
            //onBackPressedDispatcher.onBackPressed()
        }

        backActivity = getIntent().getStringExtra("backActivity")


        viewModel!!.seconds.observe(this, Observer {
            tvSecondsCount.setText("" + it.toString() + " seconds left")
        })

        viewModel!!.resetOtp.observe(this, Observer {
            Toast.makeText(this, "Enter Resent Otp", Toast.LENGTH_LONG).show()
        })

    }

    override fun onStarted() {
        Toast.makeText(this, "otp verification is started", Toast.LENGTH_LONG).show()
    }

    override fun inputValidation(field: Int, message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSuccess() {
        Toast.makeText(this, "successfull", Toast.LENGTH_LONG).show()
        if (backActivity.toString() == "Register") {
            val preferenceProvider = PreferenceProvider(this)
            preferenceProvider.setIsNewUser(true)
            val nextLoginPage = Intent(this, Login::class.java)
            startActivity(nextLoginPage)
        }
        if (backActivity.toString() == "Reset"){
            val nextResetPassword = Intent(this, ResetPassword::class.java)
            startActivity(nextResetPassword)
        }

    }

    override fun onFailure(message: String) {
        txt_pin_entry.text!!.clear()
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()    }

}
