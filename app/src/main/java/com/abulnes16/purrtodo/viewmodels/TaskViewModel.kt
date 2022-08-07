package com.abulnes16.purrtodo.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.abulnes16.purrtodo.database.TaskDao
import com.abulnes16.purrtodo.database.data.Task
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class TaskViewModel(private val taskDao: TaskDao) : ViewModel() {

    fun createTask(
        title: String,
        project: String,
        deadline: String,
        description: String
    ) {
        val newTask = Task(
            0,
            title,
            project,
            deadline,
            description,
            isDone = false,
            isInProgress = false
        )
        createTask(newTask)
    }

    private fun createTask(
        task: Task
    ) {
        viewModelScope.launch {
            try {
                taskDao.create(task)
            } catch (exception: Exception) {
                Log.e("[CREATE_TASK]", exception.toString())
                // TODO: Add notification for failed creation
            }
        }

    }

    fun isEntryValid(
        title: String,
        description: String,
        project: String,
        deadline: String
    ): Boolean {
        if (title.isBlank() || description.isBlank() || project.isBlank() || deadline.isBlank()) {
            return false
        }
        return true
    }

}


class TaskViewModelFactory(private val taskDao: TaskDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(taskDao) as T
        }

        throw IllegalArgumentException("Unknown View Model Class")
    }
}