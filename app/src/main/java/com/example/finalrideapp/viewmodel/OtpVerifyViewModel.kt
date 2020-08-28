package com.example.finalrideapp.viewmodel

import android.content.Intent
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alimuzaffar.lib.pin.PinEntryEditText
import com.example.finalrideapp.model.repositories.NewUserRepository
import com.example.finalrideapp.util.ApiException
import com.example.finalrideapp.util.Coroutines
import com.example.finalrideapp.util.NoInternetException
import com.example.finalrideapp.view.auth.AuthListener
import com.example.finalrideapp.view.auth.Login
import kotlinx.android.synthetic.main.activity_otp_verify.*
import kotlinx.android.synthetic.main.activity_otp_verify.view.*

class OtpVerifyViewModel(private val repository: NewUserRepository): ViewModel() {

    var otp: CharSequence? = ""

    var counterStart: Long = 60000
    var timeFinish: Boolean
    lateinit var timer: CountDownTimer
    var pinEntered: Boolean = false
    var seconds = MutableLiveData<Long>()
    var resetOtp = MutableLiveData<Long>()

    var authListener: AuthListener? = null

    init {
        timeFinish = false
        startTimer(counterStart)
    }

    fun startTimer(time: Long) {
        Log.d("timeeeeeeee", time.toString())
        timer = object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d("millisUntilFinished", millisUntilFinished.toString()+ " " + counterStart.toString())
                seconds.value = millisUntilFinished / 1000
                onPinEntered()
            }

            override fun onFinish() {
                timeFinish = true
                seconds.value = 0
            }
        }
        timer.start()
    }

    fun onPinEntered(){
        if (otp.toString().length == 4 && timeFinish == false && pinEntered == false) {
            pinEntered = true
            Coroutines.main {
                try {
                    val otpVerificationID = repository.getOtpVerificationID()
                    val authResponse = repository.funOtpVerify(otpVerificationID.toString(), otp.toString())
                    authResponse.data?.let {
                        timer.cancel()
                        authListener?.onSuccess()
                        return@main
                    }
                    authListener?.onFailure(authResponse.message!!)
                } catch(e: ApiException){
                    authListener?.onFailure(e.message!!)
                }catch (e: NoInternetException){
                    authListener?.onFailure(e.message!!)
                }
                pinEntered = false
            }
        }
    }

    fun funResendOtpOnClick() {
        timer.cancel()
        Coroutines.main {
            try {
                val otpVerificationID = repository.getOtpVerificationID()
                val authResponse = repository.funOtpReset(otpVerificationID.toString())
                authResponse.data?.let {
                    var resendOtpVerificationID = authResponse.data.get("resendOtpVerificationID").toString()
                    resendOtpVerificationID = resendOtpVerificationID.substring(1, resendOtpVerificationID.length-1)
                    repository.saveOtpVerificationID(resendOtpVerificationID)
                    resetOtp.value = counterStart/1000
                    startTimer(counterStart)
                    //authListener?.onSuccess()
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

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }
}