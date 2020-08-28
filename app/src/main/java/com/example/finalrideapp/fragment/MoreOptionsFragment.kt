package com.example.finalrideapp.fragment

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.example.finalrideapp.Adapters.ListViewAdapter

import com.example.finalrideapp.R
import com.example.finalrideapp.databinding.FragmentMoreOptionsBinding
import com.example.finalrideapp.model.DataModels.MoreOptionsItems
import com.example.finalrideapp.model.preferences.PreferenceProvider
import com.example.finalrideapp.view.auth.Login

class MoreOptionsFragment : Fragment() {

    val moreOptionsItems = MoreOptionsItems()
    val titleList = moreOptionsItems.titleList
    val imagesList = moreOptionsItems.imagesList

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentMoreOptionsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_more_options, container, false)

        val customAdapter = ListViewAdapter(context, titleList, imagesList)
        binding.listView.adapter = customAdapter
        binding.listView.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> {
                    // do something
                }
                1 -> {
                    // do something
                }
                2 -> {
                    //do something
                }
                3 -> {
                    // do something
                }
                4 -> {
                    if (container != null) {
                        val alertDialog = AlertDialog.Builder(container.context)
                        alertDialog.setTitle(getString(R.string.log_out))
                        alertDialog.setMessage(getString(R.string.confirm_logout))
                        alertDialog.setPositiveButton(getString(R.string.yes)) { dialogInterface: DialogInterface, i: Int ->
                            logout(container.context)
                        }
                        alertDialog.setNegativeButton(getString(R.string.no)) { dialogInterface: DialogInterface, i: Int ->
                            // do nothing
                        }
                        alertDialog.show()
                    }
                }
            }

        }



        return binding.root
    }

    /*override fun onViewCreated(view_invoice: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view_invoice, savedInstanceState)
        val toolbar = view_invoice.findViewById(R.id.tbMore) as Toolbar
        activity?.setActionBar(toolbar)

        activity?.se.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }*/


    fun logout(context: Context) {
        val preferenceProvider = PreferenceProvider(context)
        preferenceProvider.clear()
        val intentBookService = Intent(activity, Login::class.java)
        startActivity(intentBookService)

    }

}