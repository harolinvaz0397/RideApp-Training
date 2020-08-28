package com.example.finalrideapp.helpers

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import timber.log.Timber
import java.util.*

class GooglePlacesApiClient {
    private val FINE_LOCATION: String = android.Manifest.permission.ACCESS_FINE_LOCATION
    private val COURSE_LOCATION: String = android. Manifest.permission.ACCESS_COARSE_LOCATION
    private val LOCATION_PERMISSION_REQUEST_CODE = 1234
    private val TAG = "MainActivity"
    private val ERROR_DIALOG_REQUEST = 9001
    private var isPermissionsGranted: Boolean = false
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var currentLocation: String =" "
    var currentLocality: String = ""


        fun checkAndEnablePermission(context: Context, activity: Activity) {
        Log.d(TAG, "getLocationPermission: getting location permissions")
        val permissions = arrayOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)

        if (ContextCompat.checkSelfPermission(context, FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(context, COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                isPermissionsGranted = true
                Log.d("Permissions:" ,"Already given")

            } else {
                ActivityCompat.requestPermissions(activity, permissions, LOCATION_PERMISSION_REQUEST_CODE)
            }
        } else {
            ActivityCompat.requestPermissions(activity, permissions, LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

  fun getLocationInfo(context: Context){
        //var currentLocationInfo: List<Address>? = null
        Log.d("Message: "," getting location")

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val task = fusedLocationClient.lastLocation

      task.addOnCompleteListener {
          var location: Location? = it.result
          if (location != null){
              try{
                  val geoCoder = Geocoder(context, Locale.getDefault())
                  val currentLocationName = geoCoder.getFromLocation(location.latitude,location.longitude,1)
                  currentLocality = currentLocationName[0].locality
                  Log.d("Location Name:", currentLocality)
              }
              catch (e: Exception){
                  Log.d("Exception:", e.toString())
              }

          }


      }
    }




}