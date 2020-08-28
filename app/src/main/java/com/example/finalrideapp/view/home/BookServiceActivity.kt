package com.example.finalrideapp.view.home

//import com.example.rideapp.databinding.ActivityBookServiceBinding

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.finalrideapp.R
import com.example.finalrideapp.databinding.ActivityBookServiceBinding
import com.example.finalrideapp.viewmodel.BookServiceViewModel
import kotlinx.android.synthetic.main.activity_book_service.*
import timber.log.Timber


class BookServiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityBookServiceBinding>(this,
            R.layout.activity_book_service
        )
        val bookServiceViewModel = ViewModelProviders.of(this).get(BookServiceViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel =  bookServiceViewModel
        bookServiceViewModel.getMobileNumber(this)

        val toolbar = findViewById(R.id.tbBookService) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


        val serviceTypes = resources.getStringArray(R.array.service_types_array)
        val spinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, serviceTypes)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerServiceType.adapter = spinnerAdapter

        bookServiceViewModel.numberOfAttemptsError.observe(this, Observer {

            binding.textInputLayout1.error = it
            binding.editTextMobile.isEnabled = true
            binding.editTextMobile.isFocusableInTouchMode = true
            binding.editTextMobile.setSelection(binding.editTextMobile.text.length)
            binding.editTextMobile.requestFocus()
            val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.editTextMobile, InputMethodManager.SHOW_IMPLICIT)

        })

        bookServiceViewModel.isVehicleTypeEmpty.observe(this, Observer {
            if(it == false) {
                binding.textInputLayout2.isErrorEnabled = true
                binding.textInputLayout2.error = getString(R.string.please_enter_vehicle_type)
            }
            else {
                binding.textInputLayout2.isErrorEnabled = false
                binding.textInputLayout2.error = ""
            }
            //checkRequiredFields()
        })

        bookServiceViewModel.isVehicleNumberEmpty.observe(this, Observer {
            if(it == false) {
                binding.textInputLayout3.isErrorEnabled = true
                binding.textInputLayout3.error = getString(R.string.please_enter_vehicle_number)
            }
            else {
                binding.textInputLayout3.isErrorEnabled = false
                binding.textInputLayout3.error = ""
            }
            //checkRequiredFields()
        })

        bookServiceViewModel.mobileNumber.observe(this, Observer {
            if(it.length < 10){
                binding.textInputLayout1.error = getString(R.string.enter_valid_number)
                binding.buttonFindDealer.isEnabled = false

            }
            else {
                binding.textInputLayout1.error = ""
                //binding.buttonFindDealer.isEnabled = !it.isNullOrEmpty()
            }


        })


        bookServiceViewModel.serviceType.observe(this, Observer {
            Timber.d(it)
            if (!it.isNullOrEmpty() && binding.editTextMobile.text.length == 10)
                binding.buttonFindDealer.isEnabled = true
            // checkRequiredFields()
        }
        )

    }

}


