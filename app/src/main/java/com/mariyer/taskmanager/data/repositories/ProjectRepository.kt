package com.mariyer.taskmanager.data.repositories

import android.content.Context
import com.mariyer.taskmanager.data.db.Database
import com.mariyer.taskmanager.data.db.model.ProjectWithOrgDepartment
import com.mariyer.taskmanager.data.db.model.entyties.Project
import kotlinx.coroutines.flow.Flow

class ProjectRepository(context: Context) {
    private val projectDao = Database.instance.projectDao()

    fun getAllProjects(): Flow<List<ProjectWithOrgDepartment>> {
        return projectDao.getAllProjectsWithOrgDepartment()
    }

    suspend fun getProjectById(projectId: Long): Project? {
        return projectDao.getProjectById(projectId)
    }

    suspend fun saveProject(project: Project) {
        projectDao.insertProjects(listOf(project))
    }

    suspend fun updateProject(project: Project) {
        projectDao.updateProject(project)
    }

    suspend fun removeProject(projectId: Long) {
        projectDao.removeProject(projectId)
    }

    fun searchDropDownItemById(list: List<String>, searchId: Long): String? {
        return list.find { item ->
            item.contains("(id=${searchId.toString()})")
        }
    }
}