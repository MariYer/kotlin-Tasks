package com.mariyer.taskmanager.ui.details.viewmodels

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.mariyer.taskmanager.data.db.model.TaskWithNames
import com.mariyer.taskmanager.data.db.model.entyties.Task
import com.mariyer.taskmanager.data.repositories.DepartmentRepository
import com.mariyer.taskmanager.data.repositories.EmployerRepository
import com.mariyer.taskmanager.data.repositories.ProjectRepository
import com.mariyer.taskmanager.data.repositories.TaskRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

class TaskDetailViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository = TaskRepository(application)
    private val projectRepository = ProjectRepository(application)
    private val employerRepository = EmployerRepository(application)
    private val departmentRepository = DepartmentRepository(application)

    var currentOrgId: Long = 0L
        private set
    var currentDepId: Long? = 0L
        private set
    var currentProjId: Long = 0L
        private set

    val projects: LiveData<List<String>>
        get() = projectRepository.getAllProjects()
            .map { list ->
                list.map { data ->
                    "${data.title}(id=${data.id.toString()})"
                }
            }
            .asLiveData(viewModelScope.coroutineContext)

    val requesters: LiveData<List<String>>
        get() = employerRepository.getEmployersStaffWithNamesByProjectId(currentProjId)
            .map { list ->
                list.map { data ->
                    "${data.fullEmployerName}(id=${data.id.toString()})"
                }
            }
            .asLiveData(viewModelScope.coroutineContext)


    val departments: LiveData<List<String>>
        get() = departmentRepository.getDepartmentsWithHier(currentOrgId)
            .map { list ->
                list.map { data ->
                    "${data.hierTitle}(id=${data.id.toString()})"
                }
            }
            .asLiveData(viewModelScope.coroutineContext)

    lateinit var executors: LiveData<List<String>>

    val states: LiveData<List<String>>
        get() = repository.getAllStates()
            .map { list ->
                list.map { data ->
                    "${data.title}(id=${data.id.toString()})"
                }
            }
            .asLiveData(viewModelScope.coroutineContext)

    private val taskMutableData = MutableLiveData<TaskWithNames>()
    val task: LiveData<TaskWithNames>
        get() = taskMutableData

    fun getTask(taskId: Long) {
        viewModelScope.launch {
            try {
                taskMutableData.postValue(repository.getTaskById(taskId))
            } catch (t: Throwable) {
                Log.e("TaskDetailViewModel", "getTask: ${t.message}")
            }
        }
    }

    fun fillTaskStates() {
        viewModelScope.launch {
            try {
                repository.fillStates()
            } catch (t: Throwable) {
                Log.e("TaskDetailViewModel", "fillTaskStates: ${t.message}")
            }
        }
    }

    fun saveTask(task: Task) {
        viewModelScope.launch {
            try {
                if (task.id == 0L) {
                    repository.saveTask(task)
                } else {
                    repository.updateTask(task)
                }
            } catch (t: Throwable) {
                Log.e("TaskDetailViewModel", "saveTask: ${t.message}")
            }
        }
    }

    fun setCurrentProjId(projId: Long) {
        currentProjId = projId
    }

    fun setCurrentOrgId(orgId: Long) {
        currentOrgId = orgId
    }

    fun setCurrentDepId(depId: Long?) {
        currentDepId = depId
        executors = employerRepository.getEmployersStaffWithNames(currentOrgId)
            .map { list ->
                list.map { data ->
                    if ((currentDepId ?: 0L) == data.departmentId || (currentDepId ?: 0L) == 0L) {
                        "${data.fullEmployerName}(id=${data.id.toString()})"
                    } else {
                        ""
                    }
                }
            }
            .asLiveData(viewModelScope.coroutineContext)
    }

    fun getProjectTitle(projId: Long): String? {
        return repository.searchItemById(projects.value.orEmpty(), projId)
    }

    fun getRequesterName(reqId: Long): String? {
        return repository.searchItemById(requesters.value.orEmpty(), reqId)
    }

    fun getDepartmentForTitle(depId: Long?): String? {
        return repository.searchItemById(departments.value.orEmpty(), depId ?: 0L)
    }

    fun getExecutorName(execId: Long?): String? {
        return repository.searchItemById(executors.value.orEmpty(), execId ?: 0L)
    }

    fun getStateTitle(stateId: Long): String? {
        return repository.searchItemById(states.value.orEmpty(), stateId)
    }


    companion object {
        const val PROJECTS_LIST = "projects"
        const val REQUESTERS_LIST = "requesters"
        const val DEPARTMENTS_LIST = "departments"
        const val EXECUTORS_LIST = "executors"
        const val STATES_LIST = "states"
        const val PRIORITY_LIST = "priority"

        @RequiresApi(Build.VERSION_CODES.O)
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    }
}
