package com.example.finalrideapp.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.finalrideapp.R
import com.example.finalrideapp.model.DataModels.ToolKitModel
import com.example.finalrideapp.view.home.ToolKitPopUp
import kotlinx.android.synthetic.main.tool_kit_item.view.*

class ToolKitAdapter(private var arrayList: ArrayList<ToolKitModel>, val context: Context) :
    RecyclerView.Adapter<ToolKitAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindItems(model:ToolKitModel){
            itemView.text.text = model.text.toString()
            itemView.image.setImageResource(model.image)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.tool_kit_item,parent, false)
        return ViewHolder(v)

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrayList[position])

        holder.itemView.setOnClickListener{
            if (position==0){
                Toast.makeText(
                    context,
                    "you clicked over Onsite Minor Repairs ",
                    Toast.LENGTH_LONG
                ).show()

                val intent = Intent(context, ToolKitPopUp::class.java)
                context.startActivity(intent)
            }
            if (position==1){
                Toast.makeText(
                    context,
                    "you clicked over Vehicle Accident",
                    Toast.LENGTH_LONG
                ).show()


            }
            if (position==2){
                Toast.makeText(
                    context,
                    "you clicked over Battery Drain ",
                    Toast.LENGTH_LONG
                ).show()

            }
            if (position==3){
                Toast.makeText(
                    context,
                    "you clicked over Breakdown ",
                    Toast.LENGTH_LONG
                ).show()
            }
            if (position==4){
                Toast.makeText(
                    context,
                    "you clicked over Loose Chain ",
                    Toast.LENGTH_LONG
                ).show()
            }
        }



    }

    fun filterList(filteredList: ArrayList<ToolKitModel>) {
        arrayList = filteredList
        notifyDataSetChanged()

    }
}