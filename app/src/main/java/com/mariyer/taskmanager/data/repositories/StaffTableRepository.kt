package com.mariyer.taskmanager.data.repositories

import android.content.Context
import com.mariyer.taskmanager.data.db.Database
import com.mariyer.taskmanager.data.db.model.DepartmentWithHierarchy
import com.mariyer.taskmanager.data.db.model.StaffTableWithNames
import com.mariyer.taskmanager.data.db.model.entyties.Post
import com.mariyer.taskmanager.data.db.model.entyties.StaffTable
import kotlinx.coroutines.flow.Flow

class StaffTableRepository(context: Context) {
    private val staffTableDao = Database.instance.staffTableDao()
    private val departmentDao = Database.instance.departmentDao()
    private val postDao = Database.instance.postDao()

    suspend fun getAllStaffTables(): List<StaffTable> {
        return staffTableDao.getAllStaffTables()
    }

    fun getStaffTablesWithNames(orgId: Long): Flow<List<StaffTableWithNames>> {
        return staffTableDao.getStaffTablesWithNames(orgId)
    }

    suspend fun getStaffTableById(depId: Long, postId: Long): StaffTable? {
        return staffTableDao.getStaffTableById(depId, postId)
    }

    suspend fun saveStaffTable(staffTable: StaffTable) {
        staffTableDao.insertStaffTables(listOf(staffTable))
    }

    suspend fun updateStaffTable(staffTable: StaffTable) {
        staffTableDao.updateStaffTable(staffTable)
    }

    suspend fun removeStaffTable(depId: Long, postId: Long) {
        staffTableDao.removeStaffTable(depId, postId)
    }

    fun getDepartmentsWithHier(orgId: Long?): Flow<List<DepartmentWithHierarchy>> {
        return departmentDao.getDepartmentsWithHier((orgId ?: 0L))
    }

    fun getPostsByOrgId(orgId: Long): Flow<List<Post>> {
        return postDao.getPostsByOrgId(orgId)
    }
}