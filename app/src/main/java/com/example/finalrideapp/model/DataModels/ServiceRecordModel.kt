package com.example.finalrideapp.model.DataModels

data class ServiceRecordModel(val id: String,
                              val date: String,
                              val month: String,
                              val year: String,
                              val time: String,
                              val vehicleType: String,
                              val serviceType: String,
                              val isOlder : Boolean,
                              val serviceRatings: Float) {
}