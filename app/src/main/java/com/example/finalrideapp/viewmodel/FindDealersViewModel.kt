package com.example.finalrideapp.viewmodels

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalrideapp.Adapters.DealersRecycleViewAdapter
import com.example.finalrideapp.helpers.replaceAndCapitalize
import com.example.finalrideapp.model.DataModels.Dealers
import com.example.finalrideapp.model.repositories.NewUserRepository
import com.example.finalrideapp.util.ApiException
import com.example.finalrideapp.util.Coroutines
import com.example.finalrideapp.util.NoInternetException
import com.example.finalrideapp.view.auth.AuthListener
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlin.collections.ArrayList

class FindDealersViewModel(private val repository: NewUserRepository): ViewModel() {
    val dealersList = ArrayList<Dealers>()
    lateinit var adapter: DealersRecycleViewAdapter
    var authListener: AuthListener? = null

    fun initRecyclerView(context: Context, rvDealersList: RecyclerView, searchViewDealers: androidx.appcompat.widget.SearchView) {
        searchViewDealers.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return  false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.length!! > 2) {
                    //adapter.filter.filter(newText)
                    var city: String = newText
                    Log.d("Message", city)
                    Coroutines.main {
                        try {
                                val dealersResponse = repository.funGetDealers(city)

                                dealersResponse.data?.let {
                                    val dealersCount = it.get("count").toString().toInt()
                                    val dealers: JsonArray = it.get("dealers") as JsonArray
                                    if (dealersCount > 0) {
                                        dealersList.clear()
                                        rvDealersList.visibility = View.VISIBLE
                                        for(i in 0 until dealersCount) {
                                            //val dealer = response.body()?.data?.get("dealers")
                                            val dealer = dealers[i] as JsonObject
                                            //Log.d("Message:", dealer.get("name").toString().replace("\"", "")+" "+dealer.get("city").toString().replace("\"", "")+" "+dealer.get("ratings"))
                                            dealersList.add(Dealers(dealer.get("_id").toString().replace("\"", ""),
                                                dealer.get("name").toString().replaceAndCapitalize(),
                                                dealer.get("city").toString().replaceAndCapitalize(),
                                                dealer.get("phone").toString().replace("\"", ""),
                                                dealer.get("ratings").asFloat))
                                        }
                                        rvDealersList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                                        adapter = DealersRecycleViewAdapter(dealersList)
                                        //adapter = DealersRecycleViewAdapter(findDealersViewmodel.dealers.value!!)
                                        rvDealersList.adapter = adapter
                                        //adapter.filter.filter("Udupi")// later change the constraint to current location value
                                    }
                                    else {
                                        Log.d("Message:","nothing fetchedd")
                                        rvDealersList.visibility = View.GONE
                                        dealersList.clear()
                                    }

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
                /* else {
                     rvDealersList.visibility = View.GONE
                     dealersList.clear()
                 }*/

                return false
            }

        })


    }

}