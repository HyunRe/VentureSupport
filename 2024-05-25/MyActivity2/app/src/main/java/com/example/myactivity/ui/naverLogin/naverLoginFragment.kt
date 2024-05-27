package com.example.myactivity.ui.naverLogin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myactivity.R
import com.example.myactivity.databinding.FragmentLoginBinding

class naverLoginFragment : Fragment() {
    private var loginViewModel: LoginViewModel? = null
    private var binding: FragmentLoginBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding!!.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProvider(this, naverLoginViewModelFactory())
            .get(LoginViewModel::class.java)
        val usernameEditText = binding!!.username
        val passwordEditText = binding!!.password
        val loginButton = binding!!.login
        val loadingProgressBar = binding!!.loading
        loginViewModel!!.getLoginFormState().observe(
            getViewLifecycleOwner(),
            Observer<naverLoginFormState?> { naverLoginFormState ->
                if (naverLoginFormState == null) {
                    return@Observer
                }
                loginButton.setEnabled(naverLoginFormState.isDataValid)
                if (naverLoginFormState.usernameError != null) {
                    usernameEditText.error = getString(naverLoginFormState.usernameError!!)
                }
                if (naverLoginFormState.passwordError != null) {
                    passwordEditText.error = getString(naverLoginFormState.passwordError!!)
                }
            })
        loginViewModel!!.getLoginResult()
            .observe(getViewLifecycleOwner(), Observer<naverLoginResult?> { naverLoginResult ->
                if (naverLoginResult == null) {
                    return@Observer
                }
                loadingProgressBar.visibility = View.GONE
                if (naverLoginResult.error != null) {
                    showLoginFailed(naverLoginResult.error)
                }
                if (naverLoginResult.success != null) {
                    updateUiWithUser(naverLoginResult.success)
                }
            })
        val afterTextChangedListener: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                loginViewModel!!.loginDataChanged(
                    usernameEditText.getText().toString(),
                    passwordEditText.getText().toString()
                )
            }
        }
        usernameEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel!!.login(
                    usernameEditText.getText().toString(),
                    passwordEditText.getText().toString()
                )
            }
            false
        }
        loginButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            loginViewModel!!.login(
                usernameEditText.getText().toString(),
                passwordEditText.getText().toString()
            )
        }
    }

    private fun updateUiWithUser(model: LoggedInaverUserView?) {
        val welcome = getString(R.string.welcome) + model!!.displayName
        // TODO : initiate successful logged in experience
        if (context != null && context!!.applicationContext != null) {
            Toast.makeText(context!!.applicationContext, welcome, Toast.LENGTH_LONG).show()
        }
    }

    private fun showLoginFailed(@StringRes errorString: Int?) {
        if (context != null && context!!.applicationContext != null) {
            Toast.makeText(
                context!!.applicationContext,
                errorString!!,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}