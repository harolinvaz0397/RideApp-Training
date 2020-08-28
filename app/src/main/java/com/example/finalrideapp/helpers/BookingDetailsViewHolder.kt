package com.example.finalrideapp.helpers

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalrideapp.R

class BookingDetailsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val itemName: TextView
    val itemValue: TextView

    init {
        itemName = itemView.findViewById(R.id.tvItem) as TextView
        itemValue = itemView.findViewById(R.id.tvItemValue) as TextView

    }
}