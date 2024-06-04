package com.example.venturesupport

import User
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.venturesupport.databinding.MyprofileItemBinding

class MyProfileAdapter(private var myprofileLists: List<User>) : RecyclerView.Adapter<MyProfileAdapter.ViewHolder>() {
    // 아이템 클릭 리스너 인터페이스
    interface OnItemClickListeners {
        fun onItemClick(binding: MyprofileItemBinding, user: User, position: Int)
    }

    // 아이템 클릭 리스너 변수
    private var onItemClickListeners: OnItemClickListeners? = null

    // 아이템 클릭 리스너 설정 메서드
    fun setOnItemClickListener(onItemClickListeners: OnItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners
    }

    // 데이터 업데이트 메서드
    fun updateData(newList: List<User>) {
        myprofileLists = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: MyprofileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // ViewHolder에 데이터 바인딩하는 메서드
        fun bind(user: User) {
            binding.nameTextView.text = user.username
            binding.phoneNumberTextView.text = user.phone

            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                // 아이템 클릭 시 리스너 호출
                binding.root.setOnClickListener {
                    onItemClickListeners?.onItemClick(binding, user, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // ViewHolder 생성
        val binding = MyprofileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // ViewHolder에 데이터 바인딩
        holder.bind(myprofileLists[position])
    }

    override fun getItemCount(): Int {
        // 아이템 개수 반환
        return myprofileLists.size
    }
}
