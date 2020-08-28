package com.example.finalrideapp.model.network.responses

import com.google.gson.JsonObject

data class AuthResponse (
    val data: JsonObject?,
    val message: String?
)