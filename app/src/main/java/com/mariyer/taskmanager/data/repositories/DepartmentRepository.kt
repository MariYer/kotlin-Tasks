package com.mariyer.taskmanager.data.repositories

import android.content.Context
import com.mariyer.taskmanager.data.db.Database
import com.mariyer.taskmanager.data.db.model.DepartmentWithHierarchy
import com.mariyer.taskmanager.data.db.model.entyties.Department
import kotlinx.coroutines.flow.Flow

class DepartmentRepository(context: Context) {
    private val departmentDao = Database.instance.departmentDao()

    suspend fun getAllDepartments(orgId: Long?): List<Department> {
        val id = (orgId ?: 0L)
        return if (id > 0L) {
            departmentDao.getDepartmentsByOrgId(id)
        } else {
            departmentDao.getAllDepartments()
        }
    }

    fun getDepartmentsWithHier(orgId: Long?): Flow<List<DepartmentWithHierarchy>> {
       return  departmentDao.getDepartmentsWithHier((orgId ?: 0L))
    }

    suspend fun getDepartmentById(depId: Long): Department? {
        return departmentDao.getDepartmentById(depId)
    }

    suspend fun saveDepartment(department: Department) {
        departmentDao.insertDepartments(listOf(department))
    }

    suspend fun updateDepartment(department: Department) {
        departmentDao.updateDepartment(department)
    }

    suspend fun removeDepartment(depId: Long) {
        departmentDao.removeDepartment(depId)
    }

    fun getParentTitle(list: List<String>, depId: Long): String? {
        return list.find {
            it.contains("(id=${depId.toString()})")
        }
    }
}