package com.abulnes16.purrtodo.database

import androidx.room.*
import com.abulnes16.purrtodo.database.data.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks WHERE is_done != 1")
    fun getTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE id=:id")
    fun getTask(id: Int): Flow<Task>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun create(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)


}