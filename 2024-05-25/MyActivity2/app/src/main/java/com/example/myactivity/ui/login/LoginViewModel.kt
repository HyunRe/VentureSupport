package com.example.myactivity.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myactivity.R
import com.example.myactivity.data.Result
import com.example.myactivity.data.model.LoggedInUser
import com.example.myactivity.data.network.ApiRepository
import com.example.myactivity.data.repository.LoginRepository

class LoginViewModel internal constructor(private val loginRepository: LoginRepository) :
    ViewModel() {
    private val ApiRepository = ApiRepository()
    val loginFormState = MutableLiveData<LoginFormState>()
    val loginResult = MutableLiveData<LoginResult>()
    fun getLoginFormState(): LiveData<LoginFormState> {
        return loginFormState
    }

    fun getLoginResult(): LiveData<LoginResult> {
        return loginResult
    }

    fun login(emailOrUsername: String?, password: String?): Result<Any?> {
        // 이메일 혹은 유저네임이 빈 문자열이거나 null이라면 로그인 실패
        if (emailOrUsername.isNullOrEmpty()) {
            return Result.Error(Exception("Email or username cannot be empty"))
        }

        val isEmail = emailOrUsername.contains('@') && emailOrUsername.contains('.')
        val result: Result<Any?>

        if (isEmail) {
            // 이메일 형식인 경우
            result = dataSource.loginWithEmail(emailOrUsername, password)
        } else {
            // 유저네임 형식인 경우
            result = dataSource.loginWithUsername(emailOrUsername, password)
        }

        if (result is Result.Success<*>) {
            setLoggedInUser((result as Result.Success<LoggedInUser?>).data)
        }
        return result
    }



    fun loginDataChanged(username: String?, password: String?) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(LoginFormState(R.string.invalid_username, null))
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(LoginFormState(null, R.string.invalid_password))
        } else {
            loginFormState.setValue(LoginFormState(true))
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