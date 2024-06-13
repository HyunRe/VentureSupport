
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.venturesupport.RetrofitService
import com.example.venturesupport.WarehouseRegistrationActivity
import com.example.venturesupport.databinding.WarehouseBinding
import com.example.venturesupport.databinding.WarehouseItemBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 창고 관리 액티비티입니다.
 * 사용자의 창고 목록을 표시하고 창고를 추가하거나 삭제할 수 있습니다.
 */
class WarehouseActivity : AppCompatActivity() {
    // View binding 인스턴스
    private val binding: WarehouseBinding by lazy {
        WarehouseBinding.inflate(layoutInflater)
    }

    // 창고 어댑터
    private lateinit var warehouseAdapter: WarehouseAdapter

    // 창고 목록
    private var warehouseLists = ArrayList<Warehouse>()

    // 현재 사용자 정보
    private var user: User? = null
    private var currentUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 이전 액티비티에서 사용자 정보 가져오기
        user = intent.getParcelableExtra("user")
        currentUser = intent.getParcelableExtra("currentUser")

        // 사용자 ID 가져오기
        val id = user?.userId ?: currentUser?.userId ?: 0

        // 사용자의 창고 목록 불러오기
        getWarehousesByUserId(id)

        // 창고 추가 버튼 클릭 시
        binding.addWarehouseButton.setOnClickListener {
            val intent = Intent(this@WarehouseActivity, WarehouseRegistrationActivity::class.java).apply {
                putExtra("user", user)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        // 창고 어댑터 설정
        warehouseAdapter = WarehouseAdapter(warehouseLists)
        warehouseAdapter.setOnItemClickListener(object : WarehouseAdapter.OnItemClickListeners {
            override fun onItemLongClick(binding: WarehouseItemBinding, warehouse: Warehouse, position: Int) {
                // 삭제 확인 다이얼로그 표시
                val builder = AlertDialog.Builder(this@WarehouseActivity)
                builder.setTitle("삭제 확인")
                builder.setMessage("이 아이템을 삭제하시겠습니까?")

                builder.setPositiveButton("삭제") { dialog, which ->
                    // 아이템 삭제
                    warehouseAdapter.removeItem(position)
                    val warehouseId = warehouse.warehouseId ?: error("Warehouse ID가 null입니다.")
                    deleteWarehouse(warehouseId)
                }

                builder.setNegativeButton("취소") { dialog, which ->
                    dialog.dismiss()
                }

                val dialog = builder.create()
                dialog.show()
            }
        })

        // RecyclerView 설정
        binding.warehouseRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.warehouseRecyclerView.adapter = warehouseAdapter
        warehouseAdapter.notifyDataSetChanged()
    }

    // 사용자의 창고 목록을 서버에서 가져오는 함수
    private fun getWarehousesByUserId(userId: Int) {
        val call = RetrofitService.warehouseService.getWarehousesByUserId(userId)
        call.enqueue(object : Callback<List<Warehouse>> {
            override fun onResponse(call: Call<List<Warehouse>>, response: Response<List<Warehouse>>) {
                if (response.isSuccessful) {
                    val warehouses = response.body()
                    if (warehouses != null) {
                        warehouseLists.addAll(warehouses)
                        warehouseAdapter.updateData(warehouseLists)
                    } else {
                        Log.e("WarehouseActivity", "응답에서 창고 데이터를 찾을 수 없습니다.")
                    }
                } else {
                    Log.e("WarehouseActivity", "요청이 실패했습니다. 상태 코드: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Warehouse>>, t: Throwable) {
                Log.e("WarehouseActivity", "네트워크 요청 실패", t)
            }
        })
    }

    // 창고 삭제 함수
    private fun deleteWarehouse(warehouseId: Int) {
        val call = RetrofitService.warehouseService.deleteWarehouse(warehouseId)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("WarehouseActivity", "서버에서 아이템 삭제 성공")
                } else {
                    Log.e("WarehouseActivity", "서버에서 아이템 삭제 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("WarehouseActivity", "서버와의 통신 실패", t)
            }
        })
    }
}
