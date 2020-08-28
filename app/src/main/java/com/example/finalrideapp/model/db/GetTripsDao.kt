package com.example.finalrideapp.model.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.finalrideapp.model.db.entities.GetTrips

@Dao
interface GetTripsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGetTrips(getTrips: GetTrips) : Long

    @Query("SELECT * FROM GetTrips")
    fun gettrips() : GetTrips
}