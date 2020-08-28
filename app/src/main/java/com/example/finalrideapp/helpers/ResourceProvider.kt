package com.example.finalrideapp.helpers

import android.app.Application
import android.content.Context

class ResourceProvider: Application() {
    var appContext: Context
    init {
        appContext = this.applicationContext
    }
}