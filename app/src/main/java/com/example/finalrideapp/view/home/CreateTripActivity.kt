package com.example.finalrideapp.view.home

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.text.DecimalFormat
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalrideapp.Adapters.RecyclerViewAdapter
import com.example.finalrideapp.Adapters.RiderRecyclerAdapter
import com.example.finalrideapp.R
import com.example.finalrideapp.databinding.ActivityCreateTripBinding
import com.example.finalrideapp.helpers.getCurrentLocation
import com.example.finalrideapp.viewmodel.CreateTripViewModel
import com.example.rideapp.helpers.DateTimePicker
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions
import kotlinx.android.synthetic.main.activity_create_trip.*

import java.util.*
import kotlin.collections.ArrayList



class CreateTripActivity : AppCompatActivity(), Interface {

    lateinit var recyclerView: RecyclerView
    lateinit var editText2: EditText
    lateinit var placeEditText: EditText
    var string: String = android.Manifest.permission.ACCESS_FINE_LOCATION
    override var placename: String = ""
    var size = 0
    var models = ArrayList<String>()
    var contact = ArrayList<String>()
    var contacts = ArrayList<String>()
    lateinit var  recyclerViewAdapter: RiderRecyclerAdapter
    lateinit var customAdapter: RecyclerViewAdapter

    var sourceLatLng = ArrayList<Double?>()
    var destinationLatLng =  ArrayList<Double?>()
    override var milestonesource = ArrayList<List<Double>>()
    var destination: String = ""
    override var milestoneDestination = ArrayList<List<Double>>()
    var sourceAddress = String()
    var destinationAddress =  String()
    override var milestonesource_1 = ArrayList<String>()
    override var milestoneDestination_1 = ArrayList<String>()




    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityCreateTripBinding =  DataBindingUtil.setContentView(this, R.layout.activity_create_trip)
        val viewModel: CreateTripViewModel? = ViewModelProviders.of(this).get(CreateTripViewModel::class.java)
        binding.viewmodel  = viewModel

        viewModel?.listener = this

        Places.initialize(this, "AIzaSyCdOAkQd4rUbFOZviFvvkGe48Mjngt32Ck")
        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#F99C56")))
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title  = "Create a Trip"

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.visibility =android.view.View.GONE
        recommendation.visibility = android.view.View.GONE

        //current location
        getCurrentLocation(currentlocation)

        sourceEditText.setOnClickListener {
            // startActivity(Intent(this,LocationActivity::class.java))


            //Places.initialize(this, "AIzaSyBhGQmm31ctKdfQPmeGnLpGM4z3cPRdmPY")
            placeIntent(100, sourceEditText)
        }
        destinationEditText.setOnClickListener {
            //  Places.initialize(this, "AIzaSyBhGQmm31ctKdfQPmeGnLpGM4z3cPRdmPY")
            placeIntent(101, destinationEditText)

        }


        //date
        startdateEditText.setOnClickListener {
            editText2 = findViewById(R.id.startdateEditText)
            val dateTime = DateTimePicker()
            dateTime.displayDateAndTimePicker(this, editText2 )
        }

        enddateEditText.setOnClickListener {
            editText2 = findViewById(R.id.enddateEditText)
            val dateTime = DateTimePicker()
            dateTime.displayDateAndTimePicker(this, editText2 )
        }
        //time

        startTimeEditText.setOnClickListener {
            recommendation.visibility = android.view.View.VISIBLE
            val dateTime = DateTimePicker()
            dateTime.displayClock(this, startTimeEditText)

        }

