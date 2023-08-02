package com.example.shoppingcart

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.example.shoppingcart.placeholder.PlaceholderContent.PlaceholderItem
import com.example.shoppingcart.databinding.FragmentItemBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyProductRecyclerViewAdapter(
    private val listener:(PlaceholderItem)->Unit
) : RecyclerView.Adapter<MyProductRecyclerViewAdapter.ViewHolder>() {

    var values:MutableList<PlaceholderItem> = ArrayList<PlaceholderItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(values[position])
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val img: ImageView = binding.pImage
        val pName: TextView = binding.name
        val price: TextView = binding.price
        val description: TextView = binding.shortDescription
        val rate: TextView = binding.pRate

        init {
            itemView.setOnClickListener {
                listener.invoke(values[absoluteAdapterPosition])
            }
        }

        fun bind(item: PlaceholderItem){
            img.setImageResource(item.imageId)
            pName.text = item.name
            price.text = "Price: Rs ${item.price}"
            description.text = item.shortDescription
            rate.text = if(item.rating==0) "Rating: NA" else "Rating: ${item.rating}"
        }
/*
        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
*/
    }

}