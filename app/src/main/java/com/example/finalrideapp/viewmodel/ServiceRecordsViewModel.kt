package com.example.finalrideapp.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalrideapp.Adapters.ServiceRecordsAdapter
import com.example.finalrideapp.helpers.checkIfOlder
import com.example.finalrideapp.helpers.getTimeAndDate
import com.example.finalrideapp.helpers.replaceAndCapitalize
import com.example.finalrideapp.model.DataModels.ServiceRecordModel
import com.example.finalrideapp.model.network.RetrofitClient
import com.example.finalrideapp.model.network.responses.FetchServiceResponse
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_service_records.view.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class ServiceRecordsViewModel: ViewModel() {
    val serviceType = MutableLiveData<String>()
    val vehicleType = MutableLiveData<String>()
    val dataList = ArrayList<ServiceRecordModel>()

    //for spinner
    val clicksListener1 = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (parent != null) {
                if ( position == 0) {
                    vehicleType.value = ""
                    //spinnerTitle.value = ""
                }
                else {
                    vehicleType.value = parent.getItemAtPosition(position) as String
                    //spinnerTitle.value = "Service Type"

                }
            }
        }
    }

    val clicksListener2 = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (parent != null) {
                if ( position == 0) {
                    serviceType.value = ""
                    //spinnerTitle.value = ""
                }
                else {
                    serviceType.value = parent.getItemAtPosition(position) as String
                    //spinnerTitle.value = "Service Type"
                    //initRecyclerView(view?.context!!, view.findViewById(), parent.getItemAtPosition(position) as String)

                }
            }
        }
    }

    fun initRecyclerView(context: Context, rvServiceRecords: RecyclerView) {
        RetrofitClient.invoke(context).getServices("slot","desc").enqueue(object : Callback<FetchServiceResponse> {
            override fun onFailure(call: Call<FetchServiceResponse>, t: Throwable) {
               //Timber.d(t)
                Log.d("Message:","helloooo------------")
                Log.d("Message:",t.message)
            }

            override fun onResponse(
                call: Call<FetchServiceResponse>,
                response: Response<FetchServiceResponse>
            ) {
                if (response.code() == 200) {
                    Log.d("Message:","Reocrds fetched")
                    var servicesResponse = response.body()
                    var services = servicesResponse?.data
                    var servicesCount = services?.size()
                    for (i in 0 until servicesCount!!) {
                        var service = services?.get(i) as JsonObject
                        val isOlder: Boolean = service.get("slot").toString().replaceAndCapitalize().checkIfOlder()
                        val (date, month, year, time) = service.get("slot").toString().replaceAndCapitalize().getTimeAndDate()
                        Log.d("Message","Is older:"+isOlder.toString())
                        dataList.add(ServiceRecordModel(service.get("_id").toString().replace("\"", ""),
                            date,
                            month,
                            year,
                            time,   // service.get("slot").toString().replace("\"", ""),
                            service.get("vehicleType").toString().replace("\"", ""),
                            service.get("serviceType").toString().replace("\"", ""),
                            isOlder,
                            4.0f))
                        //dealer.get("ratings").asFloat))
                    }
                    rvServiceRecords.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    var adapter = ServiceRecordsAdapter()
                    adapter.serviceRecordsList = dataList
                    rvServiceRecords.adapter = adapter
                   // adapter.filter.filter(searchConstraint)
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
   /* fun generateData(): List<ServiceRecordModel> {
        val dataList = ArrayList<ServiceRecordModel>()
       /* dataList.add(ServiceRecordModel("15", "Nov 2017","General service", 3.5f))
        dataList.add(ServiceRecordModel("16", "Dec 2016", "Breakdown", 4.0f))
        dataList.add(ServiceRecordModel("17", "May 2013", "Free service", 0.0f))
        dataList.add(ServiceRecordModel("18", "Aug 2015", "General service",2.0f))*/
        return  dataList
    }*/
}