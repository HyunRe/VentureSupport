//HomeFragment.kt
//초기화면: 원장관리(소매업자용) / 로그인(도매업자용) 선택 구현 + 로고 포맘 필
package com.example.myactivity.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myactivity.R
import com.example.myactivity.databinding.FragmentHomeBinding

//import com.example.myactivity.ui.login.LoginPageActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val textView: TextView = binding.textHome
        /*homeViewModel.text.observe(viewLifecycleOwner) {
                textView.text = it
        }*/
        view.findViewById<Button>(R.id.btn_wholesale).setOnClickListener {
            findNavController().navigate(R.id.action_home_to_wholesale_login)
        }

        view.findViewById<Button>(R.id.btn_retail).setOnClickListener {
            findNavController().navigate(R.id.action_home_to_retail_login)
        }

        view.findViewById<Button>(R.id.btn_sign).setOnClickListener {
            findNavController().navigate(R.id.action_home_to_sign)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


/*
inflater: LayoutInflater,

container: ViewGroup?,
savedInstanceState: Bundle?
): View {
    val homeViewModel =
        ViewModelProvider(this).get(HomeViewModel::class.java)

    _binding = FragmentHomeBinding.inflate(inflater, container, false)
    val root: View = binding.root
    */