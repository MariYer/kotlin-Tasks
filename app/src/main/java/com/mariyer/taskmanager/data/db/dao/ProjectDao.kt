package com.mariyer.taskmanager.data.db.dao

import androidx.room.*
import com.mariyer.taskmanager.data.db.contracts.DepartmentContract
import com.mariyer.taskmanager.data.db.contracts.OrganizationContract
import com.mariyer.taskmanager.data.db.contracts.ProjectContract
import com.mariyer.taskmanager.data.db.model.ProjectWithOrgDepartment
import com.mariyer.taskmanager.data.db.model.entyties.Project
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {

    @Query("SELECT * FROM ${ProjectContract.TABLE_NAME}")
    suspend fun getAllProjects(): List<Project>

    @Query(
        "SELECT project.*, org.${OrganizationContract.Columns.ID} AS ${ProjectContract.Columns.ORGANIZATION_ID}, org.${OrganizationContract.Columns.TITLE} AS ${ProjectContract.Columns.ORGANIZATION_TITLE},\n" +
                " dep.${DepartmentContract.Columns.TITLE} AS ${ProjectContract.Columns.DEPARTMENT_TITLE}\n" +
                "  FROM ${ProjectContract.TABLE_NAME} AS project JOIN ${DepartmentContract.TABLE_NAME} AS dep ON (project.${ProjectContract.Columns.DEPARTMENT_ID}=dep.${DepartmentContract.Columns.ID})\n" +
                "JOIN ${OrganizationContract.TABLE_NAME} AS org ON (dep.${DepartmentContract.Columns.ORGANIZATION_ID}=org.${OrganizationContract.Columns.ID})"
    )
    fun getAllProjectsWithOrgDepartment(): Flow<List<ProjectWithOrgDepartment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProjects(projects: List<Project>)

    @Update
    suspend fun updateProject(project: Project)

    @Query("DELETE FROM ${ProjectContract.TABLE_NAME} WHERE ${ProjectContract.Columns.ID} = :projectId")
    suspend fun removeProject(projectId: Long)

    @Query("SELECT * FROM ${ProjectContract.TABLE_NAME} WHERE ${ProjectContract.Columns.ID} = :projectId")
    suspend fun getProjectById(projectId: Long): Project?

}