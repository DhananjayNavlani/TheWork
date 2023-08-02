package com.example.roomdatabaseexample.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDetailDao {

    @Query("Select * from task where id = :id")
    fun getTask(id: Long): LiveData<Task>

    //use suspend with fun which doesn't return LiveData
    //bcoz Room doesn't run Database queries on Main thread
    //hence it can coz exception
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: Task): Long

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}