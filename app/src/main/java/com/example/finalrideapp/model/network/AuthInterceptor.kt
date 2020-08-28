package com.example.finalrideapp.model.network

import android.content.Context
import android.util.Log
import com.example.finalrideapp.model.network.responses.AuthResponse
import com.example.finalrideapp.model.preferences.PreferenceProvider
import okhttp3.Interceptor
import retrofit2.Response

class AuthInterceptor(context: Context) : Interceptor {
    private val pref = PreferenceProvider(context)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response{

        val requestBuilder = chain.request().newBuilder()

        // If token has been saved, add it to the request
        /*
        if (token != null) {
            requestBuilder.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZWM0MDJjZWE3OWZjMzAwMTdhMjY3NWQiLCJpYXQiOjE1OTExNzMzODksImV4cCI6MTU5MTE5MTM4OX0._43kvGBw6sIR3QeRrcZcfASkWZv4voQxRcOIEBSYsE4\"")
        }

          eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZWY2ZDdkNTczNTBiODAwMTc1MGU3NTEiLCJ0eXBlIjoiYWNjZXNzIiwiaWF0IjoxNTkzMjM1NDEzLCJleHAiOjE1OTMzMjE4MTN9.wAzjIMHKynaLTcmqep-qc5T8D8f9n3hlkxWvjWYSpao
         */
        //val token = "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZWM0MDJjZWE3OWZjMzAwMTdhMjY3NWQiLCJpYXQiOjE1OTExOTY5NDUsImV4cCI6MTU5MTIxNDk0NX0.zEWQ2dUxIZ0pFdaqDS3TBfPbr6YxyVTal4IxCQYJ76c"

        pref.getAccessToken().toString().let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }
}