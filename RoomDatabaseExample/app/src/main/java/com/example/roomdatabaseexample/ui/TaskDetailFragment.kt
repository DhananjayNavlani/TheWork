package com.example.roomdatabaseexample.ui

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.roomdatabaseexample.data.PriorityLevel
import com.example.roomdatabaseexample.data.Task
import com.example.roomdatabaseexample.data.TaskStatus
import com.example.roomdatabaseexample.databinding.FragmentTaskDetailBinding

/**
 * A simple [Fragment] subclass.
 * Use the [TaskDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentTaskDetailBinding
    private lateinit var viewModel: TaskDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //we create it here bcoz as fragment goes in background and in foreground later
        //we do not need to create viewModel again as onCreate() is called only once
        //if we would have Factory class for viewModel that would be offloading.
        viewModel = ViewModelProvider(this)[TaskDetailViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_task_detail, container, false)
        binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val priorites = mutableListOf<String>()
        PriorityLevel.values().forEach { priorites.add(it.name) }
        val arrAdapter =
            ArrayAdapter(requireActivity(), R.layout.simple_spinner_dropdown_item, priorites)
        binding.taskPrioritySpinner.adapter = arrAdapter

        binding.taskPrioritySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                updateTaskPriorityView(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        val id = TaskDetailFragmentArgs.fromBundle(requireArguments()).id
        viewModel.setTaskId(id)

        viewModel.task.observe(viewLifecycleOwner, Observer {
            it?.let { setData(it) }
        })

        binding.btnSave.setOnClickListener { saveTask() }
        binding.btnDelete.setOnClickListener { deleteTask() }

    }

    private fun deleteTask() {
        viewModel.deleteTask()
        requireActivity().onBackPressed()
    }

    private fun saveTask() {
        val title = binding.taskTitle.toString()
        val detail = binding.taskDetail.toString()
        val priority = binding.taskPrioritySpinner.selectedItemPosition

        val selectedBtn = binding.statusGroup.findViewById<RadioButton>(binding.statusGroup.checkedRadioButtonId)
        var status = TaskStatus.Open.ordinal
        if( TaskStatus.Closed.name == selectedBtn.text)
            status = TaskStatus.Closed.ordinal

        val task = Task(viewModel.taskId.value!!,title,detail,priority,status)
        viewModel.saveTask(task)

        requireActivity().onBackPressed()
    }



    private fun setData(task: Task){
        updateTaskPriorityView(task.priority)
        binding.taskTitle.setText(task.title)
        binding.taskDetail.setText(task.detail)

        if(task.status == TaskStatus.Open.ordinal)
            binding.statusOpen.isChecked = true
        else
            binding.statusClosed.isChecked = true

        binding.taskPrioritySpinner.setSelection(task.priority)
    }
    private fun updateTaskPriorityView(position: Int) {
        when (position) {
            PriorityLevel.Low.ordinal -> {
                binding.taskPriorityView.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        com.example.roomdatabaseexample.R.color.colorLowPriority
                    )
                )
            }

            PriorityLevel.Medium.ordinal -> {
                binding.taskPriorityView.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        com.example.roomdatabaseexample.R.color.colorMedPriority
                    )
                )
            }

            else -> binding.taskPriorityView.setBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    com.example.roomdatabaseexample.R.color.colorHighPriority
                )
            )

        }
    }
}