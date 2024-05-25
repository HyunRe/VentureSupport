package com.example.myactivity.ui.naverLogin

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myactivity.R
import com.example.myactivity.data.Result
import com.example.myactivity.data.repository.LoginRepository
import com.example.myactivity.data.model.LoggedInUser

class LoginViewModel internal constructor(private val loginRepository: LoginRepository) :
    ViewModel() {
    private val loginFormState = MutableLiveData<naverLoginFormState>()
    private val loginResult = MutableLiveData<naverLoginResult>()
    fun getLoginFormState(): LiveData<naverLoginFormState> {
        return loginFormState
    }

    fun getLoginResult(): LiveData<naverLoginResult> {
        return loginResult
    }

    fun login(username: String?, password: String?) {
        // can be launched in a separate asynchronous job
        val result = loginRepository.login(username, password)
        if (result is Result.Success<*>) {
            val data = (result as Result.Success<LoggedInUser?>).data
            loginResult.setValue(naverLoginResult(LoggedInaverUserView(data!!.displayName)))
        } else {
            loginResult.setValue(naverLoginResult(R.string.login_failed))
        }
    }

    fun loginDataChanged(username: String?, password: String?) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(naverLoginFormState(R.string.invalid_username, null))
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(naverLoginFormState(null, R.string.invalid_password))
        } else {
            loginFormState.setValue(naverLoginFormState(true))
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String?): Boolean {
        if (username == null) {
            return false
        }
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            !username.trim { it <= ' ' }.isEmpty()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String?): Boolean {
        return password != null && password.trim { it <= ' ' }.length > 5
    }
}