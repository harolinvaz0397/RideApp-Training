package com.example.finalrideapp.model.network

import android.util.Log
import com.example.finalrideapp.model.network.responses.AuthResponse
import com.example.finalrideapp.util.ApiException
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class ApiRequest {
    suspend fun<T: Any> apiRequest(call: suspend () -> Response<T>) : T{
        val response = call.invoke()
        Log.d("response body", response.body().toString())
        Log.d("response errorBody", response.errorBody().toString())
        if(response.isSuccessful) {
            return response.body()!!
        } else {
            val error = response.errorBody()?.string()

            val message = StringBuilder()
            error?.let{
                try{
                    message.append(JSONObject(it).getString("message"))
                }catch(e: JSONException){ }
                message.append("\n")
            }
            message.append("Error Code: ${response.code()}")
            throw ApiException(message.toString())
        }

    }
}