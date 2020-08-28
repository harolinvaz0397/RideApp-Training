package com.example.finalrideapp.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalrideapp.Adapters.ToolKitAdapter
import com.example.finalrideapp.R
import com.example.finalrideapp.model.DataModels.ToolKitModel
import kotlinx.android.synthetic.main.activity_tool_kit.*

class ToolKit : AppCompatActivity() {

    val arrayList = ArrayList<ToolKitModel>()
    lateinit var myAdapter: ToolKitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tool_kit)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        arrayList.add(ToolKitModel("Onsite Minor Repairs",R.drawable.onsite))
        arrayList.add(ToolKitModel("Vehicle Accident", R.drawable.accident))
        arrayList.add(ToolKitModel("Battery Drain",R.drawable.battery))
        arrayList.add(ToolKitModel("Breakdown",R.drawable.onsite))
        arrayList.add(ToolKitModel("Loose Chain",R.drawable.chain))

        myAdapter= ToolKitAdapter(arrayList, this)
        recyclerview.layoutManager= LinearLayoutManager(this)
        recyclerview.adapter= myAdapter

        searchList()

    }

    //item search
    private fun searchList() {
        val searchName: EditText = findViewById(R.id.textInputEditText)
        searchName.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    //search name
    private fun filter(text: String) {
        val filteredList = ArrayList<ToolKitModel>()
        for (item in arrayList)
        {
            if(item.text.toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item)
            }
        }
        myAdapter.filterList(filteredList)

    }

}
