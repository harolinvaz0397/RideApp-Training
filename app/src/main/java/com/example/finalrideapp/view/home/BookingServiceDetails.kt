package com.example.finalrideapp.view.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.finalrideapp.R
import com.example.finalrideapp.databinding.ActivityBookingServiceDetailsBinding
import com.example.finalrideapp.viewmodel.ServiceDetailsViewModel

class BookingServiceDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityBookingServiceDetailsBinding>(this, R.layout.activity_booking_service_details)
        val serviceDetailsViewModel = ViewModelProvider(this).get(ServiceDetailsViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = serviceDetailsViewModel

        var toolbar = findViewById(R.id.tbBookingDetails) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle :Bundle ?=intent.extras
        if (bundle!=null){
            val serviceId = bundle.getString("Service Id") // 1
           // Toast.makeText(this, serviceId, Toast.LENGTH_SHORT).show()
            serviceDetailsViewModel.getServiceDetails(this, serviceId!!)
        }

        serviceDetailsViewModel.isOlder.observe(this, Observer {
            if(it) {
                    binding.tvServiceStatus.text = this.getString(R.string.past)
                    binding.tvServiceStatus.background = ResourcesCompat.getDrawable(this.resources, R.drawable.green_rounded_textview, null)
                    binding.tvMonthAndYear.setTextColor(this.resources.getColor(R.color.green))
                    binding.tvServiceDate.setTextColor(this.resources.getColor(R.color.green))

            }
            else {
                binding.tvServiceStatus.text = this.getString(R.string.new_service)
                binding.tvServiceStatus.background = ResourcesCompat.getDrawable(this.resources, R.drawable.rounded_textview, null)
                binding.tvMonthAndYear.setTextColor(this.resources.getColor(R.color.orange))
               binding.tvServiceDate.setTextColor(this.resources.getColor(R.color.orange))


            }
        })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.view_invoice,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.view_invoice_menu -> {
                val intentViewInvoice = Intent(this, InvoiceDetailsActivity::class.java)
                startActivity(intentViewInvoice)
            }

        }
        return true
    }

}
