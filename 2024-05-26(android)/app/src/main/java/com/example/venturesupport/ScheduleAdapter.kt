package com.example.venturesupport

//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//
//class ScheduleAdapter(private val scheduleList: List<Schedule>) :
//    RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false)
//        return ScheduleViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
//        val schedule = scheduleList[position]
//        holder.clientNameTextView.text = schedule.clientName
//        holder.productNameTextView.text = schedule.productName
//        holder.productQuantityTextView.text = schedule.productQuantity
//        holder.productPriceTextView.text = schedule.productPrice
//        holder.clientLocationTextView.text = schedule.clientLocation
//    }
//
//    override fun getItemCount(): Int {
//        return scheduleList.size
//    }
//
//    class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val clientNameTextView: TextView = itemView.findViewById(R.id.clientNameTextView)
//        val productNameTextView: TextView = itemView.findViewById(R.id.productNameTextView)
//        val productQuantityTextView: TextView = itemView.findViewById(R.id.productQuantityTextView)
//        val productPriceTextView: TextView = itemView.findViewById(R.id.productPriceTextView)
//        val clientLocationTextView: TextView = itemView.findViewById(R.id.clientLocationTextView)
//    }
//}

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScheduleAdapter(private val scheduleList: ArrayList<Schedule>) : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val schedule = scheduleList[position]
        holder.titleTextView.text = schedule.title
        holder.locationTextView.text = schedule.location
        holder.dateTextView.text = schedule.date
        holder.productTextView.text = schedule.product
        holder.amountTextView.text = schedule.amount
    }

    override fun getItemCount(): Int {
        return scheduleList.size
    }

    inner class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.text_title)
        val locationTextView: TextView = itemView.findViewById(R.id.text_location)
        val dateTextView: TextView = itemView.findViewById(R.id.text_date)
        val productTextView: TextView = itemView.findViewById(R.id.text_product)
        val amountTextView: TextView = itemView.findViewById(R.id.text_amount)
    }
}
