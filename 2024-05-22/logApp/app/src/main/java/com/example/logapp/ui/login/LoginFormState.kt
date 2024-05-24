package com.example.logapp.ui.login

/**
 * Data validation state of the login form.
 */
data class LoginFormState(
    val usernameError: Int? = null,
    val userIdError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false,
    //val emailError: Int,
    //val phoneNumberError: Int
)