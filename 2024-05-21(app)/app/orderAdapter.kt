import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.feapp.R
import com.example.feapp.model.Order
import kotlinx.android.synthetic.main.item_order.view.*

class OrderAdapter(
    private val orderList: List<Order>,
    private val onItemClick: (Order) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val currentOrder = orderList[position]
        holder.bind(currentOrder)
    }

    override fun getItemCount() = orderList.size

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(order: Order) {
            itemView.apply {
                customerNameTextView.text = order.customerName
                addressTextView.text = order.address

                setOnClickListener { onItemClick(order) }
            }
        }
    }
}
