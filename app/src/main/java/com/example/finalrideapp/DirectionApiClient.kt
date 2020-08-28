package com.example.finalrideapp

import android.util.Log
import android.widget.TextView
import com.mapbox.api.directions.v5.DirectionsCriteria
import com.mapbox.api.directions.v5.MapboxDirections
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.geojson.Point
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


class DirectionApiClient{
    val MAPBOX_ACCESS_TOKEN = "pk.eyJ1IjoiYXNod2luZGV2YWRpZ2EiLCJhIjoiY2tjMjZjMXdyMjBxbzJwdGJyM2VoMnhxdCJ9.CnkFB8AG-kbzqoE0bB63-w"


    fun getDirection(
        origin: Point,
        destination: Point,
        distanceTextView: TextView
    ){
        val client = MapboxDirections.builder()
            .origin(origin)
            .destination(destination)
            .overview(DirectionsCriteria.OVERVIEW_FULL)
            .profile(DirectionsCriteria.PROFILE_DRIVING)
            .accessToken(MAPBOX_ACCESS_TOKEN)
            .build()


        client.enqueueCall(object : Callback<DirectionsResponse>{
            override fun onFailure(call: Call<DirectionsResponse>, t: Throwable) {
                Timber.d(t)
            }

            override fun onResponse(
                call: Call<DirectionsResponse>,
                response: Response<DirectionsResponse>
            ) {
                if (response.body() == null) {
                    Log.d("tag","No routes found, make sure you set the right user and access token.")
                    return
                } else {
                  //  Log.d("distance: ", response.body()!!.routes().toString())
                   var distance = response.body()!!.routes().get(0).distance()?.times(0.001)?.toInt()
                    var duration = response.body()!!.routes().get(0).duration()
                    val duration2 = "${duration?.div(3600)?.toInt()}h ${duration?.rem(3600)?.div(60)?.toInt()}min"
                    Log.d("duration", duration2)


                    distanceTextView.setText("${distance}km - $duration2")
                    return
                }
                  }

        })

    }

}