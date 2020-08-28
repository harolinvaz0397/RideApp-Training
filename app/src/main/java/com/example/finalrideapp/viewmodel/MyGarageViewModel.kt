package com.example.finalrideapp.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalrideapp.Adapters.ServiceRecordsAdapter
import com.example.finalrideapp.R
import com.example.finalrideapp.helpers.checkIfOlder
import com.example.finalrideapp.helpers.getTimeAndDate
import com.example.finalrideapp.helpers.replaceAndCapitalize
import com.example.finalrideapp.model.DataModels.ServiceRecordModel
import com.example.finalrideapp.model.network.RetrofitClient
import com.example.finalrideapp.model.network.responses.FetchServiceResponse
import com.example.finalrideapp.model.preferences.PreferenceProvider
import com.example.rideapp.helpers.DateTimePicker
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.lang.reflect.Array.get
import java.sql.Date
import java.util.*

class MyGarageViewModel: ViewModel() {
    val daysDue = MutableLiveData<String>()
    val nextServiceCaption = MutableLiveData<String>()
    val date = DateTimePicker()

    fun getNumberOfDaysDue(context: Context) {
        val calendarInstance = Calendar.getInstance()
        val currentDay: Int = calendarInstance.get(Calendar.DAY_OF_MONTH)
        Log.d("Message","Current Day:" + currentDay.toString())
        val preferenceProvider = PreferenceProvider(context)
        val previousDay: Int = preferenceProvider.getLastDay()
        if (previousDay != currentDay) {
            //Toast.makeText(context, "Run retrofit", Toast.LENGTH_LONG).show()
           RetrofitClient.invoke(context).getUpcomingService("slot","asc").enqueue( object : Callback<FetchServiceResponse> {
                override fun onFailure(call: Call<FetchServiceResponse>, t: Throwable) {
                    Timber.d(t.message)
                    //Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<FetchServiceResponse>, response: Response<FetchServiceResponse>) {
                    Timber.d(" fetched")
                    if (response.code() == 200) {
                        Timber.d("Reocrds fetched")
                        var servicesResponse = response.body()
                        var services = servicesResponse?.data
                        var servicesCount = services?.size()
                        if (servicesCount ==  0) {
                            daysDue.value = context.getString(R.string.no_upcoming_services)
                            //nextServiceCaption.value = " "
                        }
                        else {
                            //get only first dervicce in the list
                            for (i in 0 until servicesCount!!)  {
                                var nextService = services?.get(i) as JsonObject
                                val isOlder: Boolean = nextService?.get("slot").toString().replaceAndCapitalize().checkIfOlder()
                                if (!isOlder) {
                                    Log.d("Message:", "isOlder")
                                    val nextServiceDate = nextService.get("slot").toString().replaceAndCapitalize()//.replaceAndCapitalize().getTimeAndDate()
                                    daysDue.value = date.findDaysBetweenDates(nextServiceDate) +" "+ context.getString(R.string.days) + context.getString(R.string.nextServiceDue)
                                    //nextServiceCaption.value = context.getString(R.string.nextServiceDue)
                                    // Toast.makeText(context, date.findDaysBetweenDates(nextServiceDate), Toast.LENGTH_LONG).show()
                                    preferenceProvider.setLastDay(currentDay)
                                    preferenceProvider.setNoOfDaysDue(date.findDaysBetweenDates(nextServiceDate))
                                    break
                                }

                            }

                        }

                    }
                    else {
                        Timber.d("Something went wrong "+response.code())
                        try {
                            var errorResponse = JSONObject(response.errorBody()?.string())
                            Timber.d(errorResponse.getString("name") + errorResponse.getString("message"))

                        }catch(ex: Exception) {
                            Timber.d(ex.message.toString())

                        }
                    }
                }

            })
        }
        else  {
            Log.d("Message", "Getting number of days due")
            daysDue.value = preferenceProvider.getNoOfDaysDue() + " "+ context.getString(R.string.days) + context.getString(R.string.nextServiceDue)
        }



    }

}