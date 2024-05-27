package com.example.myactivity.ui.order

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myactivity.data.model.Order
import com.example.myactivity.databinding.FragmentOrderBinding
import com.example.myactivity.placeholder.PlaceholderContent.PlaceholderItem

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyorderRecyclerViewAdapter(private val mValues: List<PlaceholderItem>, private var orders: List<Order>,private val onItemClick: (Order) -> Unit) :
    RecyclerView.Adapter<MyorderRecyclerViewAdapter.OrderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return OrderViewHolder(
            private var orders: List<Order>, private val onItemClick: (Order) -> Unit
            FragmentOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mIdView.text = mValues[position].id
        holder.mContentView.text = mValues[position].content
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    //inner class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    inner class OrderViewHolder(binding: FragmentOrderBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {
        val mIdView: TextView
        val mContentView: TextView
        var mItem: PlaceholderItem? = null

        init {
            mIdView = binding.itemNumber
            mContentView = binding.content
        }

        override fun toString(): String {
            return super.toString() + " '" + mContentView.getText() + "'"
        }
    }
}

/*package com.example.feapp.ui.confirm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.feapp.R
import com.example.feapp.data.model.Order

class OrderAdapter(
    private var orders: List<Order>,
    private val onItemClick: (Order) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val customerNameTextView: TextView = view.findViewById(R.id.customerName)
        private val addressTextView: TextView = view.findViewById(R.id.address)

        fun bind(order: Order) {
            customerNameTextView.text = order.customerName
            addressTextView.text = order.address
            itemView.setOnClickListener {
                onItemClick(order)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount() = orders.size

    fun updateOrders(newOrders: List<Order>) {
        orders = newOrders
        notifyDataSetChanged()
    }
}
*/