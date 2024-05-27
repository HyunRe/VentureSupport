package com.example.myactivity.ui.confirm.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myactivity.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var binding: FragmentSettingsBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val settingsViewModel = ViewModelProvider(this).get(
            SettingsViewModel::class.java
        )
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding!!.getRoot()
        val textView = binding!!.textSettings
        settingsViewModel.text.observe(getViewLifecycleOwner()) { text: CharSequence? ->
            textView.text = text
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}