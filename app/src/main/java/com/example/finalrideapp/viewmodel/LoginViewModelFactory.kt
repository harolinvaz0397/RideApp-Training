package com.example.finalrideapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalrideapp.model.repositories.NewUserRepository
import com.example.finalrideapp.model.repositories.UserRepository

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(private val repository: NewUserRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }

}