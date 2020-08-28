package com.example.finalrideapp.model.repositories

import android.util.Log
import com.example.finalrideapp.model.db.AppDatabase
import com.example.finalrideapp.model.db.entities.User
import com.example.finalrideapp.model.network.MyApi
import com.example.finalrideapp.model.network.SafeApiRequest
import com.example.finalrideapp.model.network.responses.AuthResponse
import com.example.finalrideapp.model.preferences.PreferenceProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository (
    private val api: MyApi,
    private val db: AppDatabase,
    private val prefs: PreferenceProvider
) : SafeApiRequest() {

    suspend fun funUserRegister(
        name: String,
        email: String,
        phone: String,
        password: String
    ) : AuthResponse {
        return apiRequest{ api.userRegister(name, email, phone, password) }
    }

    suspend fun funUserLoginPhone(
        phone: String,
        password: String
    ) : AuthResponse {
        return apiRequest{ api.userLoginPhone(phone, password) }
    }

    suspend fun funUserLoginEmail(
        email: String,
        password: String
    ) : AuthResponse {
        return apiRequest{ api.userLoginEmail(email, password) }
    }

    /*
    fun saveTokenInPref(token: String) {
        Log.d("login token saved", token)
        prefs.saveDetails(token)
    }

    suspend fun resetPassword(
        token: String,
        newPassword: String
    ) : AuthResponse {
        return apiRequest{ api.resetPassword(token, newPassword)}
    }

     */


    suspend fun saveUser(user: User): Long = db.getUserDao().upsert(user)

    suspend fun saveToken(token: String, current: String): Int = db.getUserDao().updateToken(token, current)

    suspend fun getUserDetails() = db.getUserDao().getuserdetails()

    suspend fun updatePassword(newPassword: String): Int = db.getUserDao().updatePassword(newPassword)

    //fun updateImagePath(path: String): Int = db.getUserDao().updatePath(path)

    fun getUser() = db.getUserDao().getuser()

}