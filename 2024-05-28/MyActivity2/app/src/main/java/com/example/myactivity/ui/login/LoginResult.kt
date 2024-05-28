package com.example.myactivity.ui.login

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResult {
    internal var success: LoggedInUserView? = null
        private set
    var error: Int? = null
        private set

    constructor(error: Int?) {
        this.error = error
    }

    constructor(success: LoggedInUserView?) {
        this.success = success
    }
}