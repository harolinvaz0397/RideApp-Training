package com.example.finalrideapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalrideapp.model.repositories.NewUserRepository

@Suppress("UNCHECKED_CAST")
class OtpVerifyViewModelFactory(private val repository: NewUserRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OtpVerifyViewModel(repository) as T
    }

}