package com.example.finalrideapp.view.home

import android.app.PendingIntent.getActivity
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalrideapp.Adapters.DealersRecycleViewAdapter
import com.example.finalrideapp.R
import com.example.finalrideapp.databinding.ActivityFindDealersBinding
import com.example.finalrideapp.helpers.GooglePlacesApiClient
import com.example.finalrideapp.helpers.getCurrentLocation
import com.example.finalrideapp.model.db.AppDatabase
import com.example.finalrideapp.model.network.ApiClient
import com.example.finalrideapp.model.preferences.PreferenceProvider
import com.example.finalrideapp.model.repositories.NewUserRepository
import com.example.finalrideapp.view.auth.AuthListener
import com.example.finalrideapp.viewmodel.FindDealersViewModelFatory
import com.example.finalrideapp.viewmodel.LoginViewModelFactory
import com.example.finalrideapp.viewmodels.FindDealersViewModel
import kotlinx.android.synthetic.main.activity_find_dealers.*


class FindDealersActivity : AppCompatActivity(), AuthListener {

    private var findDealersViewmodel: FindDealersViewModel? = null

    //var findDealersViewmodel = FindDealersViewModel()
    //val dealersList = ArrayList<Dealers>()
    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_find_dealers)
        // googlePlacesApiClient.checkAndEnablePermission(this, this)
        //googlePlacesApiClient.getLocationInfo(this)

        val api = ApiClient(this)
        val db = AppDatabase(this)
        val prefs = PreferenceProvider(this)
        val repository = NewUserRepository(api, db, prefs)
        val findDealersViewModelFatory = FindDealersViewModelFatory(repository)


        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)


        val binding: ActivityFindDealersBinding = DataBindingUtil.setContentView<ActivityFindDealersBinding>(this, R.layout.activity_find_dealers)
        findDealersViewmodel = ViewModelProvider(this, findDealersViewModelFatory).get(FindDealersViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewmodel = findDealersViewmodel

        findDealersViewmodel!!.authListener = this

        val toolbar = findViewById<Toolbar>(R.id.tbFindDealer)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val upArrow =  ContextCompat.getDrawable(this,R.drawable.abc_ic_ab_back_material)
        upArrow?.setColorFilter(ContextCompat.getColor(this, R.color.upArrow), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.title = ""
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val searchIcon: ImageView = searchViewDealers.findViewById(R.id.search_mag_icon) as ImageView
        searchIcon.layoutParams = LinearLayout.LayoutParams(0, 0)

        getCurrentLocation(tvCurrentLocationName)
        findDealersViewmodel!!.initRecyclerView(this,rvDealersList, searchViewDealers)



    }

    override fun onStarted() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun inputValidation(field: Int, message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
