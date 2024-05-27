package com.example.myactivity.ui.naverLogin

/**
 * Data validation state of the login form.
 */
internal class NaverLoginFormState {
    var usernameError: Int?
        private set
    var passwordError: Int?
        private set
    var isDataValid: Boolean
        private set

    constructor(usernameError: Int?, passwordError: Int?) {
        this.usernameError = usernameError
        this.passwordError = passwordError
        isDataValid = false
    }

    constructor(isDataValid: Boolean) {
        usernameError = null
        passwordError = null
        this.isDataValid = isDataValid
    }
}