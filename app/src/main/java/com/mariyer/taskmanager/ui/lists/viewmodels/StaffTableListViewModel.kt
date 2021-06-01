package com.mariyer.taskmanager.ui.lists.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.mariyer.taskmanager.data.db.model.StaffTableWithNames
import com.mariyer.taskmanager.data.repositories.StaffTableRepository
import kotlinx.coroutines.launch

class StaffTableListViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository = StaffTableRepository(application)

    var currentOrgId: Long = 0L
        private set

    val staffTable: LiveData<List<StaffTableWithNames>>
        get() = repository.getStaffTablesWithNames(currentOrgId)
            .asLiveData(viewModelScope.coroutineContext)

    fun setCurrentOrgId(orgId: Long?) {
        currentOrgId = (orgId ?: 0L)
    }

    fun deleteStaffTable(orgId: Long?, depId: Long, postId: Long) {
        viewModelScope.launch {
            try {
                repository.removeStaffTable(depId, postId)
                orgId?.let {
                    val list = repository.getStaffTablesWithNames(it)
                }
            } catch (t: Throwable) {
                Log.e("StaffTableListViewModel", "deleteStaffTable: ${t.message}")
            }
        }
    }
}