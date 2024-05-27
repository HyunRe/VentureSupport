package com.example.myactivity.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myactivity.R

// RetailLoginFragment.kt
class RetailLoginFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Assuming there is a button to handle login
        view.findViewById<Button>(R.id.btn_retail).setOnClickListener {
            // Handle login logic
            val loginSuccessful = true // replace with actual login logic

            if (loginSuccessful) {
                findNavController().navigate(R.id.action_retail_login_to_order)
            }
        }
    }
}
