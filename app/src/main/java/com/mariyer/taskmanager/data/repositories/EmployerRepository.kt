package com.mariyer.taskmanager.data.repositories

import android.content.Context
import androidx.room.withTransaction
import com.mariyer.taskmanager.data.db.Database
import com.mariyer.taskmanager.data.db.model.EmployerStaffWithNames
import com.mariyer.taskmanager.data.db.model.EmployerWithStaff
import com.mariyer.taskmanager.data.db.model.StaffTableWithNames
import com.mariyer.taskmanager.data.db.model.entyties.Employer
import kotlinx.coroutines.flow.Flow
import java.time.Instant

class EmployerRepository(context: Context) {
    private val employerDao = Database.instance.employerDao()
    private val employerStaffDao = Database.instance.employerStaffDao()
    private val staffTableDao = Database.instance.staffTableDao()

    suspend fun getAllEmployers(): List<Employer> {
        return employerDao.getAllEmployers()
    }

    suspend fun getEmployersWithStaff(orgId: Long): List<EmployerWithStaff> {
        return employerDao.getEmployersWithStaff(orgId)
    }

    fun getEmployersStaffWithNames(orgId: Long): Flow<List<EmployerStaffWithNames>> {
        return employerDao.getEmployersWithStaffNames(orgId)
    }

    fun getEmployersStaffWithNamesByProjectId(projId: Long): Flow<List<EmployerStaffWithNames>> {
        return employerDao.getEmployersWithStaffNamesByProjectId(projId)
    }

    suspend fun getEmployerById(employerId: Long): Employer? {
        return employerDao.getEmployerById(employerId)
    }

    suspend fun saveEmployer(employer: Employer) {
        employerDao.insertEmployer(employer)
    }

    suspend fun saveEmployerWithStaff(employerWithStaff: EmployerWithStaff) {
        //transaction
        Database.instance.withTransaction {
            val emplId = employerDao.insertEmployer(employerWithStaff.employer)
            employerWithStaff.staffs.forEach {
                it.employerId = emplId
            }
            employerStaffDao.insertEmployerStaffs(employerWithStaff.staffs)
        }
    }

    suspend fun updateEmployer(employer: Employer) {
        employerDao.updateEmployer(employer)
    }

    suspend fun removeEmployer(employerId: Long) {
        employerDao.removeEmployer(employerId)
    }

    suspend fun getEmployerStaffs(employerId: Long): List<EmployerStaffWithNames> {
        return employerStaffDao.getEmployerStaffsWithNamesByEmplId(employerId)
    }

    fun addStaffLocal(
        staffList: List<EmployerStaffWithNames>,
        emplId: Long,
        fullEmplName: String,
        depId: Long,
        depTitle: String,
        postId: Long,
        postTitle: String,
        dateStart: Instant,
        dateEnd: Instant?,
        orgId: Long?
    ): List<EmployerStaffWithNames> {
        var newList = staffList.toMutableList()
        newList.add(
            EmployerStaffWithNames(
                id = 0L,
                departmentId = depId,
                postId = postId,
                employerId = emplId,
                dateStart = dateStart,
                dateEnd = dateEnd,
                departmentName = depTitle,
                postName = postTitle,
                fullEmployerName = fullEmplName,
                organizationId = orgId
            )
        )

        return newList.toList()
    }

    fun getStaffTableByOrgId(orgId: Long): Flow<List<StaffTableWithNames>> {
        return staffTableDao.getStaffTablesWithNames(orgId)
    }

    fun removeStaffLocal(list: List<EmployerStaffWithNames>, emplId: Long, depId: Long, postId: Long): List<EmployerStaffWithNames>? {
        return list.filterNot { it.employerId == emplId && it.departmentId == depId && it.postId == postId }
    }

    fun getStaffLocal(list: List<EmployerStaffWithNames>, emplId: Long, depId: Long, postId: Long): EmployerStaffWithNames? {
        return list.find { it.employerId == emplId && it.departmentId == depId && it.postId == postId }
    }

    fun searchItemById(list: List<String>, searchId: Long): String? {
        return list.find { it.contains("(id=${searchId.toString()})") }
    }

    suspend fun getEmployer(emplId: Long): Employer? {
        return employerDao.getEmployerById(emplId)
    }

}