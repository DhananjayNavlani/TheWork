package com.example.roomdatabaseexample.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase: RoomDatabase() {

    abstract fun taskListDao(): TaskListDao
    abstract fun taskDetailDao(): TaskDetailDao

    companion object{
        private var instance: TaskDatabase? = null

        fun getDatabase(context: Context) = instance
            ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database"
                ).build().also { instance = it }
            }
    }
}