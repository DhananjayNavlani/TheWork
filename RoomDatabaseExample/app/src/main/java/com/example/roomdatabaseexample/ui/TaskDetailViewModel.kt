package com.example.roomdatabaseexample.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.roomdatabaseexample.data.Task
import com.example.roomdatabaseexample.data.TaskDetailRepository
import kotlinx.coroutines.launch

class TaskDetailViewModel(application: Application): AndroidViewModel(application) {
    private val repository = TaskDetailRepository(application)
    private val _taskId = MutableLiveData<Long>(0)

    val taskId: LiveData<Long>
        get() = _taskId

    val task: LiveData<Task> = _taskId.switchMap {
        id -> repository.getTask(id)
    }

    fun setTaskId(id: Long){
        if(_taskId.value != id){
            _taskId.value = id
        }
    }

    fun saveTask(task: Task){
        viewModelScope.launch {
            if(_taskId.value == 0L)
                //as the function is suspend fun we can call it another suspend fun
                //or in coroutineScope
                _taskId.value = repository.insertTask(task)
            else
                repository.updateTask(task)
        }
    }

    fun deleteTask(){
        viewModelScope.launch {
            task.value?.let { repository.deleteTask(it) }
        }
    }
}