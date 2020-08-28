package com.example.finalrideapp.helpers

import android.content.Context
import android.view.View
import androidx.appcompat.view.menu.MenuView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.finalrideapp.R
import com.example.finalrideapp.model.DataModels.ServiceRecordModel
import kotlinx.android.synthetic.main.service_records_items.view.*

class ServiceRecordsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val context : Context = itemView.context
    fun onBind(serviceRecordModel: ServiceRecordModel) {
        itemView.tvServiceDate.text = serviceRecordModel.date
        itemView.tvServiceMonthAndYear.text = serviceRecordModel.month+"\n"+serviceRecordModel.year
        itemView.tvServiceType.text = serviceRecordModel.serviceType
        itemView.rbServiceRatings.rating = serviceRecordModel.serviceRatings
        if (serviceRecordModel.isOlder) {
            itemView.tvServiceStatus.text = context.getString(R.string.past)
            itemView.tvServiceStatus.background = ResourcesCompat.getDrawable(context.resources, R.drawable.green_rounded_textview, null)
            itemView.tvServiceMonthAndYear.setTextColor(context.resources.getColor(R.color.green))
            itemView.tvServiceDate.setTextColor(context.resources.getColor(R.color.green))
        }
    }

}
