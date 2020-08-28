package com.example.finalrideapp.helpers

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import java.util.*


fun Context.getCurrentLocation(currentlocation: TextView) {

    val string: String = android.Manifest.permission.ACCESS_FINE_LOCATION
    var currentLocation: String? = null
    Places.initialize(applicationContext, "AIzaSyBhGQmm31ctKdfQPmeGnLpGM4z3cPRdmPY")    //api key

    val fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

    if (ActivityCompat.checkSelfPermission(
            this, android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        fusedLocationProviderClient.lastLocation.addOnCompleteListener {
            val location: Location = it.result!!
            if (location != null) {
                try {
                    val geocoder = Geocoder(this, Locale.getDefault())
                    val addresses: List<Address> = geocoder.getFromLocation(
                        location.latitude, location.longitude, 1
                    )
                    currentLocation = addresses.get(0).locality
                    currentlocation.text = currentLocation

                } catch (e: Exception) {
                    Toast.makeText(this, "$e", Toast.LENGTH_SHORT).show()
                }
            }
        }


    } else {
        ActivityCompat.requestPermissions(this as Activity, arrayOf(string), 100)

    }

}
