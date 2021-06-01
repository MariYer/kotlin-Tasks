package com.mariyer.taskmanager.ui.details.viewmodels

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.mariyer.taskmanager.data.db.model.entyties.Project
import com.mariyer.taskmanager.data.repositories.DepartmentRepository
import com.mariyer.taskmanager.data.repositories.OrganizationRepository
import com.mariyer.taskmanager.data.repositories.ProjectRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

class ProjectDetailViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = ProjectRepository(application)
    private val organizationRepository = OrganizationRepository(application)
    private val departmentRepository = DepartmentRepository(application)

    var currentOrgId: Long? = 0L
        private set

    private val projectMutableData = MutableLiveData<Project>()
    val project: LiveData<Project>
        get() = projectMutableData

    val organizations: LiveData<List<String>> = organizationRepository.getAllOrganisations()
            .map { list ->
                list.map { data ->
                    "${data.title}(id=${data.id.toString()})"
                }
            }
            .asLiveData(viewModelScope.coroutineContext)

    lateinit var departments: LiveData<List<String>>


    fun getProjectDyId(projectId: Long) {
        viewModelScope.launch {
            try {
                projectMutableData.postValue(repository.getProjectById(projectId))
            } catch (t: Throwable) {
                Log.e("ProjectDetailViewModel", "getProjectDyId: ${t.message}")
            }
        }
    }

    fun saveProject(project: Project) {
        viewModelScope.launch {
            try {
                if (project.id == 0L) {
                    repository.saveProject(project)
                } else {
                    repository.updateProject(project)
                }
            } catch (t: Throwable) {
                Log.e("ProjectDetailViewModel", "saveProject: ${t.message}")
            }

        }
    }

    fun setCurrentOrgId(orgId: Long) {
        currentOrgId = orgId
        departments = departmentRepository.getDepartmentsWithHier(currentOrgId)
            .map { list ->
                list.map { data ->
                    "${data.hierTitle}(id=${data.id.toString()})"
                }
            }
            .asLiveData(viewModelScope.coroutineContext)
    }

    fun searchOrganizationDropDownById(orgId: Long): String? {
        return repository.searchDropDownItemById(organizations.value.orEmpty(), orgId)
    }

    fun searchDepartmentDropDownById(depId: Long): String? {
        return repository.searchDropDownItemById(departments.value.orEmpty(), depId)
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    }
}
