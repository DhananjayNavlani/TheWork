package com.example.roomdatabaseexample.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.example.roomdatabaseexample.data.SortColumn
import com.example.roomdatabaseexample.data.Task
import com.example.roomdatabaseexample.data.TaskListRepository

class TaskListViewModel(application: Application): AndroidViewModel(application) {
    private val repo: TaskListRepository = TaskListRepository(application)

    private val _sortOrder = MutableLiveData<SortColumn>(SortColumn.Priority)

    val tasks: LiveData<List<Task>> = _sortOrder.switchMap {
        repo.getTasks(it)
    }

    fun changeSortOrder(sortOrder: SortColumn){
        _sortOrder.value = sortOrder
    }
}