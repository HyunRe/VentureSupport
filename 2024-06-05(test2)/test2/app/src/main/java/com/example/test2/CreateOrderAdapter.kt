package com.example.test2

import Product
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.databinding.CreateorderItemBinding

/**
 * 주문 생성 화면의 리사이클러뷰 어댑터입니다.
 * @param productLists ArrayList<Product> - 초기 상품 리스트
 */
class CreateOrderAdapter(private var productLists: ArrayList<Product>) : RecyclerView.Adapter<CreateOrderAdapter.ViewHolder>() {

    /**
     * 아이템 클릭 리스너 인터페이스입니다.
     */
    interface OnItemClickListeners {
        /**
         * 아이템을 롱 클릭했을 때 호출됩니다.
         * @param binding CreateorderItemBinding - 아이템의 뷰 바인딩
         * @param product Product - 롱 클릭된 상품
         * @param position Int - 아이템 위치
         */
        fun onItemLongClick(binding: CreateorderItemBinding, product: Product, position: Int)
    }

    private var onItemClickListeners: OnItemClickListeners? = null

    /**
     * 아이템 클릭 리스너를 설정합니다.
     * @param onItemClickListeners OnItemClickListeners - 클릭 리스너 객체
     */
    fun setOnItemClickListener(onItemClickListeners: OnItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners
    }

    /**
     * 데이터 리스트를 업데이트합니다.
     * @param newList ArrayList<Product> - 새로운 상품 리스트
     */
    fun updateData(newList: ArrayList<Product>) {
        productLists = newList
        notifyDataSetChanged()
    }

    /**
     * 특정 위치의 아이템을 삭제합니다.
     * @param position Int - 삭제할 아이템의 위치
     */
    fun removeItem(position: Int) {
        productLists.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, productLists.size)
    }

    /**
     * 뷰 홀더 클래스입니다. 리사이클러뷰 아이템의 뷰를 관리합니다.
     * @param binding CreateorderItemBinding - 아이템의 뷰 바인딩
     */
    inner class ViewHolder(private val binding: CreateorderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * 상품 데이터를 뷰에 바인딩합니다.
         * @param product Product - 바인딩할 상품 데이터
         */
        fun bind(product: Product) {
            binding.productName.text = product.productName
            binding.productQuantity.text = product.productQuantity
            binding.productPrice.textScaleX = product.productPrice?.toFloat() ?: 0.0f

            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                binding.root.setOnLongClickListener {
                    onItemClickListeners?.onItemLongClick(binding, product, position)
                    true
                }
            }
        }
    }

    /**
     * 새로운 뷰 홀더를 생성합니다.
     * @param parent ViewGroup - 부모 뷰 그룹
     * @param viewType Int - 뷰 타입
     * @return ViewHolder - 생성된 뷰 홀더
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CreateorderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    /**
     * 뷰 홀더에 데이터를 바인딩합니다.
     * @param holder ViewHolder - 바인딩할 뷰 홀더
     * @param position Int - 아이템 위치
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(productLists[position])
    }

    /**
     * 아이템의 총 개수를 반환합니다.
     * @return Int - 아이템의 총 개수
     */
    override fun getItemCount(): Int {
        return productLists.size
    }
}
