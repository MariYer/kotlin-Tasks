package com.mariyer.taskmanager.data.db.dao

import androidx.room.*
import com.mariyer.taskmanager.data.db.contracts.OrganizationContract
import com.mariyer.taskmanager.data.db.model.entyties.Organization
import kotlinx.coroutines.flow.Flow

@Dao
interface OrganizationDao {

    @Query("SELECT * FROM ${OrganizationContract.TABLE_NAME}")
    fun getAllOrganizations(): Flow<List<Organization>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrganization(organization: Organization)

    @Update
    suspend fun updateOrganization(organization: Organization)

    @Query("DELETE FROM ${OrganizationContract.TABLE_NAME} WHERE ${OrganizationContract.Columns.ID} = :orgId")
    suspend fun removeOrganization(orgId: Long)

    @Query("SELECT * FROM ${OrganizationContract.TABLE_NAME} WHERE ${OrganizationContract.Columns.ID} = :orgId")
    suspend fun getOrganizationById(orgId: Long): Organization?

}