package com.example.myactivity.ui.home
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myactivity.R
import com.example.myactivity.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAuth.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_authFragment)
        }

        binding.btnOrder.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_orderFragment)
        }

        binding.btnPay.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_paymentFragment)
        }

        binding.btnPro.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_productFragment)
        }

        binding.btnInfo.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_productInformationFragment)
        }

        binding.btnCar.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_vehicleFragment)
        }

        binding.btnWare.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_warehouseFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
