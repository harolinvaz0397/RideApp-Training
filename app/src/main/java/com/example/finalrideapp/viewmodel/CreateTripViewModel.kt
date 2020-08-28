package com.example.finalrideapp.viewmodel

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.finalrideapp.view.home.Interface


class CreateTripViewModel: ViewModel() {
    var destination: String? = null
    var source: String? = null
    var name: String? = null
    var startDate: String? = null
    var endDate: String? = null
    var startTime: String? = null
    var contact: String? = null
    var milestoneSource: String? = null
    var milestoneDestination: String? = null


    var listener: Interface? = null

    fun onDoneButtonClick(view: View) {
        listener?.onStarted()
        if (destination.isNullOrEmpty() || source.isNullOrEmpty() || name.isNullOrEmpty() || startDate.isNullOrEmpty() || endDate.isNullOrEmpty() || startTime.isNullOrEmpty()) {
            listener?.Failure()
            return

        }else

            listener?.success(name!!,destination!!,source!!, startDate!!, endDate!!, startTime!!)
    }
    fun onMilestonButtonClick(view: View){
        if (destination.isNullOrEmpty() || source.isNullOrEmpty() || name.isNullOrEmpty() || startDate.isNullOrEmpty() || endDate.isNullOrEmpty() || startTime.isNullOrEmpty()) {
            listener?.Failure()
            return
        } else
            listener?.addMilestone(source!!)
    }

}

