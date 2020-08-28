package com.example.finalrideapp.model.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface RetrofitClient {
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
   /* private const val BASE_URL = "https://rideapp-robosoft.herokuapp.com/api/auth/"


    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .method(original.method(), original.body())

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()*/

   /* val headerInterceptor: Interceptor = Interceptor { chain ->
        var request = chain.request()
        request = request.newBuilder().addHeader("Authorization", Tokens.accessToken).build()
        chain.proceed(request)
    }*/


    //temporary retrofit instance for fakeAPI
    //val okHttpClient1 = OkHttpClient.Builder().addInterceptor(headerInterceptor).build()
    /*val apiInstance: ApiClient by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://rideapp-robosoft.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(headerInterceptor).build())
            .build()
        retrofit.create(ApiClient::class.java)
    }*/


}
