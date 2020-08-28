package com.example.finalrideapp.view.auth

interface AuthListener {
    fun onStarted()
    fun inputValidation(field: Int, message: String)
    fun onSuccess()
    fun onFailure(message:String)
}