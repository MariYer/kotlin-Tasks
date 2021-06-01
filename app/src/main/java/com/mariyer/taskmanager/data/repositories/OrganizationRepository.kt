package com.mariyer.taskmanager.data.repositories

import android.content.Context
import com.mariyer.taskmanager.data.db.Database
import com.mariyer.taskmanager.data.db.model.entyties.Organization
import kotlinx.coroutines.flow.Flow

class OrganizationRepository(context: Context)  {
    private val organizationDao = Database.instance.organizationDao()

    fun getAllOrganisations(): Flow<List<Organization>> {
        return organizationDao.getAllOrganizations()
    }

    suspend fun getOrganizationById(orgId: Long): Organization? {
        return organizationDao.getOrganizationById(orgId)
    }

    suspend fun saveOrganization(organization: Organization) {
        organizationDao.insertOrganization(organization)
    }

    suspend fun updateOrganization(organization: Organization) {
        organizationDao.updateOrganization(organization)
    }

    suspend fun removeOrganization(orgId: Long) {
        organizationDao.removeOrganization(orgId)
    }
}