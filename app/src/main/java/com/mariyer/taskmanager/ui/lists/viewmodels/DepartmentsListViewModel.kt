package com.mariyer.taskmanager.ui.lists.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.mariyer.taskmanager.data.db.model.DepartmentWithHierarchy
import com.mariyer.taskmanager.data.repositories.DepartmentRepository
import kotlinx.coroutines.launch

class DepartmentsListViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository = DepartmentRepository(application)

    var currentOrgId: Long? = 0L
        private set

    val departmentsList: LiveData<List<DepartmentWithHierarchy>>
        get() = repository.getDepartmentsWithHier(currentOrgId)
            .asLiveData(viewModelScope.coroutineContext)

    fun setCurrentOrgId(orgId: Long?) {
        currentOrgId = orgId
        Log.d("DepartmentsListViewModel", "currentOrgId = $currentOrgId")
    }

    fun deleteDepartment(depId: Long, orgId: Long?) {
        viewModelScope.launch {
            try {
                repository.removeDepartment(depId)
            } catch (t: Throwable) {
                Log.e("DepartmentsListViewModel", "deleteDepartment: ${t.message}")
            }
        }
    }
}