package com.example.finalrideapp.view.home

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.finalrideapp.R
import com.example.finalrideapp.databinding.ActivityDealerProfileBinding
import com.example.finalrideapp.model.db.AppDatabase
import com.example.finalrideapp.model.network.ApiClient
import com.example.finalrideapp.model.preferences.PreferenceProvider
import com.example.finalrideapp.model.repositories.NewUserRepository
import com.example.finalrideapp.view.auth.AuthListener
import com.example.finalrideapp.viewmodel.DealerProfileViewModel
import com.example.finalrideapp.viewmodel.DealerProfileViewModelFactory
import com.example.finalrideapp.viewmodel.LoginViewModelFactory
import com.example.rideapp.helpers.DateAndTimePicker
import kotlinx.android.synthetic.main.activity_dealer_profile.*
import java.util.*

class DealerProfileActivity : AppCompatActivity(), AuthListener {

    private var selectedDealerName:String? = null
    private var selectedDealerCity: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_dealer_profile)

        val api = ApiClient(this)
        val db = AppDatabase(this)
        val prefs = PreferenceProvider(this)
        val repository = NewUserRepository(api, db, prefs)
        val dealerProfileViewModelFactory = DealerProfileViewModelFactory(repository)

        val binding: ActivityDealerProfileBinding = DataBindingUtil.setContentView<ActivityDealerProfileBinding>(this, R.layout.activity_dealer_profile)
        val dealerProfileViewModel = ViewModelProviders.of(this, dealerProfileViewModelFactory).get(DealerProfileViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewmodel =  dealerProfileViewModel
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dealerProfileViewModel.authListener = this

        val bundle :Bundle ?=intent.extras
        if (bundle!=null){
            val dealerId = bundle.getString("Dealer Id") // 1
            //Toast.makeText(this, dealerId, Toast.LENGTH_SHORT).show()
            dealerProfileViewModel.getDealerDetails(dealerId!!)
            /*val delimitedName = message?.split(",")
            selectedDealerName = delimitedName?.get(0)
            selectedDealerCity = delimitedName?.get(1)?.replace(" ","")
            if (selectedDealerName != null && selectedDealerCity!=null) {
                Log.d("Message:", selectedDealerCity + " " +  selectedDealerName)
                dealerProfileViewModel.getDealerDetails(selectedDealerName!!, selectedDealerCity!!)
            }*/
        }

        buttonCheckSlot.setOnClickListener {
            val dateAndTimePicker = DateAndTimePicker()
            val dateTime = dateAndTimePicker.displayDateAndTimePicker(this, "buttonCheckSlot")
            // BookServiceObject.updateChosenDealerDetails(selectedDealerName!!,selectedDealerCity!!)
        }

        dealerProfileViewModel.dealerContact.observe(this, androidx.lifecycle.Observer {
            binding.buttonCheckSlot.isEnabled = true
        })




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
