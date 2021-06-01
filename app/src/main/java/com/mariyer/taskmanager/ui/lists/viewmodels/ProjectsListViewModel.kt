package com.mariyer.taskmanager.ui.lists.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.mariyer.taskmanager.data.db.model.ProjectWithOrgDepartment
import com.mariyer.taskmanager.data.repositories.ProjectRepository
import kotlinx.coroutines.launch

class ProjectsListViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = ProjectRepository(application)

    val projects: LiveData<List<ProjectWithOrgDepartment>> = repository.getAllProjects()
        .asLiveData(viewModelScope.coroutineContext)

    fun deleteProject(projId: Long) {
        viewModelScope.launch {
            try {
                repository.removeProject(projId)
            } catch (t: Throwable) {
                Log.e("ProjectsListViewModel", "getAllProjects: ${t.message}")
            }
        }
    }
}
