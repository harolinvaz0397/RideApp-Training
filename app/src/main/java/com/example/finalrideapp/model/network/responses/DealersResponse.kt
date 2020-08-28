package com.example.finalrideapp.model.network.responses

import com.google.gson.JsonObject

/*class DealersResponse(
    var _id: String,
    var name: String,
    var address: String,
    var city: String,
    var state: String,
    var phone: String,
    var rating: Float
) {
}*/

data class DealersResponse(
    val data: JsonObject?,
    val message: String?
)