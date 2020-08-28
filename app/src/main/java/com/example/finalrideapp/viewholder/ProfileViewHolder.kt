package com.example.finalrideapp.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalrideapp.R
import com.example.finalrideapp.model.DataModels.ProfileActivities
import com.example.finalrideapp.model.db.entities.GetTrips
import com.google.gson.JsonArray

class ProfileViewHolder(inflater: LayoutInflater, parent: ViewGroup):
    RecyclerView.ViewHolder(inflater.inflate(R.layout.profile_activities, parent, false)){

    private var tvProfileId: TextView? = null
    private var tvProfileName: TextView? = null
    private var tvProfileDate: TextView? = null
    private var tvProfileYear: TextView? = null


    init {
        tvProfileId = itemView.findViewById(R.id.tvProfileId)
        tvProfileName = itemView.findViewById(R.id.tvProfileName)
        tvProfileDate = itemView.findViewById(R.id.tvProfileDate)
        tvProfileYear = itemView.findViewById(R.id.tvProfileYear)
    }

    fun bind(activity: ProfileActivities) {
        tvProfileId?.text = activity._id
        tvProfileName?.text = activity.name
        tvProfileDate?.text = activity.date
        tvProfileYear?.text = activity.year
    }

}