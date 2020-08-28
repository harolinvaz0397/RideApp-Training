package com.example.finalrideapp.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.finalrideapp.R
import com.example.finalrideapp.view.home.BookingServiceDetails
import com.example.finalrideapp.view.home.JoinTripActivity

class ViewTripRecyclerAdapter( var strTripName: Array<String>, var strTripDate: Array<String>, var strTripStatus: Array<String> ) :
    RecyclerView.Adapter<ViewTripRecyclerAdapter.TripViewHolder>() {
    lateinit var context: Context
    class TripViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textViewTripName = itemView.findViewById(R.id.tvTripName) as TextView
        var textViewTripDate = itemView.findViewById(R.id.tvTripDate) as TextView
        var textViewTripStatus = itemView.findViewById(R.id.tvTripStatus) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trips_cardview, parent, false)
        return TripViewHolder(view)
    }

    override fun getItemCount(): Int {
        return strTripName.size
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        holder.textViewTripName.text = strTripName[position]
        holder.textViewTripDate.text = strTripDate[position]
        holder.textViewTripStatus.text = strTripStatus[position]

        holder.itemView.setOnClickListener {
            //Toast.makeText(context, "Hello", Toast.LENGTH_LONG).show()
            val intentJoinTrip = Intent(context, JoinTripActivity::class.java)
            context.startActivity(intentJoinTrip)
        }


    }
}