package com.example.logapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.logapp.databinding.FragmentHomeBinding
import androidx.viewpager.widget.ViewPager
import com.example.logapp.R
import androidx.fragment.app.FragmentPagerAdapter
import com.example.logapp.ui.login.RetailLoginFragment
import com.example.logapp.ui.login.WholesaleLoginFragment
//import com.example.logapp.ui.login.SignFragment
import com.google.android.material.tabs.TabLayout

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupViewPager()

        val textView: TextView = binding.title
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    private fun setupViewPager() {
        val adapter = object : FragmentPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            private val fragmentList = listOf(
                WholesaleLoginFragment(),
                RetailLoginFragment(),
                //SignFragment()
            )
            private val fragmentTitleList = listOf(
                getString(R.string.wholesale_login),
                getString(R.string.retail_login),
                getString(R.string.action_sign)
            )

            override fun getItem(position: Int): Fragment {
                return fragmentList[position]
            }

            override fun getCount(): Int {
                return fragmentList.size
            }

            /*
    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }*/
            override fun getPageTitle(position: Int): CharSequence? {
                return fragmentTitleList[position]
            }
        }
            binding.viewPager.adapter = adapter
            binding.tabs.setupWithViewPager(binding.viewPager)
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}