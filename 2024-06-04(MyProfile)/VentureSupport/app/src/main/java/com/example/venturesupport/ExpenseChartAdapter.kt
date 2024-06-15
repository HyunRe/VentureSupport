package com.example.venturesupport

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.venturesupport.databinding.ExpensechartItemBinding
import com.github.mikephil.charting.data.PieEntry

/**
 * 지출 차트 데이터를 관리하는 RecyclerView 어댑터 클래스입니다.
 * @param dataList List<PieEntry> - PieChart 데이터를 담고 있는 리스트
 */
class ExpenseChartAdapter(private val dataList: List<PieEntry>) : RecyclerView.Adapter<ExpenseChartAdapter.ViewHolder>() {

    /**
     * ViewHolder 클래스: 각 아이템의 뷰를 관리합니다.
     */
    inner class ViewHolder(private val binding: ExpensechartItemBinding) : RecyclerView.ViewHolder(binding.root) {

        /**
         * PieEntry 데이터를 뷰에 바인딩합니다.
         * @param pieEntry PieEntry - 차트 데이터 항목
         */
        fun bind(pieEntry: PieEntry) {
            binding.percentText.text = pieEntry.value.toString() // 퍼센트 텍스트 설정
            binding.breakdownText.text = pieEntry.label // 라벨 텍스트 설정
            binding.progressBar.progress = pieEntry.value.toInt() // 프로그레스바 진행도 설정

            // 프로그레스바의 색상을 pieEntry 값에 따라 설정
            val progressBarDrawable = when {
                pieEntry.value >= 80 -> R.drawable.red_progress // 값이 80 이상일 때
                pieEntry.value >= 60 -> R.drawable.orange_progress // 값이 60 이상일 때
                pieEntry.value >= 40 -> R.drawable.yellow_progress // 값이 40 이상일 때
                pieEntry.value >= 20 -> R.drawable.green_progress // 값이 20 이상일 때
                else -> R.drawable.blue_progress // 그 외의 경우
            }
            binding.progressBar.progressDrawable = binding.root.context.getDrawable(progressBarDrawable)
        }
    }

    /**
     * ViewHolder 객체를 생성합니다.
     * @param parent ViewGroup - 부모 뷰 그룹
     * @param viewType Int - 뷰 타입
     * @return ViewHolder - 생성된 ViewHolder 객체
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ExpensechartItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    /**
     * ViewHolder에 데이터를 바인딩합니다.
     * @param holder ViewHolder - 바인딩할 ViewHolder
     * @param position Int - 아이템 위치
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    /**
     * 리스트의 아이템 개수를 반환합니다.
     * @return Int - 아이템 개수
     */
    override fun getItemCount(): Int {
        return dataList.size
    }
}
