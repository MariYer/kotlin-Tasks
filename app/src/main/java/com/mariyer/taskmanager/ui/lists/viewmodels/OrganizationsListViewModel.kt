package com.mariyer.taskmanager.ui.lists.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.mariyer.taskmanager.data.db.model.entyties.Organization
import com.mariyer.taskmanager.data.repositories.OrganizationRepository
import kotlinx.coroutines.launch

class OrganizationsListViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository = OrganizationRepository(application)

    val organizations: LiveData<List<Organization>> = repository.getAllOrganisations()
        .asLiveData(viewModelScope.coroutineContext)

    fun deleteOrganization(orgId: Long) {
        viewModelScope.launch {
            try {
                repository.removeOrganization(orgId)
            } catch (t: Throwable) {
                Log.e("OrganizationsListViewModel", "deleteOrganization: ${t.message}")
            }
        }
    }
}