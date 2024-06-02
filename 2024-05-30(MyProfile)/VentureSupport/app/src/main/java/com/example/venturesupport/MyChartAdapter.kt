package com.example.venturesupport

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.venturesupport.databinding.MychartItemBinding

// MyChartAdapter: MyChart 객체를 리사이클러뷰에 바인딩하는 어댑터 클래스
class MyChartAdapter(private val chartLists: List<MyChart>) : RecyclerView.Adapter<MyChartAdapter.ViewHolder>() {
    // 아이템 클릭 이벤트 리스너 인터페이스 정의
    interface OnItemClickListeners {
        fun onItemClick(binding: MychartItemBinding, myChart: MyChart, position: Int)
    }

    // 아이템 클릭 이벤트 리스너 변수
    private var onItemClickListeners: OnItemClickListeners? = null

    // 아이템 클릭 이벤트 리스너 설정 함수
    fun setOnItemClickListener(onItemClickListeners: OnItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners
    }

    // ViewHolder 클래스: 리사이클러뷰 아이템을 위한 뷰 홀더 클래스
    inner class ViewHolder(private val binding: MychartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // 아이템 바인딩 함수
        fun bind(myChart: MyChart) {
            // 이미지 및 텍스트 설정
            binding.mychartImage.setImageResource(myChart.imageResId)
            binding.mychartText.text = myChart.text

            // 아이템 클릭 이벤트 처리
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                binding.root.setOnClickListener {
                    onItemClickListeners?.onItemClick(binding, myChart, position)
                }
            }
        }
    }

    // onCreateViewHolder: 뷰홀더 생성 및 레이아웃 설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MychartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // onBindViewHolder: 뷰홀더에 데이터 바인딩
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chartLists[position])
    }

    // getItemCount: 아이템 개수 반환
    override fun getItemCount(): Int {
        return chartLists.size
    }
}
