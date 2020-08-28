package com.example.finalrideapp.model.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USER_ID = 0

@Entity
data class User (
    var starTokenTime: String? = null,
    var token: String? = null,
    var name: String? = null,
    var email:String? = null,
    var phone: String? = null,
    var pass: String? = null,
    var imagePath: String? = null
) {
    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID
}