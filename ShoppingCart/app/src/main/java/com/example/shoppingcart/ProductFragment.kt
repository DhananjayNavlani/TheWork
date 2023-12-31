package com.example.shoppingcart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.shoppingcart.databinding.FragmentItemListBinding
import com.example.shoppingcart.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class ProductFragment : Fragment() {

    private var columnCount = 1
    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        val viewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        Log.d("ProductFrag","Before f & s : "+Thread.currentThread().name)
        viewModel.fetchAndSaveData()
        Log.d("ProductFrag","After f & s : "+Thread.currentThread().name)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemListBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyProductRecyclerViewAdapter{

//                    val bundle = Bundle()
//                    bundle.putInt("ID",it.id)
//                    findNavController().navigate(R.id.detail,bundle)
//                    findNavController().navigate(R.id.action_productFragment_to_detailFragment,bundle)

                    //Using type safety. i.e safe args
                    findNavController().navigate(ProductFragmentDirections.actionProductFragmentToDetailFragment(it.id))
                }
                (adapter as MyProductRecyclerViewAdapter).values = PlaceholderContent.ITEMS
            }
        }

    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}