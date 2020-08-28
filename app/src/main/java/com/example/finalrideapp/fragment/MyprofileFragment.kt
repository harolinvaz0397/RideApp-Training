package com.example.finalrideapp.fragment

import android.app.PendingIntent.getActivity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.SeekBar
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalrideapp.Adapters.ProfileAdapter
import com.example.finalrideapp.R
import com.example.finalrideapp.model.DataModels.ProfileActivities
import com.example.finalrideapp.model.db.AppDatabase
import com.example.finalrideapp.model.network.ApiClient
import com.example.finalrideapp.model.preferences.PreferenceProvider
import com.example.finalrideapp.model.repositories.NewUserRepository
import com.example.finalrideapp.viewmodel.MyprofileViewModel
import com.example.finalrideapp.viewmodel.MyprofileViewModelFactory
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_tool_kit.*
import kotlinx.android.synthetic.main.myprofile_fragment.*


class MyprofileFragment : Fragment() {

    /*
    private val activities = listOf(
        ProfileActivities("Belal Khan", "Ranchi Jharkhand", 2007),
        ProfileActivities("Ramiz Khan", "Ranchi Jharkhand", 2007),
        ProfileActivities("Ramiz Khan", "Ranchi Jharkhand", 2007),
        ProfileActivities("Faiz Khan", "Ranchi Jharkhand",2006),
        ProfileActivities("Faiz Khan", "Ranchi Jharkhand",2006),
        ProfileActivities("Faiz Khan", "Ranchi Jharkhand",2006),
        ProfileActivities("Yashar Khan", "Ranchi Jharkhand", 2005),
        ProfileActivities("Yashar Khan", "Ranchi Jharkhand", 2005),
        ProfileActivities("Ramiz Khan", "Ranchi Jharkhand", 2007)
    )

     */
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var factory: MyprofileViewModelFactory
    private lateinit var viewModel: MyprofileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.myprofile_fragment, container, false)
        //val toolBar = activity?.findViewById<Toolbar>(R.id.tbProfile)
        // this.activity?.setActionBar(toolBar)
        setHasOptionsMenu(true)
        //setSupportActionBar(toolbar)
        return rootView
    }

  /*  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar = view.findViewById(R.id.tbMyProfile)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        super.onViewCreated(view, savedInstanceState)
    }*/

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val context = getContext()!!

        val api = ApiClient(context)
        val db = AppDatabase(context)
        val prefs = PreferenceProvider(context)
        val repository = NewUserRepository(api, db, prefs)
        val factory = MyprofileViewModelFactory(repository)
        viewModel = ViewModelProviders.of(this, factory).get(MyprofileViewModel::class.java)
        viewModel.displayRiderName(context)
       // supportActionBar?.setDisplayHomeAsUpEnabled(true)



        //getting recyclerview from xml
        val recyclerView = profileRecyclerView as RecyclerView
        //val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        viewModel.getTrips()

        //lateinit var tripsList: List<ProfileActivities>
        var tripsList = ArrayList<ProfileActivities>()
        lateinit var sortedTripsList: List<ProfileActivities>
        val month: List<String> = listOf<String>("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")

        viewModel.userName.observe(viewLifecycleOwner, Observer {
            tvUserName.text = it
        })

        viewModel.activities.observe(viewLifecycleOwner, Observer {

            //Log.d("sucessssssss", it.toString())  it.data?.let {

            it.data?.let {
                //Log.d("big successsss", it.toString())
                val trips: JsonArray = it.get("trips").asJsonArray
                //lateinit var tripsList: List<ProfileActivities>

                for (i in 0..trips.size()-1) {
                    var tripsJson: JsonObject = trips[0].asJsonObject
                    var _id = tripsJson.get("_id").toString()
                    var name = tripsJson.get("name").toString()
                    name = name.substring(1, name.length-1)
                    var startDate = tripsJson.get("startDate").toString()
                    var endDate = tripsJson.get("endDate").toString()
                    var date: String = startDate.substring(9, 11) + "-" + endDate.substring(9, 11) + " " + month[endDate.substring(6,8).toInt()]
                    var year: String = endDate.substring(1, 5)
                    tripsList.add( ProfileActivities (_id, name, date, year) )
                }
                sortedTripsList = tripsList.sortedByDescending { it.year }
                recyclerView.adapter = ProfileAdapter(tripsList)
            }

        })

        customSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                //recyclerView.scrollTo(0, progress)
                // write custom code for progress is changed

                val totalItem = tripsList.count()
                val item: Int = 100 / totalItem
                val position = progress / item
                var year = ""
                if (position != totalItem) {
                    year = sortedTripsList[position].year.toString()
                }

                recyclerView.layoutManager!!.smoothScrollToPosition(
                    recyclerView,
                    RecyclerView.State(),
                    position
                )

                val thumbPos: Int = seek.getThumb().getBounds().centerX()
                seekbarText.setText(year)
                seekbarText.y = (thumbPos.toFloat() + 85)
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
            }
        })


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.edit, menu)
    }

}

