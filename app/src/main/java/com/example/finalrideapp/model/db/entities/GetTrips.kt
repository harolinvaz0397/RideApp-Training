package com.example.finalrideapp.model.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GetTrips (
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String
)