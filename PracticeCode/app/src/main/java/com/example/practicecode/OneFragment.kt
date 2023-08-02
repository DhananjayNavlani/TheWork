package com.example.practicecode

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import java.lang.ClassCastException

class OneFragment : Fragment() {

    interface OnMessageClickListener{
        fun onMsgClick()
    }

    var msgListener:OnMessageClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_one, container, false)
        val showActivityMsg = view.findViewById<Button>(R.id.show_amessage)
        showActivityMsg.setOnClickListener {

            //one fragment can be used by multiple activities
            //so it is not correct to hardcode in such way
            //correct way is to use interface
//            activity?.let {
//                (it as FragmentInteractionActivity).showActivityMsg()
//            }

            msgListener?.onMsgClick()
        }
        return view
    }

    //onAttach is called when fragment it is attached to the activity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        //here context is the host activity which implements OnMsgClickListener so it is instance of that interface
        msgListener= context as? OnMessageClickListener
        msgListener ?: throw ClassCastException("$context must implement OnMessageClickListener")
    }

    override fun onDetach() {
        super.onDetach()
        msgListener = null
    }

    fun showFragmentMsg(){
        //we need to check its null or not
        activity?.let {
            Toast.makeText(it,"This msg is from fragment",Toast.LENGTH_LONG).show()
        }
    }
}