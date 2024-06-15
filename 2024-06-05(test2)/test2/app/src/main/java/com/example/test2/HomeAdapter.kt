package com.example.test2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.databinding.HomeItemBinding

/**
 * 홈 화면의 주문 목록을 표시하는 RecyclerView 어댑터 클래스입니다.
 */
class HomeAdapter(private var orderLists: List<Order>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    /**
     * 아이템 클릭 리스너 인터페이스 정의
     */
    interface OnItemClickListeners {
        /**
         * 주문 항목 클릭 시 호출되는 메서드입니다.
         * @param binding HomeItemBinding - 뷰 바인딩 객체
         * @param order Order - 클릭된 주문 객체
         * @param position Int - 클릭된 아이템의 위치
         */
        fun onItemClick(binding: HomeItemBinding, order: Order, position: Int)
    }

    // 클릭 리스너 변수
    private var onItemClickListeners: OnItemClickListeners? = null

    /**
     * 클릭 리스너 설정 메서드입니다.
     * @param onItemClickListeners OnItemClickListeners - 클릭 리스너 객체
     */
    fun setOnItemClickListener(onItemClickListeners: OnItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners
    }

    /**
     * 데이터 리스트 업데이트 메서드입니다.
     * @param newList List<Order> - 새로 업데이트할 주문 리스트
     */
    fun updateData(newList: List<Order>) {
        orderLists = newList
        notifyDataSetChanged()
    }

    /**
     * ViewHolder 클래스 정의
     */
    inner class ViewHolder(private val binding: HomeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * 주문 데이터를 뷰에 바인딩하는 메서드입니다.
         * @param order Order - 바인딩할 주문 객체
         */
        fun bind(order: Order) {
            binding.date.text = order.date.toString() // 주문 날짜
            binding.name.text = order.supplier.supplierName // 공급업체 이름
            binding.location.text = order.supplier.supplierLocation // 공급업체 위치
            binding.salary.text = order.salary.toString() // 급여

            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                binding.root.setOnClickListener {
                    // 아이템 클릭 시 리스너 호출
                    onItemClickListeners?.onItemClick(binding, order, position)
                }
            }
        }
    }

    /**
     * 뷰홀더 생성 메서드입니다.
     * @param parent ViewGroup - 부모 뷰 그룹
     * @param viewType Int - 뷰 타입
     * @return ViewHolder - 생성된 뷰 홀더 객체
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    /**
     * 뷰홀더에 데이터를 바인딩하는 메서드입니다.
     * @param holder ViewHolder - 바인딩할 뷰 홀더
     * @param position Int - 아이템 위치
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(orderLists[position])
    }

    /**
     * 아이템 개수를 반환하는 메서드입니다.
     * @return Int - 아이템 개수
     */
    override fun getItemCount(): Int {
        return orderLists.size
    }
}
