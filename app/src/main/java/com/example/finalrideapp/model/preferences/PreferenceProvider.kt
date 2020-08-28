package com.example.finalrideapp.model.preferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.finalrideapp.model.DataModels.BookServiceObject

private const val Otp_Verification_ID = "otp_verification_id"
//private const val KEY_SAVED_DETAILS = "key_saved_details"
private const val PATH_SAVED = "path_saved"
private const val UPDATE_TIME = "update_time"
private const val ACCESS_TOKEN = "access_token"
private const val REFRESH_TOKEN = "refresh_token"
private const val PHONE_NUMBER= "userPhoneNumber"
private const val ATTEMPTS_REMAINING = "no_of_attempts_remaining"
private const val LAST_DAY = "last_day"
private const val DAYS_DUE = "days_due"
private const val RIDER_NAME = "rider_name"
private const val NEW_USER = "new_user"

class PreferenceProvider(
    context: Context
) {

    private val appContext = context.applicationContext

    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)


    fun saveOtpVerificationID(otpVerificationID: String) {
        preference.edit().putString(
            Otp_Verification_ID,
            otpVerificationID
        ).apply()
    }

    fun getOtpVerificationID(): String? {
        return preference.getString(Otp_Verification_ID, null)
    }

    fun saveDetails(time: String, accessToken: String, refreshToken: String, phone: String) {
        val editor:SharedPreferences.Editor =  preference.edit()
        editor.putString(UPDATE_TIME, time)
        editor.putString(ACCESS_TOKEN, accessToken)
        editor.putString(REFRESH_TOKEN, refreshToken)
        editor.putString(PHONE_NUMBER, phone)
        editor.apply()
        editor.commit()
    }

    fun getTime(): String? {
        return preference.getString(UPDATE_TIME, null)
    }

    fun getAccessToken(): String? {
        return preference.getString(ACCESS_TOKEN, null)
    }

    fun getRefreshToken(): String? {
        return preference.getString(REFRESH_TOKEN, null)
    }

    fun savePath(path: String) {
        preference.edit().putString(
            PATH_SAVED,
            path
        ).apply()
    }

    fun getPath(): String? {
        return preference.getString(PATH_SAVED, null)
    }

    fun saveLoginDetails() {

    }



    fun clear() {
        val editor:SharedPreferences.Editor =  preference.edit()
        editor.clear()
        editor.apply()

    }

    fun saveNumberOfAttemps(attemptsRemaining: Int) {
        val editor: SharedPreferences.Editor = preference.edit()
        editor.putInt(ATTEMPTS_REMAINING, attemptsRemaining)
        editor.apply()
    }

    fun getNumberOfAttempts(): Int {
        return preference.getInt(ATTEMPTS_REMAINING, 3)
    }

    fun setLastDay(lastDay: Int) {
        val editor: SharedPreferences.Editor = preference.edit()
        editor.putInt(LAST_DAY, lastDay)
        editor.apply()
    }

    fun getLastDay(): Int {
        return preference.getInt(LAST_DAY, 0)
    }

    fun setNoOfDaysDue(daysDue: String) {
        val editor: SharedPreferences.Editor = preference.edit()
        editor.putString(DAYS_DUE, daysDue)
        editor.apply()
    }

    fun getNoOfDaysDue(): String? {
        return preference.getString(DAYS_DUE, " ")
    }

    fun setPhoneNumber(phone: String) {
        val editor: SharedPreferences.Editor = preference.edit()
        editor.putString(PHONE_NUMBER, phone)
        editor.apply()
    }

    fun getPhoneNumber(): String {
        return preference.getString(PHONE_NUMBER,null).toString()
    }

    fun setRiderName(name: String) {
        val editor: SharedPreferences.Editor = preference.edit()
        editor.putString(RIDER_NAME, name)
        Log.d("Message","In setRiderName"+name)
        editor.apply()
    }

    fun getRiderName(): String {
        Log.d("Message","In getRiderName"+preference.getString(RIDER_NAME,null).toString())
        return preference.getString(RIDER_NAME,null).toString()
    }

    fun setIsNewUser(value: Boolean) {
        val editor: SharedPreferences.Editor = preference.edit()
        editor.putBoolean(NEW_USER, value)
        //Log.d("Message","In setRiderName"+name)
        editor.apply()
    }

    fun getIsNewUser(): Boolean {
       // Log.d("Message","In getRiderName"+preference.getString(RIDER_NAME,null).toString())
        return preference.getBoolean(NEW_USER,false)
    }

}


