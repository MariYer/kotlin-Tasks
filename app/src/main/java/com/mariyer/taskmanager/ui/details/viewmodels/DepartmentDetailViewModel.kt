package com.mariyer.taskmanager.ui.details.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.mariyer.taskmanager.data.db.model.entyties.Department
import com.mariyer.taskmanager.data.repositories.DepartmentRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DepartmentDetailViewModel(
    application: Application
) : AndroidViewModel(application) {


    private val repository = DepartmentRepository(application)

    private var currentOrgId: Long? = 0L
    private var currentDepId: Long? = 0L

    private val departmentMutableData = MutableLiveData<Department>()
    val department: LiveData<Department>
        get() = departmentMutableData

    lateinit var parents: LiveData<List<String>>

    fun getDepartmentById(depId: Long) {
        viewModelScope.launch {
            try {
                departmentMutableData.postValue(repository.getDepartmentById(depId))
            } catch (t: Throwable) {

            }
        }
    }

    fun saveDepartment(department: Department) {
        viewModelScope.launch {
            try {
                if (department.id == 0L) {
                    repository.saveDepartment(department)
                } else {
                    repository.updateDepartment(department)
                }
            } catch (t: Throwable) {

            }
        }
    }


    fun setCurrentOrgId(orgId: Long) {
        currentOrgId = orgId
        parents = repository.getDepartmentsWithHier(currentOrgId)
            .map { list ->
                list.map { data ->
                    if (data.id != currentDepId) {
                        "${data.hierTitle}(id=${data.id.toString()})"
                    } else {
                        ""
                    }
                }
            }
            .asLiveData(viewModelScope.coroutineContext)
    }

    fun setCurrentDepId(depId: Long) {
        currentDepId = depId
    }

    fun getParentTitle(parentId: Long): String? {
        return repository.getParentTitle(parents.value.orEmpty(), parentId)
    }
}
