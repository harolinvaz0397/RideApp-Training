package com.example.finalrideapp.Adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.finalrideapp.R
import com.example.finalrideapp.helpers.DealerViewHolder
import com.example.finalrideapp.model.DataModels.Dealers
import com.example.finalrideapp.view.home.DealerProfileActivity
import java.util.*
import kotlin.collections.ArrayList

class DealersRecycleViewAdapter(private var data: ArrayList<Dealers>): RecyclerView.Adapter<DealerViewHolder>(), Filterable {
    var filteredList: ArrayList<Dealers> = data
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealerViewHolder {
        context = parent.context
        Log.d("View Type", viewType.toString())
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dealers_recyclerview_item, parent, false)
        return DealerViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        //return filteredList.size
        return if (filteredList == null) 0 else filteredList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: DealerViewHolder, position: Int) {
        val dealer = filteredList[position]
        holder.dealerName.text = dealer.name +", "+ dealer.city
        holder.dealerInfo.text = "Authorised Service Center"
        holder.dealerContact.text = dealer.phone
        // holder.distance.text = dealer.distanceInKMs
        holder.dealerRating.rating = dealer.rating
        holder.itemView.setOnClickListener {
            val intentDealerProfile = Intent(context, DealerProfileActivity::class.java)
            intentDealerProfile.putExtra("Dealer Id", dealer.id)
            context.startActivity(intentDealerProfile)
        }


    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filteredList = data
                } else {
                    val resultList = ArrayList<Dealers>()
                    for (row in data) {
                        val dealerNameString: String = row.name +" "+row.city
                        if (dealerNameString.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    filteredList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as ArrayList<Dealers>
                notifyDataSetChanged()
            }

        }
    }

}