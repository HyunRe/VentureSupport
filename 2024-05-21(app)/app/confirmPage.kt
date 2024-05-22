//일정에 추가할 원장을 확인/선택하는 화면
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feapp.R
import com.example.feapp.model.Order
import kotlinx.android.synthetic.main.activity_order_list.*

class confirmPage: AppCompatActivity() {

    private lateinit var orderAdapter: OrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_page)

        // 가상 데이터로 주문 목록 생성
        val orderList = generateDummyOrders()

        // RecyclerView 설정
        /*orderAdapter = OrderAdapter(orderList) { selectedOrder ->
            // 사용자가 선택한 주문 정보를 다음 페이지로 전달
            val intent = Intent(this, OrderDetailActivity::class.java).apply {
                putExtra("ORDER_ID", selectedOrder.orderId)
                putExtra("CUSTOMER_NAME", selectedOrder.customerName)
                putExtra("ADDRESS", selectedOrder.address)
                // 필요한 경우 다른 정보도 추가
            }
            startActivity(intent)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = orderAdapter*/
    }

    // 가상 데이터로 주문 목록 생성하는 함수
    private fun generateDummyOrders(): List<Order> {
        val orderList = mutableListOf<Order>()
        // 여기에 실제 서버에서 받아온 데이터를 가상으로 생성하거나, 서버와 연결하여 데이터를 받아오는 로직을 추가할 수 있음
        // 예시로 몇 개의 가상 주문을 생성
        for (i in 1..5) {
            val order = Order(
                orderId = i,
                customerName = "Customer $i",
                address = "Address $i",
                // 다른 필드에 대한 가상 데이터도 추가 가능
            )
            orderList.add(order)
        }
        return orderList
    }
}
