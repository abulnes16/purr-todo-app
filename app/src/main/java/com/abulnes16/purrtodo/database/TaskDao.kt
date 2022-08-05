package com.abulnes16.purrtodo.database

import androidx.room.*
import com.abulnes16.purrtodo.database.data.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun getTasks()

    @Query("SELECT * FROM tasks WHERE id=:id")
    fun getTask(id: Int)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun create(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)


}