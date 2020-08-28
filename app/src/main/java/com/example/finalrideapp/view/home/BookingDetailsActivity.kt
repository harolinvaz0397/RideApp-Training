package com.example.finalrideapp.view.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalrideapp.Adapters.DetailsRecyclerViewAdapter
import com.example.finalrideapp.R
import com.example.finalrideapp.databinding.ActivityBookingDetailsBinding
import com.example.finalrideapp.model.db.AppDatabase
import com.example.finalrideapp.model.network.ApiClient
import com.example.finalrideapp.model.preferences.PreferenceProvider
import com.example.finalrideapp.model.repositories.NewUserRepository
import com.example.finalrideapp.view.auth.AuthListener
import com.example.finalrideapp.viewmodel.BookingDetailsViewModel
import com.example.finalrideapp.viewmodel.BookingDetailsViewModelFactory
import com.example.finalrideapp.viewmodel.LoginViewModelFactory
import kotlinx.android.synthetic.main.activity_booking_details.*


class BookingDetailsActivity : AppCompatActivity(), AuthListener {
    //var bookingDetailsViewModel = BookingDetailsViewModel()
    private var bookingDetailsViewModel: BookingDetailsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_booking_details)

        val api = ApiClient(this)
        val db = AppDatabase(this)
        val prefs = PreferenceProvider(this)
        val repository = NewUserRepository(api, db, prefs)
        val bookingDetailsViewModelFactory = BookingDetailsViewModelFactory(repository)

        val binding:ActivityBookingDetailsBinding = DataBindingUtil.setContentView<ActivityBookingDetailsBinding>(this, R.layout.activity_booking_details)
        bookingDetailsViewModel = ViewModelProvider(this, bookingDetailsViewModelFactory).get(BookingDetailsViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewmodel = bookingDetailsViewModel
        bookingDetailsViewModel!!.authListener = this
        initRecyclerView()

        val toolbar = findViewById(R.id.tbBookDetails) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }




    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_edit ->  {
                val intentEditDetails = Intent(this, BookServiceActivity::class.java)
                startActivity(intentEditDetails)
            }
        }
        return true
    }

    fun initRecyclerView() {
        rvBookingDetails.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvBookingDetails.adapter = DetailsRecyclerViewAdapter(bookingDetailsViewModel!!.bookingDetails.value!!)
    }

    override fun onStarted() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun inputValidation(field: Int, message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSuccess() {
        Log.d("Message:", "service successfully added")
        //Toast.makeText(this, "dealer successfully created", Toast.LENGTH_LONG).show()
        val intentBookingSuccess = Intent(this, SuccessScreenActivity::class.java);
        intentBookingSuccess.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intentBookingSuccess)
        //finish()
    }

    override fun onFailure(message: String) {
        Log.d("Message",message)
        Toast.makeText(this, "Something Went Wrong!! Please try again.", Toast.LENGTH_LONG).show()
        val intentBookService = Intent(this, BookServiceActivity::class.java);
        intentBookService.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intentBookService)
    }

}
