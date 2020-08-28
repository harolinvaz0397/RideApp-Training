package com.example.finalrideapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalrideapp.R
import com.example.finalrideapp.helpers.checkIfOlder
import com.example.finalrideapp.helpers.getTimeAndDate
import com.example.finalrideapp.helpers.replaceAndCapitalize
import com.example.finalrideapp.model.network.RetrofitClient
import com.example.finalrideapp.model.network.responses.ServiceDetailsResponse
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.service_records_items.view.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceDetailsViewModel: ViewModel() {
    val riderContactNumber = MutableLiveData<String>()
    val vehicleNumber = MutableLiveData<String>()
    val serviceType = MutableLiveData<String>()
    val serviceTime = MutableLiveData<String>()
    val serviceDate= MutableLiveData<String>()
    val serviceMonthAndYear = MutableLiveData<String>()
    val serviceRating = MutableLiveData<Float>()
    val serviceDealerName = MutableLiveData<String>()
    val serviceDealerCity = MutableLiveData<String>()
    val serviceComments = MutableLiveData<String>()
    val serviceId = MutableLiveData<String>()
    val isOlder = MutableLiveData<Boolean>()

    fun getServiceDetails(context: Context, id: String) {
        RetrofitClient.invoke(context).getServiceDetails(id, true).enqueue( object : Callback<ServiceDetailsResponse> {
            override fun onFailure(call: Call<ServiceDetailsResponse>, t: Throwable) {
                Log.d("Message:", t.message)
            }
            override fun onResponse(
                call: Call<ServiceDetailsResponse>,
                response: Response<ServiceDetailsResponse>
            ) {
                Log.d("Message:","onResponse")
                if (response.code() ==  200) {
                    val service = response.body()?.data
                    if (service != null) {
                        Log.d("Message", service.get("_id").toString().replaceAndCapitalize())
                        serviceId.value = id
                        riderContactNumber.value = service.get("phone").toString().replaceAndCapitalize()
                        vehicleNumber.value = service.get("vehicleNumber").toString().replaceAndCapitalize()
                        serviceType.value = service.get("serviceType").toString().replaceAndCapitalize()
                        serviceComments.value = service.get("comments").toString().replaceAndCapitalize()
                        isOlder.value = service.get("slot").toString().replaceAndCapitalize().checkIfOlder()
                        val (date, month, year, time) = service.get("slot").toString().replaceAndCapitalize().getTimeAndDate()
                        serviceTime.value = time //service.get("serviceTime").toString().replaceAndCapitalize()
                        serviceDate.value = date
                        serviceMonthAndYear.value = month + "\n" + year
                        val serviceDealer: JsonObject = service.get("dealer") as JsonObject
                        serviceDealerName.value = serviceDealer.get("name").toString().replaceAndCapitalize()
                        serviceDealerCity.value = serviceDealer.get("city").toString().replaceAndCapitalize()//service.get("vehicleNumber").toString().replaceAndCapitalize()
                    }
                }
                else {
                    Log.d("Message:","Something went wrong "+response.code())
                    try {
                        var errorResponse = JSONObject(response.errorBody()?.string())
                        Log.d("Message:", errorResponse.getString("name") + errorResponse.getString("message"))

                    }catch(ex: Exception) {
                        Log.d("Message:", ex.message.toString())

                    }
                }


            }

        })

    }





}