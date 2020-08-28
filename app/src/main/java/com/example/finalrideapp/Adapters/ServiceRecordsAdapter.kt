package com.example.finalrideapp.Adapters

import android.app.Service
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.finalrideapp.R
import com.example.finalrideapp.helpers.ServiceRecordsViewHolder
import com.example.finalrideapp.model.DataModels.Dealers
import com.example.finalrideapp.model.DataModels.ServiceRecordModel
import com.example.finalrideapp.view.home.BookingServiceDetails
import com.example.finalrideapp.view.home.DealerProfileActivity
import kotlinx.android.synthetic.main.service_records_items.view.*
import java.util.*
import kotlin.collections.ArrayList

class ServiceRecordsAdapter: RecyclerView.Adapter<ServiceRecordsViewHolder>(), Filterable {
    var serviceRecordsList = ArrayList<ServiceRecordModel>()
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceRecordsViewHolder {
        context = parent.context
        return ServiceRecordsViewHolder(LayoutInflater.from(context).inflate(R.layout.service_records_items, parent, false))

    }

    override fun getItemCount(): Int {
        return  serviceRecordsList.size
    }

    override fun onBindViewHolder(holder: ServiceRecordsViewHolder, position: Int) {
            val service = serviceRecordsList[position]
            holder.onBind(service)
            holder.itemView.setOnClickListener {
                val intentServiceDetails = Intent(context, BookingServiceDetails::class.java)
                intentServiceDetails.putExtra("Service Id", service.id)
                context.startActivity(intentServiceDetails)

            }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    //filteredList = data
                } else {
                    val resultList = ArrayList<ServiceRecordModel>()
                    for (row in serviceRecordsList) {
                        val serviceTypeString: String = row.serviceType
                        if (serviceTypeString.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(
                                Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    serviceRecordsList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = serviceRecordsList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                serviceRecordsList = results?.values as ArrayList<ServiceRecordModel>
                notifyDataSetChanged()
            }

        }
    }


    /*class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date = itemView.findViewById<TextView>(R.id.text1)
        val day = itemView.findViewById<TextView>(R.id.text2)
        val service = itemView.findViewById<TextView>(R.id.service)
        init {


            itemView.relative.setOnClickListener {
                nextActivity(it)


            }
        }

        private fun nextActivity(it: View) {
            val intent: Intent = Intent(itemView.context, BookingServiceDetails::class.java)
            it.context.startActivity(intent)
        }
    }*/
}