package com.mariyer.taskmanager.ui.details.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mariyer.taskmanager.data.db.model.entyties.Organization
import com.mariyer.taskmanager.data.repositories.OrganizationRepository
import kotlinx.coroutines.launch

class OrganizationDetailViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = OrganizationRepository(application)

    private val organizationMutableData = MutableLiveData<Organization>()
    val organization: LiveData<Organization>
        get() = organizationMutableData

    fun getOrganizationById(orgId: Long) {
        viewModelScope.launch {
            try {
                organizationMutableData.postValue(repository.getOrganizationById(orgId))
            } catch (t: Throwable) {

            }
        }
    }

    fun saveOrganization(organization: Organization) {
        viewModelScope.launch {
            try {
                if (organization.id == 0L) {
                    repository.saveOrganization(organization)
                } else {
                    repository.updateOrganization(organization)
                }
            } catch (t: Throwable) {

            }
        }
    }
}
