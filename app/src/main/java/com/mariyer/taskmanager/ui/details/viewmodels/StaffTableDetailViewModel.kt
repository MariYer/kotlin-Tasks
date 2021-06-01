package com.mariyer.taskmanager.ui.details.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.mariyer.taskmanager.data.db.model.entyties.StaffTable
import com.mariyer.taskmanager.data.repositories.StaffTableRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class StaffTableDetailViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository = StaffTableRepository(application)

    var currentOrgId: Long = 0L
        private set

    val departments: LiveData<List<String>>
        get() = repository.getDepartmentsWithHier(currentOrgId)
            .map { list ->
                list.map { data ->
                    "${data.hierTitle}(id=${data.id.toString()})"
                }
            }
            .asLiveData(viewModelScope.coroutineContext)

    val posts: LiveData<List<String>>
        get() = repository.getPostsByOrgId(currentOrgId)
            .map { list ->
                list.map { data ->
                    "${data.title} (id=${data.id.toString()})"
                }
            }
            .asLiveData(viewModelScope.coroutineContext)

    fun saveStaffTable(staff: StaffTable, isNew: Boolean = true) {
        viewModelScope.launch {
            try {
                if (isNew) {
                    repository.saveStaffTable(staff)
                } else {
                    repository.updateStaffTable(staff)
                }
            } catch (t: Throwable) {
                Log.e("StaffTableDetailViewModel", "saveStaffTable: ${t.message}")
            }
        }
    }

    fun setCurrentOrgId(orgId: Long) {
        currentOrgId = orgId
    }
}
