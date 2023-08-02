package com.example.roomdatabaseexample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabaseexample.R
import com.example.roomdatabaseexample.data.TaskAdapter
import com.example.roomdatabaseexample.data.SortColumn
import com.example.roomdatabaseexample.databinding.FragmentTaskListBinding

/**
 * A simple [Fragment] subclass.
 * Use the [TaskListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskListFragment : Fragment(), MenuProvider {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentTaskListBinding
    private lateinit var viewModel: TaskListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[TaskListViewModel::class.java]
//        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskListBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_task_list, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.toolbar){
            inflateMenu(R.menu.list_menu)
            setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.menu_sort_priority -> {
                        it.isChecked = true
                        true
                    }
                    R.id.menu_sort_title -> {
                        it.isChecked = true
                        true
                    }
                    else -> false
                }
            }
        }
//        activity?.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        with(binding.taskList){
            layoutManager = LinearLayoutManager(activity)
            adapter = TaskAdapter {
                findNavController().navigate(TaskListFragmentDirections.actionTaskListFragmentToTaskDetailFragment(it))
            }
        }

        binding.addTask.setOnClickListener {
            findNavController().navigate(TaskListFragmentDirections.actionTaskListFragmentToTaskDetailFragment(0))
        }

        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            (binding.taskList as TaskAdapter).submitList(it)
        })
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.list_menu,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.menu_sort_priority ->{
                viewModel.changeSortOrder(SortColumn.Priority)
                menuItem.isChecked = true
                true
            }
            R.id.menu_sort_title ->{
                viewModel.changeSortOrder(SortColumn.Title)
                menuItem.isChecked = true
                true
            }
            else -> false
        }
    }
}