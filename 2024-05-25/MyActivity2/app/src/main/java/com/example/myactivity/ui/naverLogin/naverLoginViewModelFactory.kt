package com.example.myactivity.ui.naverLogin

import androidx.lifecycle.ViewModel
import com.example.myactivity.data.LoginDataSource
import com.example.myactivity.data.repository.LoginRepository.Companion.getInstance

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class naverLoginViewModelFactory : Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
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