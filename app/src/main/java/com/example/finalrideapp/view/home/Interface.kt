package com.example.finalrideapp.view.home

import android.widget.EditText
import android.widget.TextView

interface Interface {
        var placename: String

        fun placeIntent(requestCode: Int, locationEditText: EditText)
        fun success(
            name: String,
            destination: String,
            source: String,
            startDate: String,
            endDate: String,
            startTime: String
        )
        fun onStarted()
        fun Failure()
        fun addMilestone(source: String)
        fun CurrentLocation(currentlocation: TextView)
    var milestonesource: ArrayList<List<Double>>
    var milestoneDestination: ArrayList<List<Double>>
    var milestonesource_1: ArrayList<String>
    var milestoneDestination_1: ArrayList<String>
    }