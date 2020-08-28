package com.example.finalrideapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.finalrideapp.model.db.entities.User
import com.example.finalrideapp.model.repositories.NewUserRepository
import com.example.finalrideapp.model.repositories.UserRepository
import com.example.finalrideapp.util.*
import com.example.finalrideapp.view.auth.AuthListener
import java.text.SimpleDateFormat
import java.util.*

class RegisterViewModel(private val repository: NewUserRepository): ViewModel() {

    var name: String? = null
    var phone: String? = null
    var email: String? = null
    var pass: String? = null

    var authListener: AuthListener? = null

    fun registerOnClick() {
        Log.d("hello","started")
        authListener?.onStarted()

        if(name.isNullOrEmpty()){
            authListener?.inputValidation(1, "Enter Name")
            return
        }

        if(phone.isNullOrEmpty()) {
            authListener?.inputValidation(2,"Enter Phone No")
            return
        }
        if (!isPhoneValid(phone.toString())) {
            authListener?.inputValidation(2,"Enter Valid Phone No")
            return
        }

        if(email.isNullOrEmpty()){
            authListener?.inputValidation(3, "Enter Email")
            return
        }
        if(!isEmailValid(email.toString())){
            authListener?.inputValidation(3, "Enter Valid Email")
            return
        }

        if(pass.isNullOrEmpty()) {
            authListener?.inputValidation(4,"Enter Password")
            return
        }
        if(!isPasswordValid(pass.toString())) {
            authListener?.inputValidation(4,"Enter Valid Password")
            return
        }

        Coroutines.main {
            try {
                val authResponse = repository.funUserRegister(name.toString(), email.toString(), phone.toString(), pass.toString())
                Log.d("hello","started")
                authResponse.data?.let {
                    var otpVerificationID = authResponse.data.get("otpVerificationID").toString()
                    otpVerificationID = otpVerificationID.substring(1, otpVerificationID.length-1)
                    repository.saveOtpVerificationID(otpVerificationID)
                    authListener?.onSuccess()

                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            } catch(e: ApiException){
                authListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                authListener?.onFailure(e.message!!)
            }
        }

    }
}