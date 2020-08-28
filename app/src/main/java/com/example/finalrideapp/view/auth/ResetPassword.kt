package com.example.finalrideapp.view.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.finalrideapp.R
import com.example.finalrideapp.databinding.ActivityResetPasswordBinding
import com.example.finalrideapp.model.db.AppDatabase
import com.example.finalrideapp.model.network.ApiClient
import com.example.finalrideapp.model.network.MyApi
import com.example.finalrideapp.model.network.NetworkConnectionInterceptor
import com.example.finalrideapp.model.preferences.PreferenceProvider
import com.example.finalrideapp.model.repositories.NewUserRepository
import com.example.finalrideapp.model.repositories.UserRepository
import com.example.finalrideapp.util.toast
import com.example.finalrideapp.viewmodel.ResetPasswordViewModel
import com.example.finalrideapp.viewmodel.ResetPasswordViewModelFactory
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPassword : AppCompatActivity(),AuthListener {

    private var viewModel: ResetPasswordViewModel? = null
    private var binding: ActivityResetPasswordBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_reset_password)

        //val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val api = ApiClient(this)
        val db = AppDatabase(this)
        val prefs = PreferenceProvider(this)
        val repository = NewUserRepository(api, db, prefs)
        val factory = ResetPasswordViewModelFactory(repository, prefs)

        viewModel = ViewModelProviders.of(this, factory).get(ResetPasswordViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password)
        binding!!.resetPasswordViewModel = viewModel

        viewModel!!.authListener = this

        val resetToolbar: Toolbar = findViewById(R.id.resetToolbar) as Toolbar
        setSupportActionBar(resetToolbar)
        supportActionBar?.title = " "
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStarted() {
        Toast.makeText(this, "reset password started", Toast.LENGTH_LONG).show()
    }

    override fun inputValidation(field: Int, message: String) {
        if (field == 1) {
            binding!!.etNewPassword.error = message
            binding!!.etNewPassword.requestFocus()
        }
        if (field == 2) {
            binding!!.etConfirmPassword.error = message
            binding!!.etConfirmPassword.requestFocus()
        }
    }

    override fun onSuccess() {
        Toast.makeText(this, "reset password successful", Toast.LENGTH_LONG).show()
        val nextResetSuccessPage = Intent(this, ResetPasswordSuccess::class.java)
        startActivity(nextResetSuccessPage)
    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        val backLoginPage = Intent(this, Login::class.java)
        startActivity(backLoginPage)
    }
}
