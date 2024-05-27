//원장을 작성하고 관리함
package com.example.myactivity.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myactivity.R
import com.example.myactivity.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class OrderFragment
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
    : Fragment() {
    // TODO: Customize parameters
    private var mColumnCount = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mColumnCount = requireArguments().getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            val context = view.getContext()
            val recyclerView = view
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(LinearLayoutManager(context))
            } else {
                recyclerView.setLayoutManager(GridLayoutManager(context, mColumnCount))
            }
            recyclerView.setAdapter(MyorderRecyclerViewAdapter(PlaceholderContent.ITEMS))
        }
        return view
    }

    companion object {
        // TODO: Customize parameter argument names
        private const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @Suppress("unused")
        fun newInstance(columnCount: Int): OrderFragment {
            val fragment = OrderFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.setArguments(args)
            return fragment
        }
    }
}