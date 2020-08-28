package com.example.finalrideapp.view.home

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalrideapp.Adapters.TripsummaryAdapter
import com.example.finalrideapp.R
import com.example.finalrideapp.databinding.ActivityTripSummaryBinding
import com.example.finalrideapp.model.db.AppDatabase
import com.example.finalrideapp.model.network.ApiClient
import com.example.finalrideapp.model.preferences.PreferenceProvider
import com.example.finalrideapp.model.repositories.NewUserRepository
import com.example.finalrideapp.viewmodel.TripSummaryViewModel
import com.example.finalrideapp.viewmodel.TripSummaryViewModelFactory
import com.google.android.gms.maps.model.LatLng
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute
import kotlinx.android.synthetic.main.activity_trip_summary.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TripSummaryActivity: AppCompatActivity(), TripSummaryListener {
    @SuppressLint("SetTextI18n")
    var navigationMapRoute: NavigationMapRoute? = null
    var currentRoute: DirectionsRoute? = null
    lateinit var map: MapboxMap

    override lateinit var name: String

    override lateinit var source: ArrayList<Double>
    override lateinit var destination: ArrayList<Double>
    override  lateinit var startDate: String
    override  lateinit var endDate: String
    override  lateinit var startTime: String
    override lateinit var milestoneSource: ArrayList<ArrayList<Double>>
    override lateinit var milestoneDestination: ArrayList<ArrayList<Double>>
    override var invitedUsers: ArrayList<String>? = null
    lateinit var origin: Point
    lateinit var destination2: Point
    override fun onCreate(savedInstanceState: Bundle?) {
        Mapbox.getInstance(this, getString(R.string.MAPBOX_ACCESS_TOKEN))
        super.onCreate(savedInstanceState)
        val api = ApiClient(this)
        val db = AppDatabase(this)
        val prefs = PreferenceProvider(this)
        val repository = NewUserRepository(api, db, prefs)
        val factory = TripSummaryViewModelFactory(repository)


        val binding: ActivityTripSummaryBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_trip_summary)
        val viewModel: TripSummaryViewModel =
            ViewModelProviders.of(this, factory).get(TripSummaryViewModel::class.java)
        binding.tripSummary = viewModel

        viewModel.tripListener = this
        val mapView: MapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState)




        val actionBar: ActionBar? = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#F99C56")))
        actionBar?.setDisplayHomeAsUpEnabled(true)


        val bundle: Bundle? = intent.extras
        source = bundle?.get("source") as ArrayList<Double>
        destination = bundle.get("destination") as ArrayList<Double>
        name = bundle.getString("name").toString()
        startDate = bundle.getString("startDate")!!
        endDate = bundle.getString("endDate")!!
        val sourceAddress = bundle.getString("source1")
        val destinationAddress = bundle.getString("destination1")
        val milestoneAddres1 = bundle.getStringArrayList("milestonesource1")
        val milestoneAddres2 = bundle.getStringArrayList("milestonedestination1")
        startTime = bundle.getString("startTime")!!
        invitedUsers = bundle.getStringArrayList("contact")
        milestoneSource = bundle.get("milestonesource") as ArrayList<ArrayList<Double>>
        milestoneDestination = bundle.get("milestonedestination") as ArrayList<ArrayList<Double>>
        val milestonesTitle: ArrayList<String> = bundle.getStringArrayList("milestones")!!


        tripNameTextview.text = name
        timeTextView.text = startTime
        sourceTextView.text = sourceAddress
        destinationTextView.text = destinationAddress


        val size = invitedUsers!!.size - 1
        for (i in 0..size) {
            val image = ImageView(this)
            image.setImageResource(R.drawable.rider1)
            image.setPadding(0, 0, 11, 0)
            inviteRidersLinearLayout.addView(image)
        }


        val dateformatter: SimpleDateFormat = SimpleDateFormat("dd MMM, yyyy")
        val date = dateFormatter(endDate)
        val startDate2 = dateFormatter(startDate).toString()
        val endDate2 = dateFormatter(endDate).toString()
        val date2 = dateFormatter(startDate)
        dateTextView.text = "${DateFormat.format("dd", date2)} - ${dateformatter.format(date)}"


        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val customAdapter =
            TripsummaryAdapter(milestonesTitle, milestoneAddres2, milestoneAddres1)
        recyclerView.adapter = customAdapter
        customAdapter.tripSummaryListener = this

        mapView.getMapAsync(object: OnMapReadyCallback {
            override fun onMapReady(mapboxMap: MapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, object : Style.OnStyleLoaded{
                    override fun onStyleLoaded(style: Style) {

                        // Set the origin location to the Alhambra landmark in Granada, Spain.
                        origin = Point.fromLngLat(source[0]!!, source[1]!!);

// Set the destination location to the Plaza del Triunfo in Granada, Spain.
                        destination2 = Point.fromLngLat(destination[0]!!, destination[1]!!)
                        mapboxMap.addMarker(
                            MarkerOptions().position(
                                com.mapbox.mapboxsdk.geometry.LatLng(
                                    origin.latitude(),
                                    origin.longitude()
                                )
                            ))
                        mapboxMap.addMarker(
                            MarkerOptions().position(
                                com.mapbox.mapboxsdk.geometry.LatLng(
                                    destination2.latitude(),
                                    destination2.longitude()
                                )
                            ))

// Get the directions route from the Mapbox Directions API
                        getRoute(mapboxMap, origin, destination2)

                    }

                })
            }

        })


    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.edit, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val intent: Intent =  Intent(this, CreateTripActivity::class.java);      //I suppose they are in same package
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        return true
    }
    fun dateFormatter(string: String): Date{
        val date = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(string)!!
        return  date
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_edit -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)

        }
    }

    override fun onStarted() {

        Toast.makeText(this,"started", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()

        startActivity(Intent(this,TripSuccessActivity::class.java))

    }

    override fun onFailure(s: String) {

        Toast.makeText(this,"$s", Toast.LENGTH_SHORT).show()
    }


    fun getRoute(map: MapboxMap, origin: Point, destination: Point){
        NavigationRoute.builder(this) //1
            .accessToken(Mapbox.getAccessToken()!!) //2
            .origin(origin) //3
            .destination(destination) //4
            .build() //5
            .getRoute(object : Callback<DirectionsResponse> { //6
                override fun onFailure(call: Call<DirectionsResponse>, t: Throwable) {
                    Log.d("MainActivity", t.localizedMessage)


                }

                override fun onResponse(call: Call<DirectionsResponse>,
                                        response: Response<DirectionsResponse>
                ) {
                    Log.d("respnse","${response.body()?.routes()}")
                    if (navigationMapRoute != null) {
                        navigationMapRoute?.updateRouteVisibilityTo(false)
                    } else {
                        navigationMapRoute = NavigationMapRoute(null, mapView, map)
                    }

                    currentRoute = response.body()?.routes()?.first()
                    if (currentRoute != null) {
                        navigationMapRoute?.addRoute(currentRoute)
                        map.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                com.mapbox.mapboxsdk.geometry.LatLng(
                                    destination.latitude(),
                                    destination.longitude()
                                ),8.0))

                    }


                }
            })


    }


}