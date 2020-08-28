package com.example.finalrideapp.view.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.finalrideapp.R
import com.example.finalrideapp.databinding.ActivityLoginBinding
import com.example.finalrideapp.model.db.AppDatabase
import com.example.finalrideapp.model.network.ApiClient
import com.example.finalrideapp.model.network.MyApi
import com.example.finalrideapp.model.network.NetworkConnectionInterceptor
import com.example.finalrideapp.model.preferences.PreferenceProvider
import com.example.finalrideapp.model.repositories.NewUserRepository
import com.example.finalrideapp.model.repositories.UserRepository
import com.example.finalrideapp.view.home.HomeActivity
import com.example.finalrideapp.viewmodel.LoginViewModel
import com.example.finalrideapp.viewmodel.LoginViewModelFactory
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity(), AuthListener {

    private var viewModel: LoginViewModel? = null
    private var binding: ActivityLoginBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)

        val api = ApiClient(this)
        val db = AppDatabase(this)
        val prefs = PreferenceProvider(this)
        val repository = NewUserRepository(api, db, prefs)
        val loginFactory = LoginViewModelFactory(repository)

        viewModel = ViewModelProviders.of(this, loginFactory).get(LoginViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding!!.loginViewModel = viewModel

        viewModel!!.authListener = this
/*
        val current: String = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        viewModel!!.getUserDetails().observe(this, Observer {user ->
            if (user.token != null) {
               /* if (isTokenExpire(user.starTokenTime.toString(), current)) {
                    viewModel!!
                } */
                val time = user.starTokenTime.toString()
                Log.d("time", time)
                val nextRegisterProfilePage = Intent(this, RegisterProfile::class.java)
                startActivity(nextRegisterProfilePage)
            }
        })

 */
    viewModel!!.userName.observe(this, Observer {
        val prefs = PreferenceProvider(this)
        //val userName = prefs.getRiderName()
        if (prefs.getIsNewUser()) {
            val nextRegisterProfilePage = Intent(this, RegisterProfile::class.java)
            nextRegisterProfilePage.putExtra("Rider Name", it)
            startActivity(nextRegisterProfilePage)
            finish()
        }
        else {
            val intentHomeActivity = Intent(this, HomeActivity::class.java)
            startActivity(intentHomeActivity)
            finish()


        }

    })

    viewModel!!.forgotOnClick.observe(this, Observer {
        if (it.toString() == "success") {
            val nextOtpPage = Intent(this, OtpVerify::class.java)
            val reset = "Reset"
            nextOtpPage.putExtra("backActivity", reset)
            startActivity(nextOtpPage)
        }
    })

        tvLoginRegister.setOnClickListener() {
            val nextRegisterPage = Intent(this, Register::class.java)
            startActivity(nextRegisterPage)
        }

    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }


    override fun onStarted() {
        //Toast.makeText(this, "login started", Toast.LENGTH_LONG).show()
        Log.d("Message:","Login started")

    }

    override fun inputValidation(field: Int, message: String) {
        if (field == 1) {
            binding!!.etLoginEmail.error = message
            binding!!.etLoginEmail.requestFocus()
        }
        if (field == 2) {
            binding!!.etLoginPassword.error = message
            binding!!.etLoginPassword.requestFocus()
        }
    }

    override fun onSuccess() {
       // Toast.makeText(this, "login successful", Toast.LENGTH_LONG).show()
        Log.d("Message:","Login successful")
        //Log.d("token",token)
        viewModel?.getRiderDetails(this)
        /**/
    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        Log.d("failed",message)
    }
}
