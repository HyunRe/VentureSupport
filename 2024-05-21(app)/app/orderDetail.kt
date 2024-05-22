import android.os.Bundle
    import androidx.appcompat.app.AppCompatActivity
    import kotlinx.android.synthetic.main.activity_order_detail.*

    class OrderDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_order_detail)

    // 주문 상세 정보를 받아와 화면에 표시
    val orderId = intent.getIntExtra("ORDER_ID", 0)
    val customerName = intent.getStringExtra("CUSTOMER_NAME")
    val address = intent.getStringExtra("ADDRESS")

    orderIdTextView.text = "Order ID: $orderId"
    customerNameTextView.text = "Customer Name: $customerName"
    addressTextView.text = "Address: $address"
    }
    }