package com.example.venturesupport

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_scheduler.view.*

class SchedulerAdapter(private var orders: List<Order>) : RecyclerView.Adapter<SchedulerAdapter.OrderViewHolder>() {

    // 생성자에서 orders 리스트를 받아 초기화합니다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_scheduler, parent, false)
        return OrderViewHolder(view)
    }

    // 데이터 바인딩
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.bind(order)
    }

    override fun getItemCount(): Int = orders.size

    // 주문 데이터를 업데이트할 메서드
    fun updateOrders(newOrders: List<Order>) {
        orders = newOrders
        notifyDataSetChanged()
    }

    // ViewHolder 클래스 정의
    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(order: Order) {
            itemView.orderIdTextView.text = order.orderId.toString()
            itemView.dateTextView.text = order.date.toString()
            itemView.productTextView.text = order.product.name
            itemView.supplierTextView.text = order.supplier.name
            itemView.companyTextView.text = order.company.name
            itemView.salaryTextView.text = order.salary.toString()
            itemView.totalAmountTextView.text = order.totalAmount.toString()
        }
    }
}
