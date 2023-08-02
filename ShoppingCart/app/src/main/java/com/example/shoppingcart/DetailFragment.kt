package com.example.shoppingcart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.shoppingcart.databinding.FragmentDetailBinding
import com.example.shoppingcart.placeholder.PlaceholderContent
import com.example.shoppingcart.placeholder.PlaceholderContent.PlaceholderItem

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var product: PlaceholderItem? = null
//        val id = arguments?.getInt("ID")
//        id?.let {
//            product = PlaceholderContent.ITEMS.find { it.id == id }
//        }

        arguments?.let {
            val args = DetailFragmentArgs.fromBundle(it)
            product = PlaceholderContent.ITEMS.find { it.id == args.id }
        }

        product?.let {
            with(it){
                binding.include.name.text = name
                binding.include.price.text = "Price: Rs $price"
                binding.include.pImage.setImageResource(imageId)
                binding.include.shortDescription.text = shortDescription
                binding.longDescription.text = longDescription
                binding.btnSave.visibility = View.INVISIBLE
                binding.txtInputLayout.visibility = View.INVISIBLE
                binding.btnBuy.setOnClickListener {
//                    val bundle = Bundle()
//                    bundle.putInt("ID",this.id)
//                    findNavController().navigate(R.id.action_detailFragment_to_checkoutFragment,bundle)

                    findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToCheckoutFragment(this.id))
                }
                binding.btnRate.setOnClickListener {
                    binding.btnSave.visibility = View.VISIBLE
                    binding.txtInputLayout.visibility = View.VISIBLE
                }
                binding.btnSave.setOnClickListener {
                    val value = binding.rateEditText.text.toString().toInt()
                    if(value < 1 || value > 5){
                        Toast.makeText(activity,"Rate between 1 and 5 "+binding.rateEditText.isTextInputLayoutFocusedRectEnabled,Toast.LENGTH_LONG).show()
                    }else{
                        this.rating = value
                        binding.include.pRate.text = "Rating: $value"

                        binding.btnSave.visibility = View.INVISIBLE
                        binding.txtInputLayout.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}