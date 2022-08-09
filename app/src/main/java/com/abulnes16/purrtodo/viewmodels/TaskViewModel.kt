package com.abulnes16.purrtodo.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.abulnes16.purrtodo.database.TaskDao
import com.abulnes16.purrtodo.database.data.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class TaskViewModel(private val taskDao: TaskDao) : ViewModel() {

    private var _error: MutableLiveData<Boolean> = MutableLiveData(false)
    val error: LiveData<Boolean>
        get() = _error

    fun allTodos(): Flow<List<Task>> = taskDao.getTasks()

    fun inProgressTasks(): Flow<List<Task>> = taskDao.getInProgressTasks()

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
            description,
            deadline,
            isDone = false,
            isInProgress = false
        )
        createTask(newTask)
    }

    fun retrieveTask(id: Int): LiveData<Task> {
        return taskDao.getTask(id).asLiveData()
    }

    private fun createTask(
        task: Task
    ) {
        viewModelScope.launch {
            try {
                taskDao.create(task)
                _error.value = false
            } catch (exception: Exception) {
                Log.e("[CREATE_TASK]", exception.toString())
                _error.value = true
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