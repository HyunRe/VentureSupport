class LoginFormState {
    var usernameError: Int?
        private set
    var emailError: Int?
        private set
    var passwordError: Int?
        private set
    var userTypeError: Int?
        private set
    var networkError: Int?
        private set
    var isDataValid: Boolean
        private set

    constructor(usernameError: Int?, emailError: Int?, passwordError: Int?, userTypeError: Int?, networkError: Int?) {
        this.usernameError = usernameError
        this.emailError = emailError
        this.passwordError = passwordError
        this.userTypeError = userTypeError
        this.networkError = networkError
        isDataValid = false
    }

    constructor(isDataValid: Boolean) {
        usernameError = null
        emailError = null
        passwordError = null
        userTypeError = null
        networkError = null
        this.isDataValid = isDataValid
    }
}
