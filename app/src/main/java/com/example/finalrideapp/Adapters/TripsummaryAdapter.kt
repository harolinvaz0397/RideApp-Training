package com.example.finalrideapp.Adapters

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalrideapp.DirectionApiClient
import com.example.finalrideapp.R
import com.example.finalrideapp.view.home.TripSummaryListener
import com.google.android.gms.maps.model.LatLng
import com.mapbox.geojson.Point
import kotlinx.android.synthetic.main.mileston_cardview.view.*
import java.util.*
import kotlin.collections.ArrayList

class TripsummaryAdapter( val milestones: ArrayList<String>,
                          val milestoneDestination: java.util.ArrayList<String>?,
                          val milestoneSource: java.util.ArrayList<String>?
) :  RecyclerView.Adapter<TripsummaryAdapter.ViewHolder>() {

    var tripSummaryListener: TripSummaryListener? = null
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.mileston_cardview, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return milestones.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.milestoneTextView.text = milestones[position]
        holder.itemView.source.text = milestoneSource?.get(position)
        holder.itemView.destination.text = milestoneDestination?.get(position)
        val source = tripSummaryListener!!.milestoneSource[position]
        val destination = tripSummaryListener!!.milestoneDestination[position]
        val directionApi = DirectionApiClient()
        val distance = directionApi.getDirection(
            Point.fromLngLat(source[0], source[1]),
            Point.fromLngLat(destination[0], destination[1]),holder.itemView.distanceTextView
        )



        if(position == 0)
        {
            holder.itemView.line2.visibility = View.GONE
        }
        if(position == milestones.size-1)
        {
            holder.itemView.line3.visibility = View.GONE
        }
    }

}