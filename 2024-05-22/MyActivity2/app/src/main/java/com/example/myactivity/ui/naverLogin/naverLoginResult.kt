package com.example.myactivity.ui.naverLogin

/**
 * Authentication result : success (user details) or error message.
 */
internal class naverLoginResult {
    var success: LoggedInaverUserView? = null
        private set
    var error: Int? = null
        private set

    constructor(error: Int?) {
        this.error = error
    }

    constructor(success: LoggedInaverUserView?) {
        this.success = success
    }
}