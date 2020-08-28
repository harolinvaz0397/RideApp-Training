package com.example.finalrideapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalrideapp.model.DataModels.ProfileActivities
import com.example.finalrideapp.model.db.entities.GetTrips
import com.example.finalrideapp.model.network.responses.GetTripsResponse
import com.example.finalrideapp.viewholder.ProfileViewHolder
import com.google.gson.JsonArray
import com.google.gson.JsonObject

class ProfileAdapter(val activityList: List<ProfileActivities>): RecyclerView.Adapter<ProfileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        return ProfileViewHolder(inflater, parent)

    }

    override fun getItemCount(): Int {

        return activityList.size

    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {

        holder.bind(activityList[position])

    }
}