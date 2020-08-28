package com.example.finalrideapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalrideapp.helpers.replaceAndCapitalize
import com.example.finalrideapp.model.DataModels.BookServiceObject
import com.example.finalrideapp.model.network.responses.AuthResponse
import com.example.finalrideapp.model.network.responses.DealersResponse
import com.example.finalrideapp.model.repositories.NewUserRepository
import com.example.finalrideapp.util.ApiException
import com.example.finalrideapp.util.Coroutines
import com.example.finalrideapp.util.NoInternetException
import com.example.finalrideapp.util.isPhoneValid
import com.example.finalrideapp.view.auth.AuthListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DealerProfileViewModel(private val repository: NewUserRepository): ViewModel() {
    val dealerName = MutableLiveData<String>()
    val dealerAddress = MutableLiveData<String>()
    val dealerInfo = MutableLiveData<String>()
    val dealerContact = MutableLiveData<String>()
   val distance = MutableLiveData<String>()
    val dealerRating = MutableLiveData<Float>()
    val dealerId = MutableLiveData<String>()

    var authListener: AuthListener? = null


    fun getDealerDetails(id: String) {

        Coroutines.main {
            try {
                val dealersResponse: DealersResponse = repository.funGetDealerDetails(id)


                //val authResponse = repository.funUserLoginEmail(emailOrPhone.toString(), pass.toString())
                dealersResponse.data?.let {

                    val dealer = it
                    dealerId.value = id
                    dealerName.value = dealer.get("name").toString().replaceAndCapitalize()
                    dealerAddress.value = dealer.get("address").toString().replaceAndCapitalize()+", "+dealer.get("city").toString().replaceAndCapitalize()+", "+dealer.get("state").toString().replaceAndCapitalize()
                    dealerContact.value = "9901906078"  //dealer.get("phone").toString().replace("\"","")
                    dealerInfo.value = "Authorised Service Center"
                    distance.value = "10 k.m"
                    dealerRating.value = dealer.get("ratings").asFloat
                    BookServiceObject.updateChosenDealerDetails(dealer.get("name").toString().replaceAndCapitalize(),
                        dealer.get("city").toString().replaceAndCapitalize(),
                        dealerId.value.toString()
                    )

                    return@main
                }
                authListener?.onFailure(dealersResponse.message!!)
            } catch(e: ApiException){
                authListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                authListener?.onFailure(e.message!!)
            }
        }

    }

}
