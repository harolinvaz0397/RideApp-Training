package com.example.finalrideapp.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.finalrideapp.model.db.entities.User
import com.example.finalrideapp.model.preferences.PreferenceProvider
import com.example.finalrideapp.model.repositories.NewUserRepository
import com.example.finalrideapp.model.repositories.UserRepository
import com.example.finalrideapp.util.*
import com.example.finalrideapp.view.auth.AuthListener
import java.text.SimpleDateFormat
import java.util.*

class ResetPasswordViewModel(private val repository: NewUserRepository,
                             private val prefs: PreferenceProvider): ViewModel() {

    var  newPassword: String? = null
    var  confirmPassword: String? = null

    var authListener: AuthListener? = null

    fun resetOnClick() {

        Log.d("hello","started")
        authListener?.onStarted()

        if(newPassword.isNullOrEmpty()) {
            authListener?.inputValidation(1,"Enter Password")
            return
        }
        if(!isPasswordValid(newPassword.toString())) {
            authListener?.inputValidation(1,"Enter Valid Password")
            return
        }
        if (!isPasswordMatching(newPassword.toString(), confirmPassword.toString())) {
            authListener?.inputValidation(2,"Password Not Matching")
            return
        }


        Coroutines.main {
            try {
                //val userToken = repository.getUserDetails().token.toString()
                //prefs.saveDetails(userToken.toString())

                val authResponse = repository.resetPassword(newPassword.toString())

                authResponse.data?.let {
                    authListener?.onSuccess()

                    repository.updatePassword(newPassword.toString())

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