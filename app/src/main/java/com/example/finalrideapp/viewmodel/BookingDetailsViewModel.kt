package com.example.finalrideapp.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalrideapp.model.DataModels.BookServiceObject
import com.example.finalrideapp.model.DataModels.BookingDetails
import com.example.finalrideapp.model.DataModels.DateTime
import com.example.finalrideapp.model.network.responses.AuthResponse
import com.example.finalrideapp.model.repositories.BookingDetailsRepository
import com.example.finalrideapp.model.repositories.NewUserRepository
import com.example.finalrideapp.util.ApiException
import com.example.finalrideapp.util.Coroutines
import com.example.finalrideapp.util.NoInternetException
import com.example.finalrideapp.util.isPhoneValid
import com.example.finalrideapp.view.auth.AuthListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BookingDetailsViewModel(private val repository: NewUserRepository): ViewModel() {
    val comments = MutableLiveData<String>()
    val bookingDetails =  MutableLiveData<ArrayList<BookingDetails>>()

    var authListener: AuthListener? = null

    val bookingDetailsRepository = BookingDetailsRepository()

    init {
        bookingDetails.value = bookingDetailsRepository.bookingDetail.value
        comments.value = BookServiceObject.comments.value
    }

    val phone = BookServiceObject.mobileNumber.value.toString()
    val slotDate = DateTime.dateString
    val slotTime = DateTime.timeString
    val vehicleNumber = BookServiceObject.vehicleNumber.value.toString()
    val vehicleType = BookServiceObject.vehicleType.value.toString()
    val serviceType = BookServiceObject.serviceType.value.toString()
    val comment = BookServiceObject.comments.value.toString()
    val dealer = BookServiceObject.selectedDealerId.value.toString()

    fun onBookButtonClicked() {

        Coroutines.main {
            try {


                val bookingDetailsResponse: AuthResponse
                bookingDetailsResponse = repository.funAddService(phone, slotDate, slotTime, vehicleNumber, vehicleType, serviceType, comment, dealer)

                bookingDetailsResponse.data?.let {

                    authListener?.onSuccess()

                    return@main
                }
                authListener?.onFailure(bookingDetailsResponse.message!!)
            } catch(e: ApiException){
                authListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                authListener?.onFailure(e.message!!)
            }
        }

    }

}

/*
fun onBookButtonClicked(view_invoice: View){
        val context: Context = view_invoice.context
        RetrofitClient.apiInstance_new.addService(BookServiceObject.mobileNumber.value.toString(),
            DateTime.dateString,
            DateTime.timeString,
            BookServiceObject.vehicleNumber.value.toString(),
            BookServiceObject.vehicleType.value.toString(),
            BookServiceObject.serviceType.value.toString(),
            BookServiceObject.comments.value.toString(),
            BookServiceObject.selectedDealerId.value.toString()
        ).enqueue(object : Callback<AddServiceResponse> {
            override fun onFailure(call: Call<AddServiceResponse>, t: Throwable) {
                Log.d("Message",t.message)
                Toast.makeText(context, "Something Went Wrong!! Please try again.", Toast.LENGTH_LONG).show()
                val intentBookService = Intent(context, BookServiceActivity::class.java);
                intentBookService.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intentBookService)
            }

            override fun onResponse(call: Call<AddServiceResponse>, response: Response<AddServiceResponse>) {
                if (response.code() == 200) {
                    Log.d("Message:", response.body()?.data?.get("message").toString())
                    val intentBookingSuccess = Intent(context, SuccessScreenActivity::class.java);
                    intentBookingSuccess.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intentBookingSuccess)

                }
                else {
                    Log.d("Message:","Something went wrong "+response.code())
                    try {
                        var errorResponse = JSONObject(response.errorBody()?.string())
                        Log.d("Message:", errorResponse.getString("name") + errorResponse.getString("message"))

                    }catch(ex: Exception) {
                        Log.d("Message:", ex.message.toString())

                    }
                }
            }

        })

    }
 */