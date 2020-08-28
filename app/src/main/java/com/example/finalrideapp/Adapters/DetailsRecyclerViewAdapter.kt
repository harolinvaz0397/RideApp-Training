package com.example.finalrideapp.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalrideapp.R
import com.example.finalrideapp.helpers.BookingDetailsViewHolder
import com.example.finalrideapp.model.DataModels.BookingDetails

class DetailsRecyclerViewAdapter(private var data: ArrayList<BookingDetails>): RecyclerView.Adapter<BookingDetailsViewHolder>() {
   lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingDetailsViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.booking_detail_item, parent, false)
        return BookingDetailsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: BookingDetailsViewHolder, position: Int) {
        val bookingItem = data[position]
        holder.itemValue.text = bookingItem.itemValue
        holder.itemName.text = bookingItem.itemName
    }
}