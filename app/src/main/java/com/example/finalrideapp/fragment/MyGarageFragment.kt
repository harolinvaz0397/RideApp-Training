package com.example.finalrideapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.finalrideapp.Adapters.ListViewAdapter
import com.example.finalrideapp.R
import com.example.finalrideapp.databinding.FragmentMyGarageBinding
import com.example.finalrideapp.model.DataModels.MyGarageItems
import com.example.finalrideapp.view.home.*
import com.example.finalrideapp.viewmodel.MyGarageViewModel
import kotlinx.android.synthetic.main.activity_home.*

import kotlinx.android.synthetic.main.fragment_my_garage.*

/**
 * A simple [Fragment] subclass.
 */
class MyGarageFragment : Fragment() {
    val myGarageItems = MyGarageItems()
    val titleList = myGarageItems.titleList
    val imagesList = myGarageItems.imagesList
    lateinit var myGarageViewModel: MyGarageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_my_garage, container, false)
        val binding: FragmentMyGarageBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_garage, container, false)
        activity?.actionBar?.hide()
        myGarageViewModel = ViewModelProvider(this).get(MyGarageViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = myGarageViewModel
         myGarageViewModel.getNumberOfDaysDue(inflater.context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //(activity as HomeActivity).navBar.menu.getItem(1).isChecked = true



        val customAdapter = ListViewAdapter(context, titleList, imagesList)
        listView.adapter = customAdapter



        //listView.setOnItemClickListener { parent, view_invoice, position, id ->
        listView.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> {
                    val intentBookService = Intent(activity, BookServiceActivity::class.java)
                    startActivity(intentBookService)
                }
                1 -> {
                    val intentServiceRecords = Intent(activity, ServiceRecords::class.java)
                    startActivity(intentServiceRecords)
                }
                2 -> {
                    val intentOwnerManualPage = Intent(activity, OwnerManualPage1::class.java)
                    startActivity(intentOwnerManualPage)
                }
                3 -> {
                    val nextToolKit = Intent(activity, ToolKit::class.java)
                    startActivity(nextToolKit)
                }
                4 -> {
                    val intentAccessories = Intent(activity, Accessories::class.java)
                    startActivity(intentAccessories)
                }
            }
        }
    }
}
