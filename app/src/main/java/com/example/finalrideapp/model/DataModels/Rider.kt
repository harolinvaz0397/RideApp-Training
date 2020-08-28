package com.example.finalrideapp.model.DataModels

import android.content.Context
import android.util.Log
import com.example.finalrideapp.helpers.replaceAndCapitalize
import com.example.finalrideapp.model.preferences.PreferenceProvider
import com.google.gson.JsonObject

class Rider{

    fun saveRiderDetails(context: Context, riderDetailsResponse: JsonObject?) {
        val riderContactNumber: String = riderDetailsResponse?.get("phone").toString().replaceAndCapitalize()
        val riderUserName: String = riderDetailsResponse?.get("name").toString().replaceAndCapitalize()
        Log.d("Message:", riderContactNumber)
        val preferenceProvider = PreferenceProvider(context)
        preferenceProvider.setPhoneNumber(riderContactNumber)
        preferenceProvider.setRiderName(riderUserName)


    }
}