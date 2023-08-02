package com.example.roomdatabaseexample.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface TaskListDao {

    @Query("Select * from task where status = :status Order By priority Desc")
    fun getTasksByPriority(status: Int): LiveData<List<Task>>

    @Query("Select * from task where status = :status Order By title")
    fun getTasksByTitle(status: Int): LiveData<List<Task>>
}