/*
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZWRjODg1NjIwYjE2MjAwMTc0ZDU4NGYiLCJpYXQiOjE1OTI1NjY3ODUsImV4cCI6MTU5MjU4NDc4NX0.w-1kXZHwYHkRkznAU3Y8mwJF2Na9ab1GScFlvJLxFyY
/*

        val context = getContext()!!

        val api = ApiClient(context)
        val db = AppDatabase(context)
        val prefs = PreferenceProvider(context)
        val repository = NewUserRepository(api, db, prefs)
        val factory = MyprofileViewModelFactory(repository)
        viewModel = ViewModelProviders.of(this, factory).get(MyprofileViewModel::class.java)

        //getting recyclerview from xml
        val recyclerView = profileRecyclerView as RecyclerView
        //val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        //creating our adapter
        //val adapter = ProfileAdapter(actiities)

        viewModel.getTrips()
        viewModel.activities.observe(viewLifecycleOwner, Observer {

            Log.d("sucessssssss", it.toString())

            lateinit var tripsList: List<ProfileActivities>
            for (i in 1..it.count) {
                val jsonObject: JsonObject = it.trips.get(i).asJsonObject
                val _id = jsonObject.get("_id").toString()
                val name = jsonObject.get("name").toString()
                Log.d("nameeeeeeeeeeeee", name)
                val tripsList = listOf(
                    ProfileActivities(name, _id, 2007)
                )
            }
            //now adding the adapter to recyclerview
            Log.d("hellooooo", it.toString())
            recyclerView.adapter = ProfileAdapter(tripsList)

        })
 */



/*
//now adding the adapter to recyclerview
        recyclerView.adapter = ProfileAdapter(activities.sortedByDescending { it.year })

        val sortedActivities = activities.sortedByDescending { it.year }

        customSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                //recyclerView.scrollTo(0, progress)
                // write custom code for progress is changed

                val totalItem = activities.count()
                val item: Int = 100 / totalItem
                val position = progress / item
                var year = ""
                if (position != totalItem) {
                    year = sortedActivities[position].year.toString()
                }

                recyclerView.layoutManager!!.smoothScrollToPosition(
                    recyclerView,
                    RecyclerView.State(),
                    position
                )

                val thumbPos: Int = seek.getThumb().getBounds().centerX()
                seekbarText.setText(year)
                seekbarText.y = (thumbPos.toFloat() + 85)
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
            }
        })
 */


 /*
    viewModel.activities.observe(viewLifecycleOwner, Observer {

            //Log.d("sucessssssss", it.toString())

            it.data?.let {
                //Log.d("big successsss", it.toString())
                val trips: JsonArray = it.get("trips").asJsonArray
                //lateinit var tripsList: List<ProfileActivities>

                var tripsJson: JsonObject = trips[0].asJsonObject
                var _id = tripsJson.get("_id").toString()
                var name = tripsJson.get("name").toString()
                name = name.substring(1, name.length-1)
                var startDate = tripsJson.get("startDate").toString()
                var endDate = tripsJson.get("endDate").toString()
                var date: String = startDate.substring(9, 11) + "-" + endDate.substring(9, 11) + " " + month[endDate.substring(6,8).toInt()]
                var year: String = endDate.substring(1, 5)
                tripsList = listOf( ProfileActivities (_id, name, date, year) )

                for (i in 1..trips.size()-1) {
                    tripsJson = trips[i].asJsonObject
                    //Log.d("another big successsss", tripsJson.toString())
                    _id = tripsJson.get("_id").toString()
                    name = tripsJson.get("name").toString()
                    name = name.substring(1, name.length-1)
                    startDate = tripsJson.get("startDate").toString()
                    endDate = tripsJson.get("endDate").toString()
                    date = startDate.substring(9, 11) + "-" + endDate.substring(9, 11) + " " + month[endDate.substring(6,8).toInt()]
                    year = endDate.substring(1, 5)
                    tripsList = tripsList + listOf( ProfileActivities (_id, name, date, year) )
                }
                sortedTripsList = tripsList.sortedByDescending { it.year }
                recyclerView.adapter = ProfileAdapter(tripsList)
            }

        })
 */