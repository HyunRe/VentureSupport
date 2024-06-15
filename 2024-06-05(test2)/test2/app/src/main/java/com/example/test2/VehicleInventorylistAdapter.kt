package com.example.test2

import Product
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.databinding.VehicleinventorylistItemBinding

/**
 * 차량 재고 리스트를 관리하는 RecyclerView 어댑터 클래스입니다.
 */
class VehicleInventorylistAdapter(val productLists: ArrayList<Product>) : RecyclerView.Adapter<VehicleInventorylistAdapter.ViewHolder>() {

    /**
     * ViewHolder 클래스: 각 아이템의 뷰를 관리합니다.
     */
    inner class ViewHolder(private val binding: VehicleinventorylistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * 제품 데이터를 뷰에 바인딩합니다.
         * @param product Product - 리스트의 각 제품
         */
        fun bind(product: Product) {
            binding.productName.text = product.productName // 제품 이름 설정
            binding.productQuantity.text = product.productQuantity // 제품 수량 설정
        }
    }

    /**
     * ViewHolder 객체를 생성합니다.
     * @param parent ViewGroup - 부모 뷰 그룹
     * @param viewType Int - 뷰 타입
     * @return ViewHolder - 생성된 ViewHolder 객체
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = VehicleinventorylistItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    /**
     * ViewHolder에 데이터를 바인딩합니다.
     * @param holder ViewHolder - 바인딩할 ViewHolder
     * @param position Int - 아이템 위치
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(productLists[position])
    }

    /**
     * 리스트의 아이템 개수를 반환합니다.
     * @return Int - 아이템 개수
     */
    override fun getItemCount(): Int {
        return productLists.size
    }
}
