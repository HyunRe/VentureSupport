package com.example.venturesupport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SchedulerActivity : Fragment() {

    // 뷰모델을 초기화합니다. viewModels()는 Fragment에서 ViewModel을 관리하는 방법입니다.
    private val viewModel: SchedulerViewmodel by viewModels()

    // RecyclerView와 Adapter를 초기화합니다.
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SchedulerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 레이아웃 파일을 inflate하여 뷰를 생성합니다.
        val view = inflater.inflate(R.layout.scheduler, container, false)

        // RecyclerView를 찾고 LinearLayoutManager를 설정합니다.
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Adapter 초기화 및 RecyclerView에 설정합니다.
        adapter = SchedulerAdapter(emptyList())
        recyclerView.adapter = adapter

        // 뷰모델에서 데이터를 로드합니다.
        // 인텐트를 통해 사용자 ID를 가져옵니다.
        val userId = requireActivity().intent.getIntExtra("USER_ID", -1)
        if (userId == -1) {
            Toast.makeText(requireContext(), "유효하지 않은 사용자 ID", Toast.LENGTH_SHORT).show()
            requireActivity().finish()
            return view
        }
        viewModel.loadOrdersByUserId(userId)

        // 뷰모델의 orders LiveData를 관찰합니다. 데이터가 변경되면 어댑터를 업데이트합니다.
        viewModel.orders.observe(viewLifecycleOwner, { orders ->
            orders?.let {
                adapter = SchedulerAdapter(it)
                recyclerView.adapter = adapter
            }
        })

        return view
    }
}
