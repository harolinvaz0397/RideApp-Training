package com.example.finalrideapp.model.network.responses

import com.example.finalrideapp.model.db.entities.GetTrips
import com.google.gson.JsonArray
import com.google.gson.JsonObject

data class GetTripsResponse (
    val data: JsonObject?,
    val message: String
)

/*
data class GetTripsResponse (
    val count: Int,
    val trips: List<GetTrips>
)

 */