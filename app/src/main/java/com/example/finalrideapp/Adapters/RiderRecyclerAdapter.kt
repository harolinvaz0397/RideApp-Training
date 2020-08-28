package com.example.finalrideapp.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalrideapp.R


class RiderRecyclerAdapter(val contacts: ArrayList<String>) : RecyclerView.Adapter<RiderRecyclerAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.riders_cardview, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rider1 = holder.itemView.findViewById<ImageView>(R.id.rider2)
        rider1.setOnClickListener {
            contacts.removeAt(position)
            notifyDataSetChanged()
          //  Log.d("riders", "$contacts")
        }


    }
}