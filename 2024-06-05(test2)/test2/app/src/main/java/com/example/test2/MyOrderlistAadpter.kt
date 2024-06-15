package com.example.test2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.databinding.MyorderlistItemBinding
/**
 * 사용자 주문 목록을 표시하기 위한 RecyclerView.Adapter 클래스입니다.
 * 각 아이템의 클릭 및 롱 클릭 이벤트 처리를 지원합니다.
 */
class MyOrderlistAadpter(val myOrderLists: ArrayList<Order>) : RecyclerView.Adapter<MyOrderlistAadpter.ViewHolder>() {

    // 아이템 클릭 리스너 인터페이스 정의
    private var itemClickListeners: OnItemClickListeners? = null

    interface OnItemClickListeners {
        fun onItemClick(binding: MyorderlistItemBinding, order: Order, position: Int)
        fun onItemLongClick(binding: MyorderlistItemBinding, order: Order, position: Int)
    }

    /**
     * 아이템 클릭 리스너 설정 메서드
     */
    fun setOnItemClickListener(itemClickListeners: OnItemClickListeners) {
        this.itemClickListeners = itemClickListeners
    }

    /**
     * 리스트 아이템 삭제 메서드
     */
    fun removeItem(position: Int) {
        myOrderLists.removeAt(position) // 아이템 제거
        notifyItemRemoved(position) // 아이템 삭제 알림
        notifyItemRangeChanged(position, myOrderLists.size) // 남은 아이템 갱신 알림
    }

    /**
     * RecyclerView.ViewHolder 클래스 정의
     */
    inner class ViewHolder(private val binding: MyorderlistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            // 아이템 클릭 리스너 설정
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListeners?.onItemClick(binding, myOrderLists[position], position)
                }
            }

            // 아이템 롱 클릭 리스너 설정
            binding.root.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListeners?.onItemLongClick(binding, myOrderLists[position], position)
                }
                true
            }
        }

        /**
         * 아이템 뷰 바인딩 메서드
         */
        fun bind(order: Order) {
            binding.date.text = order.date.toString() // 주문 날짜
            binding.name.text = order.supplier.supplierName // 공급업체 이름
            binding.location.text = order.supplier.supplierLocation // 공급업체 위치
            binding.salary.text = order.salary.toString() // 급여
        }
    }

    /**
     * 뷰 홀더 생성 메서드
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MyorderlistItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    /**
     * 뷰 홀더 바인딩 메서드
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(myOrderLists[position]) // 아이템 데이터 바인딩
    }

    /**
     * 아이템 개수 반환 메서드
     */
    override fun getItemCount(): Int {
        return myOrderLists.size
    }
}
