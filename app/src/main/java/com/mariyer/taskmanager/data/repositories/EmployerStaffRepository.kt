package com.mariyer.taskmanager.data.repositories

import android.content.Context
import com.mariyer.taskmanager.data.db.Database
import com.mariyer.taskmanager.data.db.model.entyties.EmployerStaff

class EmployerStaffRepository(context: Context) {
    private val employerStaffDao = Database.instance.employerStaffDao()

    suspend fun getAllEmployerStaffs(): List<EmployerStaff> {
        return employerStaffDao.getAllEmployerStaffs()
    }

    suspend fun getEmployerStaffById(emplStaffId: Long): EmployerStaff? {
        return employerStaffDao.getEmployerStaffById(emplStaffId)
    }

    suspend fun saveEmployerStaff(emplStaff: EmployerStaff) {
        employerStaffDao.insertEmployerStaffs(listOf(emplStaff))
    }

    suspend fun updateEmployerStaff(emplStaff: EmployerStaff) {
        employerStaffDao.updateEmployerStaff(emplStaff)
    }

    suspend fun removeEmployerStaff(emplStaffId: Long) {
        employerStaffDao.removeEmployerStaff(emplStaffId)
    }
}