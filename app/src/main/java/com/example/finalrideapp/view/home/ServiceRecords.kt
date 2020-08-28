package com.example.finalrideapp.view.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalrideapp.Adapters.ServiceRecordsAdapter
import com.example.finalrideapp.R
import com.example.finalrideapp.databinding.ActivityServiceRecordsBinding
import com.example.finalrideapp.model.DataModels.ServiceRecordModel
import com.example.finalrideapp.viewmodel.ServiceRecordsViewModel
import kotlinx.android.synthetic.main.activity_service_records.*
import java.util.ArrayList

class ServiceRecords : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_service_records)



        val binding: ActivityServiceRecordsBinding = DataBindingUtil.setContentView(this, R.layout.activity_service_records)
        val serviceRecordsViewModel = ViewModelProvider(this).get(ServiceRecordsViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = serviceRecordsViewModel

        var toolbar = findViewById(R.id.tbServiceRecord) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val category2: List<String> = arrayListOf("Service Type","Free service","General service","Breakdown")
        val category1: List<String> = arrayListOf("Vehicle Type","Classic 350- Black","Thunderbird 350-Blue","Bullet 350-Black")
        val dataAdapter1: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, category1)
        val dataAdapter2: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, category2)
        binding.spinner1.adapter = dataAdapter1
        //spinner1TextView.text = "Vehicle Type"
        binding.spinner2.adapter = dataAdapter2
        // spinner2TextView.text = "Service Type"


        supportActionBar?.elevation = 0F
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#ED7E2B")))



        //serviceRecordsViewModel.initRecyclerView(this, binding.rvServiceRecords,"")
        serviceRecordsViewModel.initRecyclerView(this, binding.rvServiceRecords)


        serviceRecordsViewModel.serviceType.observe(this, Observer {
            if (!it.isNullOrEmpty() && it.isNotEmpty()) {
                binding.spinner2TextView.text = getString(R.string.service_type)
                //serviceRecordsViewModel.initRecyclerView(this, rvServiceRecords, it)
            }
            else
                binding.spinner2TextView.text = ""

        })

        serviceRecordsViewModel.vehicleType.observe(this, Observer {
            if (!it.isNullOrEmpty() && it.isNotEmpty())
                binding.spinner1TextView.text = getString(R.string.vehicle_type)
            else
                binding.spinner1TextView.text = ""

        })




    }




}
