package com.example.finalrideapp.viewholder

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalrideapp.R
import com.bumptech.glide.Glide

class AccessoriesViewHolder (itemView: View, private val mContext: Context): RecyclerView.ViewHolder(itemView){
    private val iview : ImageView
    private val itview : TextView
    private val itview2 : TextView
    private val tview: TextView

    init{
        iview = itemView.findViewById<View>(R.id.iview) as ImageView
        itview = itemView.findViewById<View>(R.id.itview) as TextView
        itview2=itemView.findViewById<View>(R.id.itview2) as TextView
        tview=itemView.findViewById<View>(R.id.tview) as TextView

    }
    fun index(item: Int, s: String, s1: String, s2:String){
        Glide.with(mContext).load(item).into(iview)
        itview.text = s
        itview2.text=s1
        tview.text=s2
    }

}