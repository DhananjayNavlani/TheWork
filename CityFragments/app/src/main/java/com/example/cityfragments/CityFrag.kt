package com.example.cityfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
lateinit var cityData:List<City>

/**
 * A simple [Fragment] subclass.
 * Use the [CityFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class CityFrag : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        cityData = listOf<City>(
        City("New Delhi","India",3.2f),
        City("New York","America",2.2f),
        City("Paris","France",0.21f),
        City("Rome","Italy",0.28f),
        City("Amsterdam","Netherlands",0.09f)
        )
        val view = inflater.inflate(R.layout.fragment_city, container, false)
/*
        val rv = view.findViewById<RecyclerView>(R.id.cityrv)
        with(rv){
            layoutManager = LinearLayoutManager(activity)
            //when moving from one fragment to another w/o using navigation graph
*/
/*            adapter = CityAdapter(activity).apply {
                submitList(cityData.shuffled())
            }
*//*


            //using navigation graph
            adapter = CityAdapter{
                val bundle = Bundle()
                bundle.putString("cNameGraph" +
                        "",it.name)
//                findNavController().navigate(R.id.cityFrag,bundle)
                findNavController().navigate(R.id.action_cityFrag_to_detailFragment,bundle)
            }.apply {
                submitList(cityData.shuffled())
            }


            setHasFixedSize(true)
        }
*/
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val rv = view.findViewById<RecyclerView>(R.id.cityrv)
        with(rv){
            layoutManager = LinearLayoutManager(activity)
            //when moving from one fragment to another w/o using navigation graph
//            adapter = CityAdapter(activity).apply {
//                submitList(cityData.shuffled())
//            }


            //using navigation graph
            adapter = CityAdapter{
                val bundle = Bundle()
                bundle.putString("cNameGraph",it.name)
                findNavController().navigate(R.id.detailFragment,bundle)
//                findNavController().navigate(R.id.action_cityFrag_to_detailFragment,bundle)
            }.apply {
                submitList(cityData.shuffled())
            }

            setHasFixedSize(true)
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CityFrag.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CityFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}