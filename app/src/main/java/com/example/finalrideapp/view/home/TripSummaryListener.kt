package com.example.finalrideapp.view.home

interface TripSummaryListener {

   fun onStarted()
   fun onSuccess(s: String)
   fun onFailure(s: String)
    val name: String
   val source: ArrayList<Double>
   val destination: ArrayList<Double>
   val startDate: String
   val endDate: String
   val startTime: String
   val milestoneSource : ArrayList<ArrayList<Double>>
   val milestoneDestination : ArrayList<ArrayList<Double>>
   val invitedUsers: ArrayList<String>?
}