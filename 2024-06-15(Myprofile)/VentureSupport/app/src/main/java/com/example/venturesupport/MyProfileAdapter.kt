package com.example.venturesupport

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.venturesupport.databinding.MyprofileItemBinding

class MyProfileAdapter(private var myprofileLists: List<User>) : RecyclerView.Adapter<MyProfileAdapter.ViewHolder>() {
    interface OnItemClickListeners {
        fun onItemClick(binding: MyprofileItemBinding, user: User, position: Int)
    }

    private var onItemClickListeners: OnItemClickListeners? = null

    fun setOnItemClickListener(onItemClickListeners: OnItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners
    }

    fun updateData(newList: List<User>) {
        myprofileLists = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: MyprofileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.nameTextView.text = user.username
            binding.phoneNumberTextView.text = user.userPhoneNumber

            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                binding.root.setOnClickListener {
                    onItemClickListeners?.onItemClick(binding, user, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MyprofileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(myprofileLists[position])
    }

    override fun getItemCount(): Int {
        return myprofileLists.size
    }
}