        val ridersRecyclerView: RecyclerView = findViewById(R.id.inviteRidersLinearLayout)
        ridersRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL,false)
        recyclerViewAdapter = RiderRecyclerAdapter(contacts)
        inviteRidersLinearLayout.adapter = recyclerViewAdapter

        //contact
        inviteRidersTextView.setOnClickListener {

            startActivityForResult(Intent(this, ContactsActivity::class.java), 1000)
        }


        //milestone
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        customAdapter = RecyclerViewAdapter(models)
        recyclerView.adapter = customAdapter
        customAdapter.listener = this


    }


    override fun placeIntent(requestCode: Int, locationEditText: EditText) {
        // Places.initialize(this, "AIzaSyCYd9DNtP8fAnic_H5XwgCef7dmqj_7vB0")
        placeEditText = locationEditText
        val fieldlist: List<Place.Field> = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME)

        val placeOption = PlaceOptions.builder().country("IN")
            .backgroundColor(Color.parseColor("#EEEEEE"))
            .limit(10)
            .build(PlaceOptions.MODE_CARDS)
        val   MAPBOX_ACCESS_TOKEN = getString(R.string.MAPBOX_ACCESS_TOKEN)
        val intent = PlaceAutocomplete.IntentBuilder()
            .accessToken(MAPBOX_ACCESS_TOKEN)
            .placeOptions(placeOption)
            .build(this)
        startActivityForResult(intent, requestCode)


        /* val intent: Intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fieldlist)
                 .setCountry("IN").build(this)
         startActivityForResult(intent, requestCode)*/
    }




    //autocomplete result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //contact
        if (requestCode == 1000 ) {

            val  contact = data!!.getStringArrayListExtra("Data")
            size = contact.size
            val j = contacts.size -1
            if(size != 0) {
                for (index in contact) {
                    contacts.add(index)
                }
                inviteRidersTextView2.visibility = android.view.View.GONE
                recyclerViewAdapter.notifyItemRangeInserted(j,size)


            }else
                inviteRidersTextView2.visibility = android.view.View.VISIBLE



        }
        else if (resultCode == Activity.RESULT_OK){
            val feature = PlaceAutocomplete.getPlace(data)
            if (requestCode == 100) {
                sourceLatLng.add(0, feature.center()?.longitude())
                sourceLatLng.add(1, feature.center()?.latitude())
                // Log.d("this", "$sourceLatLng")
                sourceAddress = feature.text().toString()

                // sourceLatLng = place.name!!
                // mi leston esplace.add(place.latLng)
                placeEditText.setText(feature.text())

            } else if (requestCode == 101) {

                // Toast.makeText(this, feature.text(), Toast.LENGTH_LONG).show()
                //destination = place.name!!
                destinationLatLng.add(0, feature.center()?.longitude())
                destinationLatLng.add(1,feature.center()?.latitude())
                destinationAddress = feature.text().toString()


                placeEditText.setText(feature.text())

            }
            else if(requestCode == 102){

                // val place = Autocomplete.getPlaceFromIntent(data!!)
                feature.center()?.coordinates()?.let { milestoneDestination.add(it) }
                //  Log.d("hello", "$milestoneDestination")
                milestoneDestination_1.add(feature.text().toString())
                //placeEditText.text = Editable.Factory.getInstance().newEditable(place.name)
                placeEditText.setText(feature.text())

            }else if(requestCode == 103) {
                //val place = Autocomplete.getPlaceFromIntent(data!!)
                //milestonesource.add(place.name!!)
                //placeEditText.text = Editable.Factory.getInstance().newEditable(place.name)
                milestonesource_1.add(feature.text().toString())
                feature.center()?.coordinates()?.let { milestonesource.add(it) }
                placeEditText.setText(feature.text())
            }
            else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                val status = Autocomplete.getStatusFromIntent(data!!)

                Toast.makeText(applicationContext, status.statusMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    override fun success(
        name: String,
        destination: String,
        source: String,
        startDate: String,
        endDate: String,
        startTime: String
    ) {

        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, TripSummaryActivity::class.java)
        intent.putExtra("name", name)
        intent.putExtra("startDate", startDate)
        intent.putExtra("endDate", endDate)
        intent.putExtra("startTime", startTime)
        intent.putExtra("destination", destinationLatLng)
        intent.putExtra("source", sourceLatLng)
        intent.putExtra("destination1", destination)
        intent.putExtra("source1", source)
        intent.putExtra("contact", contacts)
        intent.putExtra("milestones", models)
        intent.putExtra("milestonesource", milestonesource)
        intent.putExtra("milestonedestination", milestoneDestination)
        intent.putExtra("milestonesource1", milestonesource_1)
        intent.putExtra("milestonedestination1", milestoneDestination_1)
        startActivity(intent)


    }

    override fun onStarted() {
        Toast.makeText(this, "started", Toast.LENGTH_SHORT).show()
    }

    override fun Failure() {
        Toast.makeText(this, "Please Fill all the fields", Toast.LENGTH_SHORT).show()
    }

    override fun addMilestone(source: String) {

        val i = models.size
        models.add("Milestone ${i + 1}")
        recyclerView.visibility = android.view.View.VISIBLE

        done.setBackgroundResource(R.drawable.tripbutton)
        customAdapter.notifyItemInserted(models.size )

    }

    override fun CurrentLocation(currentlocation: TextView) {
        getCurrentLocation(currentlocation)
    }
    override fun onSupportNavigateUp(): Boolean {
        startActivity(Intent(this, HomeActivity::class.java))
        return true
    }


}