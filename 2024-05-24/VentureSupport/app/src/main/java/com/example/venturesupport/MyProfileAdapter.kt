package com.example.venturesupport

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.venturesupport.databinding.MyprofileItemBinding

class MyProfileAdapter(private val myprofileLists: List<User>) : RecyclerView.Adapter<MyProfileAdapter.ProfileViewHolder>() {
    interface OnItemClickListeners {
        fun onItemClick(binding: MyprofileItemBinding, user: User, position: Int)
    }

    private var onItemClickListeners: OnItemClickListeners? = null

    fun setOnItemClickListener(onItemClickListeners: OnItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners
    }

    inner class ProfileViewHolder(private val binding: MyprofileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.nameTextView.text = user.user_name
            binding.phoneNumberTextView.text = user.phone

            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                binding.root.setOnClickListener {
                    onItemClickListeners?.onItemClick(binding, user, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val binding = MyprofileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind(myprofileLists[position])
    }

    override fun getItemCount(): Int {
        return myprofileLists.size
    }
}