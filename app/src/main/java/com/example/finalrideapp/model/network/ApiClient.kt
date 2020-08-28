package com.example.finalrideapp.model.network

import android.content.Context
import com.example.finalrideapp.model.network.responses.*
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiClient {

    @FormUrlEncoded
    @POST("api/auth/register")
    suspend fun userRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("password") password: String
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @POST("api/auth/login")
    suspend fun userLoginEmail(
        @Field("email") emailorphone: String,
        @Field("password") password: String
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @POST("api/auth/login")
    suspend fun userLoginPhone(
        @Field("phone") emailorphone: String,
        @Field("password") password: String
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @POST("api/auth/forgot-password")
    suspend fun forgotPasswordEmail(
        @Field("email") emailorphone: String
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @POST("api/auth/forgot-password")
    suspend fun forgotPasswordPhone(
        @Field("phone") emailorphone: String
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @POST("api/auth/otp/verify")
    suspend fun otpVerify(
        @Field("otpVerificationID") otpVerificationID: String,
        @Field("otp") otp: String
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @POST("api/auth/otp/reset")
    suspend fun otpReset(
        @Field("otpVerificationID") otpVerificationID: String
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @POST("api/auth/reset-password")
    suspend fun resetPassword (
        @Field("newPassword") newPassword: String
    ) : Response<AuthResponse>

    @GET("api/trip?sortBy=<createdAt>&&orderBy=<dsc>")
    suspend fun getTrips () : Response<GetTripsResponse>

    @GET("api/dealer")
    suspend fun getDealers(
        @Query("city") cityName: String,
        @Query("sortby") ratings: String,
        @Query("orderby") order: String
    ) : Response<DealersResponse>

    @GET("api/dealer/{id}")
    suspend fun getDealerDetails(
        @Path("id") dealerID: String
    ) : Response<DealersResponse>

    @FormUrlEncoded
    @POST("api/service")
    suspend fun addService(
        @Field("phone") phone: String,
        @Field("slotDate") slotDate: String,
        @Field("slotTime") slotTime: String,
        @Field("vehicleNumber") vehicleNumber: String,
        @Field("vehicleType") vehicleType: String,
        @Field("serviceType") serviceType: String,
        @Field("comments") comments: String,
        @Field("dealer") dealer: String
    ) : Response<AuthResponse>

    @GET("api/service")
    fun getServices(@Query("sortby") slot: String,
                    @Query("orderby") order: String): Call<FetchServiceResponse>

    @GET("api/service/{id}")
    fun getServiceDetails(
        @Path("id") serviceID: String,
        @Query("populate") populate: Boolean
    ): Call<ServiceDetailsResponse>

    @GET("api/service")
    fun getUpcomingService(
        @Query("sortby") slot: String,
        @Query("orderby") order: String
    ) : Call<FetchServiceResponse>

    @GET("api/rider")
    fun getRiderDetails(): Call<RiderDetailsResponse>


    companion object{
        operator fun invoke(
            context: Context
        ) : ApiClient{
            //val context = context.applicationContext
            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(NetworkConnectionInterceptor(context))
                .addInterceptor(AuthInterceptor(context))
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("https://rideapp-robosoft.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiClient::class.java)

        }
    }
}