package com.example.finalrideapp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalrideapp.R
import com.example.finalrideapp.viewholder.AccessoriesViewHolder

class AccessoriesAdapter (private val picture: IntArray, private val name: Array<String>, private val price:Array<String>, private val date: Array<String>, private val mContext: Context):
    RecyclerView.Adapter<AccessoriesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccessoriesViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.accessories_list,parent,false)
        return AccessoriesViewHolder(v,mContext)
    }

    override fun getItemCount(): Int {
        return picture.size
    }

    override fun onBindViewHolder(holder: AccessoriesViewHolder, position: Int) {
        holder.index(picture[position],name[position],price[position],date[position])
    }


}