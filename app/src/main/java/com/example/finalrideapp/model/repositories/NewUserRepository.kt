package com.example.finalrideapp.model.repositories

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.finalrideapp.model.db.AppDatabase
import com.example.finalrideapp.model.db.entities.GetTrips
import com.example.finalrideapp.model.db.entities.User
import com.example.finalrideapp.model.network.ApiClient
import com.example.finalrideapp.model.network.ApiRequest
import com.example.finalrideapp.model.network.MyApi
import com.example.finalrideapp.model.network.SafeApiRequest
import com.example.finalrideapp.model.network.responses.AuthResponse
import com.example.finalrideapp.model.network.responses.DealersResponse
import com.example.finalrideapp.model.network.responses.GetTripsResponse
import com.example.finalrideapp.model.preferences.PreferenceProvider
import com.example.finalrideapp.viewmodel.ResetPasswordViewModel
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewUserRepository(
    private val api: ApiClient,
    private val db: AppDatabase,
    private val prefs: PreferenceProvider
) : ApiRequest() {

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

    suspend fun funForgotPasswordPhone(
        phone: String
    ) : AuthResponse {
        return apiRequest{ api.forgotPasswordPhone(phone) }
    }

    suspend fun funForgotPasswordEmail(
        email: String
    ) : AuthResponse {
        return apiRequest{ api.forgotPasswordEmail(email) }
    }

    suspend fun funOtpVerify(
        otpVerificationID: String,
        otp: String
    ) : AuthResponse {
        return apiRequest{ api.otpVerify(otpVerificationID, otp) }
    }

    suspend fun funOtpReset(
        otpVerificationID: String
    ) : AuthResponse {
        return apiRequest{ api.otpReset(otpVerificationID) }
    }

    suspend fun resetPassword(
        newPassword: String
    ) : AuthResponse {
        return apiRequest{ api.resetPassword(newPassword)}
    }

    suspend fun getTrips() : GetTripsResponse {
        return apiRequest { api.getTrips() }
    }

    suspend fun funGetDealers(cityName: String): DealersResponse {
        return apiRequest { api.getDealers(cityName, "ratings", "desc") }
    }

    suspend fun funGetDealerDetails(dealerID: String): DealersResponse {
        return apiRequest { api.getDealerDetails(dealerID) }
    }

    suspend fun funAddService(
        phone: String,
        slotDate: String,
        slotTime: String,
        vehicleNumber: String,
        vehicleType: String,
        serviceType: String,
        comments: String,
        dealer: String
    ) : AuthResponse {
        return apiRequest{ api.addService(phone, slotDate, slotTime, vehicleNumber, vehicleType, serviceType, comments, dealer) }
    }



    suspend fun getUserDetails() = db.getUserDao().getuserdetails()

    suspend fun updatePassword(newPassword: String): Int = db.getUserDao().updatePassword(newPassword)

    //suspend fun saveGetTrips(trips: GetTrips) = db.getTripsDao().saveGetTrips(trips)

    fun saveOtpVerificationID(otpVerificationID: String) {
        prefs.saveOtpVerificationID(otpVerificationID)
    }
    fun saveDetails(time: String, accessToken: String, refreshToken: String, phone: String) {
        prefs.saveDetails(time, accessToken, refreshToken, phone)
    }
    fun getOtpVerificationID(): String? {
        return prefs.getOtpVerificationID()
    }

    fun getTime(): String? {
        return prefs.getTime()
    }
    fun getAccessToken(): String? {
        return prefs.getAccessToken()
    }
    fun getRefreshToken(): String? {
        return prefs.getRefreshToken()
    }

}

/*
    fun updateImagePath(path: String): Int = db.getUserDao().updatePath(path)
    fun getUser() = db.getUserDao().getuser()
    suspend fun saveUser(user: User): Long = db.getUserDao().upsert(user)
    suspend fun saveToken(token: String, current: String): Int = db.getUserDao().updateToken(token, current)
 */



/*
LiveData<String> {
        val resetPasswordResponse = MutableLiveData<String>()
        api.getApiService().resetPassword(newPassword)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    resetPasswordResponse.value = t.message
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        resetPasswordResponse.value = response.body()?.toString()
                    } else {
                        resetPasswordResponse.value = response.errorBody()?.toString()
                    }
                }

            })

        return resetPasswordResponse
    }
 */