package com.example.cityfragments

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class CityAdapter(val fragactivity:FragmentActivity?) : ListAdapter<City, CityAdapter.ViewHolder>(DiffCallBack()) {
    lateinit var listener:(City)->Unit
    constructor(listener: (City)->Unit) : this(null){
        this.listener = listener
    }
    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
            val cityName = view.findViewById<TextView>(R.id.cityName)
        init{
            itemView.setOnClickListener {
//                Toast.makeText(view.context,getItem(adapterPosition),Toast.LENGTH_LONG).show()
                val orientation: Int = Resources.getSystem().configuration.orientation
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    val f = DetailFragment().apply {
                        arguments = bundleOf("city_name" to getItem(adapterPosition).name)
                    }
                    fragactivity?.supportFragmentManager?.beginTransaction()?.add(R.id.city_landscape_container,f)?.commit()
                } else {
 /*
                    //Without using navigation graph, only with fragments attached to their activites
                    val intent = Intent(view.context,CityDetailActivity::class.java).apply {
                        putExtra("city_name",getItem(adapterPosition).name)
                    }
                    view.context.startActivity(intent)
*/
                    //using navigation graph
//                    val bundle = Bundle()
//                    bundle.putString("cNameGraph",cityName.text.toString())
//                    fragactivity?.supportFragmentManager?.findFragmentByTag("City Tag")?.findNavController()?.navigate(R.id.cityFrag,bundle) ?: Toast.makeText(fragactivity?.applicationContext,"Got null at findnavcontroller",Toast.LENGTH_LONG).show()
//                    fragactivity?.supportFragmentManager?.findFragmentByTag("City Tag")?.findNavController()?.navigate(R.id.action_cityFrag_to_detailFragment,bundle)

                    listener.invoke(getItem(adapterPosition))
                }

            }
        }
        fun bind(city: City){
            cityName.text = city.name
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<City>(){


        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}
