package com.example.logapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.logapp.data.LoginDataSource
import com.example.logapp.data.repository.LoginRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            // Use the singleton instance of LoginRepository
            val repository = LoginRepository.getInstance(LoginDataSource())
            return LoginViewModel(loginRepository = repository!!) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
