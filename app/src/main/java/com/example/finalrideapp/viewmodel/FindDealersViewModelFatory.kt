package com.example.finalrideapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalrideapp.model.repositories.NewUserRepository
import com.example.finalrideapp.viewmodels.FindDealersViewModel

@Suppress("UNCHECKED_CAST")
class FindDealersViewModelFatory(private val repository: NewUserRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FindDealersViewModel(repository) as T
    }

}