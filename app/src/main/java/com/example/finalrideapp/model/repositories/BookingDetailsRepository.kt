package com.example.finalrideapp.model.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.finalrideapp.model.DataModels.BookServiceObject
import com.example.finalrideapp.model.DataModels.BookingDetails
import com.example.finalrideapp.model.DataModels.DateTime

class BookingDetailsRepository {
    private val _bookingDetail = MutableLiveData<ArrayList<BookingDetails>>()
    val bookingDetailList = ArrayList<BookingDetails>()
    val bookingDetail: LiveData<ArrayList<BookingDetails>>
        get() {
            return _bookingDetail
        }

    init {
        bookingDetailList.add(BookingDetails("Mobile Number", BookServiceObject.mobileNumber.value.toString()))
        bookingDetailList.add(BookingDetails("Vehicle Number", BookServiceObject.vehicleNumber.value.toString()))
        bookingDetailList.add(BookingDetails("Service Type", BookServiceObject.serviceType.value.toString()))
        bookingDetailList.add(BookingDetails("Slot Date", DateTime.dateString2))
        bookingDetailList.add(BookingDetails("Time", DateTime.timeString))
        bookingDetailList.add(BookingDetails("Dealer Name",BookServiceObject.selectedDealerName.value.toString()))
        bookingDetailList.add(BookingDetails("City",BookServiceObject.selectedDealerCity.value.toString()))
        _bookingDetail.value = bookingDetailList
    }

}