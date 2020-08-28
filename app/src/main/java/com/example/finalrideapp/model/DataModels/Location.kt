package com.example.finalrideapp.model.DataModels

import android.app.Activity
import android.content.Context
import android.location.Address
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.finalrideapp.helpers.GooglePlacesApiClient

class Location() {

    private val currentLocationInfo = MutableLiveData<List<Address>>()
    private val _currentLocationName = MutableLiveData<String>()
    val currentLocationName: LiveData<String>
    get() = _currentLocationName

    init {
        val googlePlacesApiClient = GooglePlacesApiClient()
      // currentLocationInfo.value =  googlePlacesApiClient.getLocationInfo()
        _currentLocationName.value = googlePlacesApiClient.currentLocality
    }

}