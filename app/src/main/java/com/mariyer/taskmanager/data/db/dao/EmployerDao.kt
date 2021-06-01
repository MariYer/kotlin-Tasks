package com.mariyer.taskmanager.data.db.dao

import androidx.room.*
import com.mariyer.taskmanager.data.db.contracts.*
import com.mariyer.taskmanager.data.db.model.EmployerStaffWithNames
import com.mariyer.taskmanager.data.db.model.EmployerWithStaff
import com.mariyer.taskmanager.data.db.model.entyties.Employer
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployerDao {

    @Query("SELECT * FROM ${EmployerContract.TABLE_NAME}")
    suspend fun getAllEmployers(): List<Employer>

    @Query("SELECT * FROM ${EmployerContract.TABLE_NAME} WHERE ${EmployerContract.Columns.ORGANIZATION_ID}=:orgId")
    suspend fun getEmployersWithStaff(orgId: Long): List<EmployerWithStaff>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployer(employer: Employer): Long

    @Update
    suspend fun updateEmployer(employer: Employer)

    @Query("DELETE FROM ${EmployerContract.TABLE_NAME} WHERE ${EmployerContract.Columns.ID} = :employerId")
    suspend fun removeEmployer(employerId: Long)

    @Query("SELECT * FROM ${EmployerContract.TABLE_NAME} WHERE ${EmployerContract.Columns.ID} = :employerId")
    suspend fun getEmployerById(employerId: Long): Employer?

    @Query(
        "SELECT staff.*,dep.${DepartmentContract.Columns.TITLE} AS ${EmployerStaffContract.Columns.DEPARTMENT_NAME}, " +
                " post.${PostContract.Columns.TITLE} AS ${EmployerStaffContract.Columns.POST_NAME},\n" +
                " empl.${EmployerContract.Columns.SURNAME}||' '||empl.${EmployerContract.Columns.NAME}||' '||empl.${EmployerContract.Columns.MIDDLE_NAME} AS ${EmployerStaffContract.Columns.FULL_EMPLOYER_NAME},\n" +
                " empl.${EmployerContract.Columns.ORGANIZATION_ID} AS ${EmployerStaffContract.Columns.ORGANIZATION_ID}\n" +
                " FROM ${EmployerContract.TABLE_NAME} AS empl\n" +
                " JOIN ${EmployerStaffContract.TABLE_NAME} AS staff ON (empl.${EmployerContract.Columns.ID}=staff.${EmployerStaffContract.Columns.EMPLOYER_ID})\n" +
                " JOIN ${DepartmentContract.TABLE_NAME} AS dep ON (dep.${DepartmentContract.Columns.ID}=staff.${EmployerStaffContract.Columns.DEPARTMENT_ID})\n" +
                " JOIN ${PostContract.TABLE_NAME} AS post ON (post.${PostContract.Columns.ID}=staff.${EmployerStaffContract.Columns.POST_ID})\n" +
                " WHERE empl.${EmployerContract.Columns.ORGANIZATION_ID} = :orgId"
    )
    fun getEmployersWithStaffNames(orgId: Long): Flow<List<EmployerStaffWithNames>>

    @Query(
        "SELECT staff.*,dep.${DepartmentContract.Columns.TITLE} AS ${EmployerStaffContract.Columns.DEPARTMENT_NAME}, " +
                " post.${PostContract.Columns.TITLE} AS ${EmployerStaffContract.Columns.POST_NAME},\n" +
                " empl.${EmployerContract.Columns.SURNAME}||' '||empl.${EmployerContract.Columns.NAME}||' '||empl.${EmployerContract.Columns.MIDDLE_NAME} AS ${EmployerStaffContract.Columns.FULL_EMPLOYER_NAME},\n" +
                " empl.${EmployerContract.Columns.ORGANIZATION_ID} AS ${EmployerStaffContract.Columns.ORGANIZATION_ID}\n" +
                " FROM ${EmployerContract.TABLE_NAME} AS empl\n" +
                " JOIN ${EmployerStaffContract.TABLE_NAME} AS staff ON (empl.${EmployerContract.Columns.ID}=staff.${EmployerStaffContract.Columns.EMPLOYER_ID})\n" +
                " JOIN ${DepartmentContract.TABLE_NAME} AS dep ON (dep.${DepartmentContract.Columns.ID}=staff.${EmployerStaffContract.Columns.DEPARTMENT_ID})\n" +
                " JOIN ${PostContract.TABLE_NAME} AS post ON (post.${PostContract.Columns.ID}=staff.${EmployerStaffContract.Columns.POST_ID})\n" +
                " JOIN ${ProjectContract.TABLE_NAME} AS proj ON (dep.${DepartmentContract.Columns.ID}=proj.${ProjectContract.Columns.DEPARTMENT_ID})\n" +
                " WHERE proj.${ProjectContract.Columns.ID} = :projId"
    )
    fun getEmployersWithStaffNamesByProjectId(projId: Long): Flow<List<EmployerStaffWithNames>>
}