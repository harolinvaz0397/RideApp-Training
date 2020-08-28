package com.example.finalrideapp.model.network

import com.example.finalrideapp.model.network.responses.AuthResponse
import com.example.finalrideapp.model.preferences.PreferenceProvider
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun userLoginEmail(
        @Field("email") emailorphone: String,
        @Field("password") password: String
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @POST("login")
    suspend fun userLoginPhone(
        @Field("phone") emailorphone: String,
        @Field("password") password: String
    ) : Response<AuthResponse>


    @FormUrlEncoded
    @POST("register")
    suspend fun userRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("password") password: String
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @POST("reset-password")
    suspend fun resetPassword (
        @Header("Authorization") token: String,
        @Field("newPassword") newPassword: String
    ) : Response<AuthResponse>



/*
    @GET("quotes")
    suspend fun getQuotes() : Response<QuotesResponse> */

    companion object{
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : MyApi{
            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("https://rideapp-robosoft.herokuapp.com/api/auth/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }

}