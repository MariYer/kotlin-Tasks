package com.mariyer.taskmanager.ui.lists.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.mariyer.taskmanager.data.db.model.EmployerStaffWithNames
import com.mariyer.taskmanager.data.repositories.EmployerRepository
import kotlinx.coroutines.launch

class EmployerListViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = EmployerRepository(application)

    var currentOrgId: Long = 0
        private set

    val employers: LiveData<List<EmployerStaffWithNames>>
        get() = repository.getEmployersStaffWithNames(currentOrgId)
            .asLiveData(viewModelScope.coroutineContext)

    fun setCurrentOrgId(newOrgId: Long) {
        currentOrgId = newOrgId
    }


    fun deleteEmployer(orgId: Long, emplId: Long) {
        viewModelScope.launch {
            try {
                repository.removeEmployer(emplId)
            } catch (t: Throwable) {
                Log.e("EmployerListViewModel", "deleteEmployer: ${t.message}")
            }
        }
    }
}
