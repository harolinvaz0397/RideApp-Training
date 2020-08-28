package com.example.finalrideapp.view.home

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalrideapp.Adapters.ContactrvAdapter
import com.example.finalrideapp.R
import com.example.finalrideapp.model.DataModels.Data

class ContactsActivity: AppCompatActivity() {

    var contacts = ArrayList<Data>()
    lateinit var customAdapter: ContactrvAdapter
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.invite_peolple)


// action bar
        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#F99C56")))
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "Invite People"

        recyclerView = findViewById(R.id.contactrecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

//contact fetch
        contactList()
        customAdapter = ContactrvAdapter(contacts)
        recyclerView.adapter = customAdapter
        //searchview
        searchList()

    }




//contact seach name
    private fun searchList() {
        val searchName: EditText = findViewById(R.id.searchName)
        searchName.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }
//to create contact list
    private fun contactList() {
    val string: String = android.Manifest.permission.READ_CONTACTS
    if (ActivityCompat.checkSelfPermission(
            this, android.Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        val phones: Cursor? = contentResolver.query(
            CommonDataKinds.Phone.CONTENT_URI, null, null, null,
            CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )
        while (phones!!.moveToNext()) {
            //contact name
            val name: String =
                phones.getString(phones.getColumnIndex(CommonDataKinds.Phone.DISPLAY_NAME))
            //contact numer
            val phoneNumber: String =
                phones.getString(phones.getColumnIndex(CommonDataKinds.Phone.NUMBER))
            //contact photo
            val contactID = phones.getString(phones.getColumnIndex(CommonDataKinds.Phone.CONTACT_ID))

            contacts.add(Data(name, phoneNumber, contactID))
        }
        phones.close()
    }
    else {
        ActivityCompat.requestPermissions(this as Activity, arrayOf(string), 100)

    }

    }

    //search name
    private fun filter(text: String) {
        val filteredList = ArrayList<Data>()
        for (item in contacts)
        {
            if(item.name.toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item)
            }
        }
        customAdapter.filterList(filteredList)

    }

//action bar back button
    override fun onSupportNavigateUp(): Boolean {
         var contact: ArrayList<String> = ArrayList()
        var i= 0
        for(item in customAdapter.inviteList)
        {
            contact.add(item.name)
             i = i + 1
        }

        val intent: Intent = Intent()
        intent.putExtra("Data", contact)
        setResult(1000, intent)
        finish()

        onBackPressed()
        return true
    }
}




