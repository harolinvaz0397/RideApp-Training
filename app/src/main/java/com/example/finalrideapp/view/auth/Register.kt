package com.example.finalrideapp.view.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.finalrideapp.R
import com.example.finalrideapp.databinding.ActivityRegisterBinding
import com.example.finalrideapp.model.db.AppDatabase
import com.example.finalrideapp.model.network.ApiClient
import com.example.finalrideapp.model.network.MyApi
import com.example.finalrideapp.model.network.NetworkConnectionInterceptor
import com.example.finalrideapp.model.preferences.PreferenceProvider
import com.example.finalrideapp.model.repositories.NewUserRepository
import com.example.finalrideapp.model.repositories.UserRepository
import com.example.finalrideapp.viewmodel.RegisterViewModelFactory
import com.example.finalrideapp.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity(), AuthListener {

    private var viewModel: RegisterViewModel? = null
    private var binding: ActivityRegisterBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val api = ApiClient(this)
        val db = AppDatabase(this)
        val prefs = PreferenceProvider(this)
        val repository = NewUserRepository(api, db, prefs)
        val factory = RegisterViewModelFactory(repository)

        viewModel = ViewModelProviders.of(this, factory).get(RegisterViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding!!.registerViewModel = viewModel

        viewModel!!.authListener = this

        val registerToolbar: Toolbar = findViewById(R.id.registerToolbar) as Toolbar
        setSupportActionBar(registerToolbar)
        supportActionBar?.title = "Register"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onStarted() {
        Toast.makeText(this, "register started", Toast.LENGTH_LONG).show()

    }

    override fun inputValidation(field: Int, message: String) {
        if (field == 1) {
            binding!!.etRegisterName.error = message
            binding!!.etRegisterName.requestFocus()
        }
        if (field == 2) {
            binding!!.etRegisterPhone.error = message
            binding!!.etRegisterPhone.requestFocus()
        }
        if (field == 3) {
            binding!!.etRegisterEmail.error = message
            binding!!.etRegisterEmail.requestFocus()
        }
        if (field == 4){
            binding!!.etRegisterPassword.error = message
            binding!!.etRegisterPassword.requestFocus()
        }

    }

    override fun onSuccess() {
        //Toast.makeText(this, "successfully registered", Toast.LENGTH_LONG).show()
        //Log.d("token",token)

        val register = "Register"
        val nextOtpPage = Intent(this, OtpVerify::class.java)
        nextOtpPage.putExtra("backActivity", register)
        startActivity(nextOtpPage)
    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        Log.d("failled",message)
    }
}
