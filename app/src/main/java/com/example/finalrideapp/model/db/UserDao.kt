package com.example.finalrideapp.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.finalrideapp.model.db.entities.CURRENT_USER_ID
import com.example.finalrideapp.model.db.entities.User

@Dao
interface UserDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(user: User) : Long

    @Query("SELECT * FROM User where uid = $CURRENT_USER_ID")
    fun getuser() : LiveData<User>

    @Query("UPDATE User SET token= :token, starTokenTime= :current  WHERE uid = $CURRENT_USER_ID")
    suspend fun updateToken(token: String, current: String) : Int

    @Query("UPDATE User SET pass = :newPassword  WHERE uid = $CURRENT_USER_ID")
    suspend fun updatePassword(newPassword: String) : Int

    @Query("SELECT * FROM User where uid = $CURRENT_USER_ID")
    suspend fun getuserdetails() : User

    @Query("UPDATE User SET imagePath= :path WHERE uid = $CURRENT_USER_ID")
    fun updatePath(path: String) : Int

}