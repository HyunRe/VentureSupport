package com.example.myactivity.ui.confirm.ui.transform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myactivity.R
import com.example.myactivity.databinding.FragmentTransformBinding
import com.example.myactivity.databinding.ItemTransformBinding
import java.util.Arrays

/**
 * Fragment that demonstrates a responsive layout pattern where the format of the content
 * transforms depending on the size of the screen. Specifically this Fragment shows items in
 * the [RecyclerView] using LinearLayoutManager in a small screen
 * and shows items using GridLayoutManager in a large screen.
 */
class TransformFragment : Fragment() {
    private var binding: FragmentTransformBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val transformViewModel: TransformViewModel =
            ViewModelProvider(this).get<TransformViewModel>(
                TransformViewModel::class.java
            )
        binding = FragmentTransformBinding.inflate(inflater, container, false)
        val root: View = binding!!.getRoot()
        val recyclerView: RecyclerView = binding!!.recyclerviewTransform
        val adapter: TransformAdapter = TransformAdapter()
        recyclerView.setAdapter(adapter)
        transformViewModel.texts.observe(getViewLifecycleOwner()) { list: List<T>? ->
            adapter.submitList(
                list
            )
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private class TransformAdapter :
        ListAdapter<String?, TransformViewHolder>(object : DiffUtil.ItemCallback<String?>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }) {
        private val drawables = Arrays.asList<Int>(
            R.drawable.avatar_1,
            R.drawable.avatar_2,
            R.drawable.avatar_3,
            R.drawable.avatar_4,
            R.drawable.avatar_5,
            R.drawable.avatar_6,
        )

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransformViewHolder {
            val binding = ItemTransformBinding.inflate(LayoutInflater.from(parent.getContext()))
            return TransformViewHolder(binding)
        }

        override fun onBindViewHolder(holder: TransformViewHolder, position: Int) {
            holder.textView.setText(getItem(position))
            holder.imageView.setImageDrawable(
                ResourcesCompat.getDrawable(
                    holder.imageView.resources,
                    drawables[position],
                    null
                )
            )
        }
    }

    private class TransformViewHolder(binding: ItemTransformBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {
        val imageView: ImageView
        val textView: TextView

        init {
            imageView = binding.imageViewItemTransform
            textView = binding.textViewItemTransform
        }
    }
}