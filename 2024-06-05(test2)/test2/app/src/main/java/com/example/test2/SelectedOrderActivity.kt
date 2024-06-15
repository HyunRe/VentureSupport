package com.example.test2

import Product
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test2.databinding.CreateorderItemBinding
import com.example.test2.databinding.SelectedorderBinding

/**
 * 선택된 주문의 상세 정보를 표시하는 액티비티 클래스입니다.
 */
class SelectedOrderActivity: AppCompatActivity() {
    // 바인딩 변수: 레이아웃과 연결하여 UI 요소에 접근할 수 있도록 합니다.
    private val binding: SelectedorderBinding by lazy {
        SelectedorderBinding.inflate(layoutInflater)
    }

    // 주문에 포함된 제품 리스트를 저장합니다.
    private lateinit var selectedOrderAdapter: SelectedOrderAdapter
    private var productOrderLists = ArrayList<Product>()

    // 선택된 주문 객체를 저장합니다.
    private var selectedOrder: Order? = null

    /**
     * 액티비티가 생성될 때 호출됩니다.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 인텐트를 통해 전달된 선택된 주문 정보를 가져옵니다.
        @Suppress("DEPRECATION")
        selectedOrder = intent.getParcelableExtra("selectedOrder")

        // 뷰를 업데이트하여 선택된 주문 정보를 표시합니다.
        binding.date.text = selectedOrder?.date.toString() // 날짜 설정
        binding.SupplierName.text = selectedOrder?.supplier?.supplierName // 공급업체 이름 설정
        binding.SupplierPhoneNumber.text = selectedOrder?.supplier?.supplierPhoneNumber // 공급업체 전화번호 설정
        binding.SupplierLocation.text = selectedOrder?.supplier?.supplierLocation // 공급업체 위치 설정
        binding.Salary.text = selectedOrder?.salary.toString() // 급여 설정
        binding.TotalAmount.text = selectedOrder?.totalAmount.toString() // 총 금액 설정

        // 선택된 주문의 제품을 리스트에 추가합니다.
        productOrderLists.add(selectedOrder?.product!!)

        // 어댑터 초기화 및 설정
        selectedOrderAdapter = SelectedOrderAdapter(productOrderLists)
        binding.productRecyclerView.layoutManager = LinearLayoutManager(this) // 레이아웃 매니저 설정
        binding.productRecyclerView.adapter = selectedOrderAdapter // 어댑터 설정
        selectedOrderAdapter.notifyDataSetChanged() // 어댑터 데이터 변경 알림
    }
}
