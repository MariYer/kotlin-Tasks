package com.mariyer.taskmanager.ui.lists.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.mariyer.taskmanager.data.db.model.TaskWithNames
import com.mariyer.taskmanager.data.repositories.TaskRepository
import kotlinx.coroutines.launch

class TasksListViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = TaskRepository(application)

    var currentProjectId: Long = 0L
        private set

    val tasks: LiveData<List<TaskWithNames>>
        get() = repository.getTasksByProjectId(currentProjectId)
        .asLiveData(viewModelScope.coroutineContext)

    fun setCurrentProjectId(newProjectId: Long) {
        currentProjectId = newProjectId
    }

    fun deleteTask(projId: Long, taskId: Long) {
        viewModelScope.launch {
            try {
                repository.removeTask(taskId)
            } catch (t: Throwable) {
                Log.e("TasksListViewModel", "deleteTask: ${t.message}")
            }
        }
    }
}
