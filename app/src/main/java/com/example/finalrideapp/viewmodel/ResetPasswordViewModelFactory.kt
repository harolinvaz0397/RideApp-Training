package com.example.finalrideapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalrideapp.model.preferences.PreferenceProvider
import com.example.finalrideapp.model.repositories.NewUserRepository
import com.example.finalrideapp.model.repositories.UserRepository

@Suppress("UNCHECKED_CAST")
class ResetPasswordViewModelFactory(private val repository: NewUserRepository, private  val prefs: PreferenceProvider) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ResetPasswordViewModel(repository, prefs) as T
    }

}