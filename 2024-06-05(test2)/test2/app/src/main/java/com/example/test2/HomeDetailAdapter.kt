package com.example.test2

import Product
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.databinding.HomedetailItemBinding

/**
 * 홈 상세 화면에서 제품 리스트를 표시하기 위한 RecyclerView 어댑터입니다.
 */
class HomeDetailAdapter(val productOrderLists: ArrayList<Product>) : RecyclerView.Adapter<HomeDetailAdapter.ViewHolder>() {

    /**
     * RecyclerView의 ViewHolder 클래스입니다.
     * 제품 정보를 바인딩합니다.
     */
    inner class ViewHolder(private val binding: HomedetailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * 제품 데이터를 뷰에 바인딩합니다.
         * @param product Product - 바인딩할 제품 객체
         */
        fun bind(product: Product) {
            binding.productName.text = product.productName
            binding.productQuantity.text = product.productQuantity
            binding.productPrice.textScaleX = product.productPrice?.toFloat() ?: 0.0f
        }
    }

    /**
     * 새로운 ViewHolder 객체를 생성합니다.
     * @param parent ViewGroup - 부모 뷰 그룹
     * @param viewType Int - 뷰 타입
     * @return ViewHolder - 생성된 ViewHolder 객체
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomedetailItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    /**
     * ViewHolder에 데이터를 바인딩합니다.
     * @param holder ViewHolder - 바인딩할 ViewHolder 객체
     * @param position Int - 리스트 내 위치
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(productOrderLists[position])
    }

    /**
     * 아이템 수를 반환합니다.
     * @return Int - 제품 리스트 크기
     */
    override fun getItemCount(): Int {
        return productOrderLists.size
    }
}
