package com.example.myactivity.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myactivity.data.LoginDataSource
import com.example.myactivity.data.repository.LoginRepository.Companion.getInstance

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            LoginViewModel(
                getInstance(
                    LoginDataSource()
                )!!
            ) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}