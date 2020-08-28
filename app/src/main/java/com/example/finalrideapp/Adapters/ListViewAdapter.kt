package com.example.finalrideapp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.example.finalrideapp.R


class ListViewAdapter(val context: Context?, private val titleList: Array<String>, private val imagesList: Array<Int>): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val listView = parent?.findViewById(R.id.listView) as ListView
        val totalHeight: Int = listView.height
        val rowHeight = totalHeight / count
        var view = LayoutInflater.from(parent?.context).inflate(R.layout.listview_items, parent, false)
        val itemTitle = view.findViewById(R.id.tvTitle) as TextView
        val itemIcon = view.findViewById(R.id.listviewitemIcon) as ImageView
        view.minimumHeight = rowHeight
        itemTitle.text = titleList[position]
        itemIcon.setImageResource(imagesList[position])
        return view
    }

    override fun getItem(position: Int): Any {
        return 0
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return titleList.size
    }
}