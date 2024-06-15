package com.example.test2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.databinding.SelectedorderlistItemBinding

/**
 * 선택된 주문 목록을 보여주는 어댑터 클래스입니다.
 */
class SelectedOrderlistAdapter(val selectedOrderLists: ArrayList<Order>) : RecyclerView.Adapter<SelectedOrderlistAdapter.ViewHolder>() {
    // 아이템 클릭 리스너 인터페이스 정의
    private var itemClickListeners: OnItemClickListeners? = null

    interface OnItemClickListeners {
        // 아이템 클릭 시 호출되는 콜백 함수 정의
        fun onItemClick(binding: SelectedorderlistItemBinding, order: Order, position: Int)
    }

    /**
     * 아이템 클릭 리스너 설정 함수입니다.
     * @param itemClickListeners OnItemClickListeners - 클릭 리스너 객체
     */
    fun setOnItemClickListener(itemClickListeners: OnItemClickListeners) {
        this.itemClickListeners = itemClickListeners
    }

    /**
     * 뷰 홀더 클래스입니다.
     */
    inner class ViewHolder(private val binding: SelectedorderlistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            // 아이템 클릭 리스너 설정
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListeners?.onItemClick(binding, selectedOrderLists[position], position)
                }
            }
        }

        /**
         * 뷰 홀더에 데이터를 바인딩하는 함수입니다.
         * @param order Order - 주문 데이터
         */
        fun bind(order: Order) {
            binding.date.text = order.date.toString() // 주문 날짜 설정
            binding.name.text = order.supplier.supplierName // 공급자 이름 설정
            binding.location.text = order.supplier.supplierLocation // 공급자 위치 설정
            binding.salary.text = order.salary.toString() // 급여 설정
        }
    }

    /**
     * 뷰 홀더를 생성하는 함수입니다.
     * @param parent ViewGroup - 부모 뷰 그룹
     * @param viewType Int - 뷰 타입
     * @return ViewHolder - 생성된 뷰 홀더
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SelectedorderlistItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    /**
     * 뷰 홀더에 데이터를 바인딩하는 함수입니다.
     * @param holder ViewHolder - 뷰 홀더 객체
     * @param position Int - 아이템 위치
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(selectedOrderLists[position])
    }

    /**
     * 아이템의 개수를 반환하는 함수입니다.
     * @return Int - 아이템 개수
     */
    override fun getItemCount(): Int {
        return selectedOrderLists.size
    }
}
