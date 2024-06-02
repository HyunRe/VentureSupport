import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.venturesupport.databinding.WarehouseItemBinding

/**
 * 창고 목록을 표시하기 위한 어댑터 클래스입니다.
 */
class WarehouseAdapter(private var warehouseLists: ArrayList<Warehouse>) : RecyclerView.Adapter<WarehouseAdapter.ViewHolder>() {
    interface OnItemClickListeners {
        /**
         * 창고 아이템을 길게 클릭할 때 호출되는 콜백 메서드입니다.
         * @param binding WarehouseItemBinding - 아이템의 바인딩 객체
         * @param warehouse Warehouse - 클릭된 창고 아이템
         * @param position Int - 아이템의 위치
         */
        fun onItemLongClick(binding: WarehouseItemBinding, warehouse: Warehouse, position: Int)
    }

    private var onItemClickListeners: OnItemClickListeners? = null

    /**
     * 아이템 클릭 리스너를 설정합니다.
     * @param onItemClickListeners OnItemClickListeners - 아이템 클릭 리스너
     */
    fun setOnItemClickListener(onItemClickListeners: OnItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners
    }

    /**
     * 데이터를 업데이트하고 RecyclerView를 새로고칩니다.
     * @param newList ArrayList<Warehouse> - 새로운 창고 목록
     */
    fun updateData(newList: ArrayList<Warehouse>) {
        warehouseLists = newList
        notifyDataSetChanged()
    }

    /**
     * 특정 위치의 아이템을 삭제하고 RecyclerView를 갱신합니다.
     * @param position Int - 삭제할 아이템의 위치
     */
    fun removeItem(position: Int) {
        warehouseLists.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, warehouseLists.size)
    }

    inner class ViewHolder(private val binding: WarehouseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * 아이템 바인딩을 설정하고 클릭 리스너를 등록합니다.
         * @param warehouse Warehouse - 표시할 창고 정보
         */
        fun bind(warehouse: Warehouse) {
            binding.warehouseName.text = warehouse.warehouseName
            binding.warehouseLocation.text = warehouse.warehouseLocation

            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                binding.root.setOnLongClickListener {
                    onItemClickListeners?.onItemLongClick(binding, warehouse, position)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = WarehouseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(warehouseLists[position])
    }

    override fun getItemCount(): Int {
        return warehouseLists.size
    }
}
