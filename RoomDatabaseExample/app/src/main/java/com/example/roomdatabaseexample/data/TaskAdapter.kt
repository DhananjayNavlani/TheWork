package com.example.roomdatabaseexample.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabaseexample.R
import com.example.roomdatabaseexample.databinding.ListItemBinding

class TaskAdapter(private val listener: (Long)->Unit): ListAdapter<Task, TaskAdapter.ViewHolder>(
    DiffCallback()
) {
    inner class ViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root){

        init {
            itemView.setOnClickListener {
                listener.invoke(getItem(adapterPosition).id)
            }
        }
        fun bind(task: Task){
            with(task){
                when(priority){
                    PriorityLevel.Low.ordinal ->{
                        binding.taskPriority.setBackgroundColor(ContextCompat.getColor(binding.root.context,
                            R.color.colorLowPriority
                        ))
                    }
                    PriorityLevel.Medium.ordinal ->{
                        binding.taskPriority.setBackgroundColor(ContextCompat.getColor(binding.root.context,
                            R.color.colorMedPriority
                        ))
                    }
                    else -> binding.taskPriority.setBackgroundColor(ContextCompat.getColor(binding.root.context,
                        R.color.colorHighPriority
                    ))
                }
                binding.taskTitle.text = title
                binding.taskDetail.text = detail
            }
        }
    }

    class DiffCallback: DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return  oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val itemLayout = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
//        return ViewHolder(itemLayout)

        return ViewHolder(
            ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}