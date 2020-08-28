package com.example.finalrideapp.helpers

import android.content.Context
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalrideapp.R

class DealerViewHolder(itemView: View, context: Context): RecyclerView.ViewHolder(itemView) {
    val dealerName: TextView
    val dealerInfo: TextView
    val dealerContact: TextView
    val dealerRating: RatingBar
    //val distance: TextView

    init {
        dealerName = itemView.findViewById<TextView>(R.id.tvDealerName)
        dealerInfo = itemView.findViewById(R.id.tvDealerInfo) as TextView
        dealerContact = itemView.findViewById(R.id.tvDealerContact) as TextView
        dealerRating = itemView.findViewById(R.id.rbDealerRatings) as RatingBar
       // distance = itemView.findViewById(R.id.tvDistance) as TextView

        /* itemView.setOnClickListener {
             val intentDealerProfile = Intent(context, DealerProfileActivity::class.java)
             intentDealerProfile.putExtra("Dealer Name", it.findViewById<TextView>(R.id.tvDealerName).text as String)
             //Log.d("Dealer Name:", it.findViewById<TextView>(R.id.tvDealerName).text as String)
             context.startActivity(intentDealerProfile)

         }*/
    }
}
