                package com.example.finalrideapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalrideapp.Adapters.ViewTripRecyclerAdapter

import com.example.finalrideapp.R

/**
 * A simple [Fragment] subclass.
 */
class ViewTripsFragment : Fragment() {

    var strTripName = arrayOf<String>()
    var strTripDate = arrayOf<String>()
    var strTripStatus = arrayOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_view_trips,container, false)
        //return inflater.inflate(R.layout.fragment_view_trips, container, false)
        strTripName = resources.getStringArray(R.array.trip_names)
        strTripDate = resources.getStringArray(R.array.trip_date)
        strTripStatus = resources.getStringArray(R.array.trip_status)
        val recyclerViewTripsView = view!!.findViewById(R.id.recyclerTripView) as RecyclerView
        recyclerViewTripsView.layoutManager = LinearLayoutManager(activity!!.applicationContext, RecyclerView.VERTICAL, false)
        recyclerViewTripsView.adapter = ViewTripRecyclerAdapter(strTripName, strTripDate, strTripStatus)
        return view
    }

}
