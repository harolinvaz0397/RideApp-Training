package com.example.finalrideapp.model.DataModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.finalrideapp.MyAppilcation
import com.example.finalrideapp.helpers.ResourceProvider
import com.example.finalrideapp.model.preferences.PreferenceProvider

object BookServiceObject {
    //val myApplication = MyAppilcation()
   // val appContext: Context = myApplication.appContext

    //var userObject: PreferenceProvider
    //val resourceProvider = ResourceProvider()
    //var userObject = PreferenceProvider(resourceProvider.appContext)
    private val _mobileNumber = MutableLiveData<String>()
    val mobileNumber: LiveData<String>
        get() {
            return _mobileNumber
        }

    private val _vehicleNumber = MutableLiveData<String>()
    val vehicleNumber: LiveData<String>
        get() {
            return _vehicleNumber
        }

    private val _vehicleType = MutableLiveData<String>()
    val vehicleType: LiveData<String>
        get() {
            return _vehicleType
        }

    private val _numberOfAttempts = MutableLiveData<Int>()
    val numberOfAttempts: LiveData<Int>
        get() {
            return _numberOfAttempts
        }

    private val _comments = MutableLiveData<String>()
    val comments: LiveData<String>
        get() {
            return _comments
        }

    private val _serviceType = MutableLiveData<String>()
    val serviceType: LiveData<String>
        get() {
            return _serviceType
        }

    init {
        //_mobileNumber.value = "9901906078"//userObject.getPhoneNumber()
        _numberOfAttempts.value = 3
        _comments.value = ""
        _serviceType.value = ""
    }

    fun findAttemptsRemaining() {
        if (_numberOfAttempts.value!! > 0)
            _numberOfAttempts.value = _numberOfAttempts.value?.minus(1)
        else
            _numberOfAttempts.value = 0

    }

    private val _selectedDealerName = MutableLiveData<String>()
    val selectedDealerName: LiveData<String>
        get() {
            return _selectedDealerName
        }

    private val _selectedDealerCity= MutableLiveData<String>()
    val selectedDealerCity: LiveData<String>
        get() {
            return _selectedDealerCity
        }

    private val _selectedDealerId= MutableLiveData<String>()
    val selectedDealerId: LiveData<String>
        get() {
            return _selectedDealerId
        }


    fun updateBookingDetails(BookingInformation: ArrayList<MutableLiveData<String>>) {
        _mobileNumber.value = BookingInformation[0].value
        _vehicleNumber.value = BookingInformation[1].value
        _vehicleType.value = BookingInformation[2].value
        _serviceType.value = BookingInformation[3].value
        _comments.value = BookingInformation[4].value

    }

    fun updateChosenDealerDetails(dealerName: String, dealerCity: String, dealerId: String) {
        _selectedDealerName.value = dealerName
        _selectedDealerCity.value = dealerCity
        _selectedDealerId.value = dealerId

    }



}