package com.example.finalrideapp

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.finalrideapp.BuildConfig
import com.example.finalrideapp.helpers.ResourceProvider
import timber.log.Timber

class MyAppilcation: Application() {
    //lateinit var resourceProvider: ResourceProvider


    override fun onCreate() {
        super.onCreate()

        //val _context = applicationContext
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Log.d("Message:", "Application created")
            //resourceProvider = ResourceProvider()
           // resourceProvider.appContext
        }


    }


}