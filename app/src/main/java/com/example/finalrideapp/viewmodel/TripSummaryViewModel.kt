package com.example.finalrideapp.viewmodel

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.finalrideapp.model.repositories.NewUserRepository
import com.example.finalrideapp.view.home.TripSummaryListener
import retrofit2.Call
import retrofit2.Callback

class TripSummaryViewModel(private val repository: NewUserRepository): ViewModel() {
    var name: String = ""
    var destination = arrayListOf<Double>(100.0, 200.0)
    var source = arrayListOf<Double>(300.0, 200.0)
    var startDate: String = "1/7/2020"
    var endDate: String = "2/8/2020"
    var startTime: String ="10:30 am"
    var milestones = ArrayList<ArrayList<Double>>()
    var invitedUsers = arrayListOf<String>()
    var time: String = ""
    var tripListener: TripSummaryListener? = null

    fun onCreateButtonClick(view: View) {
        // time?.let { getDate(it) }
        //tripListener?.onStarted()

        //val tripResponse = repository.createTrip(name, destination, source,startDate, endDate,startTime)
        tripListener?.onSuccess("success")

/*
        Coroutines.main {
            try {
                val tripResponse = repository.createTrip(tripListener?.name!!, tripListener?.destination!!, tripListener?.source!!, tripListener?.startDate!!, tripListener?.endDate!!, tripListener?.startTime!!, milestones, invitedUsers)
                Log.d("hello","started")
                tripResponse.data?.let {
                    Log.d("hello", "${it.get("message")}")
                    val s = it.get("message").toString()
                    tripListener?.onSuccess(s)

                    return@main
                }
                tripListener?.onFailure(tripResponse.message!!)
            } catch(e: ApiException){
                tripListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                tripListener?.onFailure(e.message!!)
            }
        }*/


    }






}