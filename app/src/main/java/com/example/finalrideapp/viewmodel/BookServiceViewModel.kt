package com.example.finalrideapp.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalrideapp.R
import com.example.finalrideapp.model.DataModels.BookServiceObject
import com.example.finalrideapp.model.preferences.PreferenceProvider
import com.example.finalrideapp.view.home.FindDealersActivity

class BookServiceViewModel: ViewModel() {
    // val bookService = BookService()
    val mobileNumber = MutableLiveData<String>()
    val comments = MutableLiveData<String>()
    val serviceType = MutableLiveData<String>()
    val vehicleType = MutableLiveData<String>()
    val vehicleNumber = MutableLiveData<String>()
    val spinnerTitle = MutableLiveData<String>()
    val numberOfAttemptsError = MutableLiveData<String>()
    var isVehicleNumberEmpty = MutableLiveData<Boolean>()
    var isVehicleTypeEmpty = MutableLiveData<Boolean>()
    var attemptsRemaining:LiveData<Int> = BookServiceObject.numberOfAttempts
    lateinit var context: Context


    //for editTexts
    val focusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
        val editText = v as EditText
        if (editText == v.findViewById(R.id.editTextVehicleType))
            isVehicleTypeEmpty.value = !(editText.text.isEmpty() && !hasFocus)
        if (editText == v.findViewById(R.id.editTextVehicleNumber))
            isVehicleNumberEmpty.value = !(editText.text.isEmpty() && !hasFocus)
    }


    init {
        //mobileNumber.value = BookServiceObject.mobileNumber.value
        vehicleType.value = BookServiceObject.vehicleType.value
        vehicleNumber.value = BookServiceObject.vehicleNumber.value
        serviceType.value = BookServiceObject.serviceType.value
        comments.value = BookServiceObject.comments.value

    }

    fun getMobileNumber(context: Context) {
        val preferenceProvider = PreferenceProvider(context)
        mobileNumber.value = preferenceProvider.getPhoneNumber()
    }


    //for spinner
    val clicksListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (parent != null) {
                if ( position == 0) {
                    serviceType.value = ""
                    spinnerTitle.value = ""
                }
                else {
                    serviceType.value = parent.getItemAtPosition(position) as String
                    spinnerTitle.value = "Service Type"

                }
            }
        }
    }



    fun onEditButtonClicked(view: View) {
        context = view.context
        BookServiceObject.findAttemptsRemaining()

        attemptsRemaining = BookServiceObject.numberOfAttempts
        if (attemptsRemaining.value!! > 0)
            numberOfAttemptsError.value = "You have only "+attemptsRemaining.value+" attempts left. Your new number will be your login ID"
        else
            numberOfAttemptsError.value = "You have no attempts left"

    }


    fun onFindDealersButtonClicked(view: View) {
        context = view.context
        val bookingInformation = ArrayList<MutableLiveData<String>>()
        bookingInformation.add(0, mobileNumber)
        bookingInformation.add(1, vehicleNumber)
        bookingInformation.add(2, vehicleType)
        bookingInformation.add(3, serviceType)
        bookingInformation.add(4, comments)
        BookServiceObject.updateBookingDetails(bookingInformation)
        var preferenceProvider = PreferenceProvider(context)
        preferenceProvider.saveNumberOfAttemps(attemptsRemaining.value!!)
        val intentFindDealers = Intent(context, FindDealersActivity::class.java);
        intentFindDealers.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intentFindDealers)
    }


}


@BindingAdapter("onFocus")
fun bindFocusChange(editText: EditText, onFocusChangeListener: View.OnFocusChangeListener?) {
    if (editText.onFocusChangeListener == null) {
        editText.onFocusChangeListener = onFocusChangeListener
    }
}