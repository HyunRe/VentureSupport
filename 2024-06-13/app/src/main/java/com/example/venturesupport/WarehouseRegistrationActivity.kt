package com.example.venturesupport

import User
import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.example.venturesupport.databinding.WarehouseRegistrationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WarehouseRegistrationActivity: AppCompatActivity() {
    // 뷰 바인딩 인스턴스 생성
    private val binding: WarehouseRegistrationBinding by lazy {
        WarehouseRegistrationBinding.inflate(layoutInflater)
    }
    private lateinit var user: User // 사용자 객체 늦은 초기화

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        user = intent.getParcelableExtra("user") ?: error("User 정보가 없습니다.")

        // 창고 생성 버튼 클릭 시 동작 설정
        binding.checkButton.setOnClickListener {
            // 사용자가 입력한 창고 이름과 위치 정보를 가져오기
            val warehouseName = binding.warehouseNameEdit.text.toString()
            val warehouseLocation = binding.warehouseLocationEdit.text.toString()

            // Warehouse 객체 생성
            val warehouse = Warehouse(
                warehouseId = null, // 창고 ID는 서버에서 생성되므로 null로 설정
                user = user, // 사용자 객체 전달
                warehouseName = warehouseName, // 사용자가 입력한 창고 이름
                warehouseLocation = warehouseLocation // 사용자가 입력한 창고 위치
            )

            // 서버에 창고 등록 요청
            createWarehouse(warehouse)

            // WarehouseActivity로 돌아가는 Intent 생성
            val intent = Intent(this@WarehouseRegistrationActivity, WarehouseActivity::class.java).apply {
                putExtra("currentUser", user) // 현재 사용자 정보 전달
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent) // WarehouseActivity 시작
            finish() // 현재 액티비티 종료
        }
    }

    // 서버에 새로운 창고 생성 요청
    private fun createWarehouse(warehouse: Warehouse) {
        // RetrofitService를 통해 createWarehouse API 호출
        val call = RetrofitService.warehouseService.createWarehouse(warehouse)
        call.enqueue(object : Callback<Warehouse> {
            override fun onResponse(call: Call<Warehouse>, response: Response<Warehouse>) {
                if (response.isSuccessful) { // 요청 성공 시
                    val createdWarehouse = response.body() // 생성된 창고 객체 가져오기
                    println("Warehouse 생성 성공: $createdWarehouse")
                } else { // 요청 실패 시
                    println("Warehouse 생성 실패: ${response.code()}") // 에러 코드 출력
                }
            }

            override fun onFailure(call: Call<Warehouse>, t: Throwable) {
                println("네트워크 요청 실패: ${t.message}") // 네트워크 요청 실패 시 에러 메시지 출력
            }
        })
    }
}
