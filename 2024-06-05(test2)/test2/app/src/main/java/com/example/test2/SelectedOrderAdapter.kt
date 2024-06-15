package com.example.test2

import Product
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.databinding.SelectedorderItemBinding

/**
 * 선택된 주문의 제품 리스트를 관리하는 어댑터 클래스입니다.
 */
class SelectedOrderAdapter(val productOrderLists: ArrayList<Product>) : RecyclerView.Adapter<SelectedOrderAdapter.ViewHolder>() {

    /**
     * ViewHolder 클래스: RecyclerView의 각 아이템을 정의합니다.
     */
    inner class ViewHolder(private val binding: SelectedorderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * 제품 데이터를 뷰에 바인딩합니다.
         * @param product Product - 표시할 제품 데이터
         */
        fun bind(product: Product) {
            binding.productName.text = product.productName // 제품 이름 설정
            binding.productQuantity.text = product.productQuantity // 제품 수량 설정
            binding.productPrice.textScaleX = product.productPrice?.toFloat() ?: 0.0f // 제품 가격 설정
        }
    }

    /**
     * 뷰 홀더를 생성합니다.
     * @param parent ViewGroup - 부모 뷰 그룹
     * @param viewType Int - 뷰 타입
     * @return ViewHolder - 생성된 뷰 홀더 객체
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SelectedorderItemBinding.inflate(
            LayoutInflater.from(parent.context), // 레이아웃 인플레이터 생성
            parent, // 부모 뷰 그룹
            false // 부모 레이아웃에 추가하지 않음
        )
        return ViewHolder(binding) // 뷰 홀더 반환
    }

    /**
     * 뷰 홀더에 데이터를 바인딩합니다.
     * @param holder ViewHolder - 바인딩할 뷰 홀더
     * @param position Int - 데이터 리스트의 위치 인덱스
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(productOrderLists[position]) // 위치에 따른 제품 데이터 바인딩
    }

    /**
     * 아이템의 총 개수를 반환합니다.
     * @return Int - 제품 리스트의 크기
     */
    override fun getItemCount(): Int {
        return productOrderLists.size
    }
}
