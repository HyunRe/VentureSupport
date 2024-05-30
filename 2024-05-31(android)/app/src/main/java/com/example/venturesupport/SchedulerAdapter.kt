package com.example.venturesupport

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SchedulerAdapter(private val orders: List<Order>) : RecyclerView.Adapter<SchedulerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userId: TextView = itemView.findViewById(R.id.userId)
        val username: TextView = itemView.findViewById(R.id.username)
        val email: TextView = itemView.findViewById(R.id.email)
        val userPhoneNumber: TextView = itemView.findViewById(R.id.userPhoneNumber)
        val lat: TextView = itemView.findViewById(R.id.lat)
        val lng: TextView = itemView.findViewById(R.id.lng)
        val userPassword: TextView = itemView.findViewById(R.id.userPassword)
        val inventoryQuantity: TextView = itemView.findViewById(R.id.inventoryQuantity)
        // 필요한 정보들 추가
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orders[position]
        holder.userId.text = "Order ${position + 1}"
        holder.username.text = order.username
        holder.email.text = order.email
        holder.userPhoneNumber.text = order.userPhoneNumber
        holder.lat.text = order.lat.toString()
        holder.lng.text = order.lng.toString()
        holder.userPassword.text = order.userPassword
        holder.inventoryQuantity.text = order.inventoryQuantity
        // 필요한 정보들 설정
    }

    override fun getItemCount(): Int {
        return orders.size
    }
}
