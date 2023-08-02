package com.example.shoppingcart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.shoppingcart.databinding.FragmentCheckoutBinding
import com.example.shoppingcart.placeholder.PlaceholderContent

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CheckoutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CheckoutFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var quantity = 1
    private var _binding:FragmentCheckoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel:CheckoutViewModel
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
        _binding = FragmentCheckoutBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        var product: PlaceholderContent.PlaceholderItem? = null
//        val id = arguments?.getInt("ID")
//        id?.let {
//            product = PlaceholderContent.ITEMS.find { it.id == id }
//        }

        //using safe-args
//        arguments?.let {
//            val args = CheckoutFragmentArgs.fromBundle(it)
//            product = PlaceholderContent.ITEMS.find { args.id == it.id }
//        }

        val id = CheckoutFragmentArgs.fromBundle(requireArguments()).id

        val viewModelFactory = CheckoutViewModelFactory(id,1)
        viewModel = ViewModelProvider(this,viewModelFactory).get(CheckoutViewModel::class.java)

        //using viewModel w/o LiveData
//        setData(viewModel.product)

        //using viewModel with LiveData
        //remember to pass viewLifecycleOwner instead of "this" because of onDestroyView called
        //when going to back stack, if we pass "this" data updates will be sent when fragment is in backstack which
        //can crash the app as fragment's view is destroyed and livedata is being observed.
        viewModel.product.observe(viewLifecycleOwner, Observer {
            setData(it)
        })

        viewModel.qty.observe(viewLifecycleOwner, Observer {
            binding.qty.text = "Qty: $it"
        })


        //using transformations
        viewModel.trimDesc.observe(viewLifecycleOwner, Observer {
            binding.tvShortDesp.text = it
        })

        binding.applyDiscount.setOnClickListener {
            viewModel.applyDiscount(10f)
//            setData(viewModel.product.value)
        }

        viewModel.price.observe(viewLifecycleOwner, Observer {
            binding.cPrice.text = "Price: Rs $it"
            binding.orderTotal.text = "Order Total: Rs $it"
            })

        viewModel.discountApplied.observe(viewLifecycleOwner) {
            binding.applyDiscount.isEnabled = !it
        }

        binding.btnPlus.setOnClickListener {
            //w/o viewmodel
//            quantity++

            //using viewmodel
//            viewModel.addQty(1)
//            binding.qty.text = "Qty: ${viewModel.qty}"

            //using viewmodel with livedata
            viewModel.addQty(1)

        }

        binding.btnMinus.setOnClickListener {
            //w/o viewmodel
//            if(quantity>1){
//                quantity--
//                binding.qty.text = "Qty: $quantity"
//            }else{
//                Toast.makeText(activity,"Quantity can't be less than 1",Toast.LENGTH_LONG).show()
//            }


            //using viewModel
//            if(viewModel.qty>1){
//                viewModel.reduceQty(1)
//                binding.qty.text = "Qty: ${viewModel.qty}"
//            }else{
//                Toast.makeText(activity,"Quantity can't be less than 1",Toast.LENGTH_LONG).show()
//            }

            //using viewmodel with livedata
            viewModel.reduceQty(1)
        }
    }

    private fun setData(product: PlaceholderContent.PlaceholderItem?) {
        product?.let {
            with(it){
                binding.cImage.setImageResource(imageId)
                binding.cPrice.text = "Price: Rs $price"
                binding.orderTotal.text = "Order Total: Rs $price"
//                binding.qty.text = "Qty: $quantity"
//                binding.qty.text = "Qty: ${viewModel.qty}"
                binding.cName.text = name
//                binding.applyDiscount.isEnabled = !discountApplied

                binding.btnCheckout.setOnClickListener {
//                    val bundle = Bundle()
//                    bundle.putString("NAME",name)
//                    findNavController().navigate(R.id.action_checkoutFragment_to_thanksFragment,bundle)

                    //using safe-args
                    findNavController().navigate(CheckoutFragmentDirections.actionCheckoutFragmentToThanksFragment(name))
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
         * @return A new instance of fragment CheckoutFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CheckoutFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}