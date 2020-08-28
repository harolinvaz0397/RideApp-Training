package com.example.finalrideapp.Adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalrideapp.R
import com.example.finalrideapp.model.DataModels.Data


class ContactrvAdapter(var contact: ArrayList<Data>): RecyclerView.Adapter<ContactrvAdapter.ViewHolder>() {
    var inviteList: ArrayList<Data> = ArrayList<Data>()
  inner  class ViewHolder(itemView: View, val textView: TextView = itemView.findViewById(R.id.contactName)): RecyclerView.ViewHolder(itemView)
    {
        val select: CheckBox  = itemView.findViewById(R.id.select)

        init {
            select.setOnClickListener(addinvitelist())
        }

         fun  addinvitelist(): (View) -> Unit = {
             layoutPosition.also { currentPosition ->  inviteList.add(contact.get(currentPosition)) }
         }


        fun bindItems(data: Data) {
            textView.text = data.name

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.contactlist_layout, parent, false)
        return ViewHolder(v)

    }

    override fun getItemCount(): Int {
       return contact.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(contact[position])
       // holder.select.setOnClickListener {

       // }


    }

    fun filterList(filteredList: ArrayList<Data>) {
        contact = filteredList
        notifyDataSetChanged()

    }


}