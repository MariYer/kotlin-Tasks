package com.mariyer.taskmanager.data.db.dao

import androidx.room.*
import com.mariyer.taskmanager.data.db.contracts.DepartmentContract
import com.mariyer.taskmanager.data.db.contracts.EmployerContract
import com.mariyer.taskmanager.data.db.contracts.EmployerStaffContract
import com.mariyer.taskmanager.data.db.contracts.PostContract
import com.mariyer.taskmanager.data.db.model.EmployerStaffWithNames
import com.mariyer.taskmanager.data.db.model.entyties.EmployerStaff

@Dao
interface EmployerStaffDao {

    @Query("SELECT * FROM ${EmployerStaffContract.TABLE_NAME}")
    suspend fun getAllEmployerStaffs(): List<EmployerStaff>

    @Query(
        "SELECT staff.*,dep.${DepartmentContract.Columns.TITLE} AS ${EmployerStaffContract.Columns.DEPARTMENT_NAME}, " +
                " empl.${EmployerContract.Columns.SURNAME}||'  '||empl.${EmployerContract.Columns.NAME}||'  '||empl.${EmployerContract.Columns.MIDDLE_NAME} AS ${EmployerStaffContract.Columns.FULL_EMPLOYER_NAME},\n" +
                " post.${PostContract.Columns.TITLE} AS ${EmployerStaffContract.Columns.POST_NAME}, empl.${EmployerContract.Columns.ORGANIZATION_ID} AS ${EmployerStaffContract.Columns.ORGANIZATION_ID}\n" +
                " FROM ${EmployerStaffContract.TABLE_NAME} AS staff\n" +
                " JOIN ${EmployerContract.TABLE_NAME} AS empl ON (empl.${EmployerContract.Columns.ID}=staff.${EmployerStaffContract.Columns.EMPLOYER_ID})\n" +
                " JOIN ${DepartmentContract.TABLE_NAME} AS dep ON (dep.${DepartmentContract.Columns.ID}=staff.${EmployerStaffContract.Columns.DEPARTMENT_ID})\n" +
                " JOIN ${PostContract.TABLE_NAME} AS post ON (post.${PostContract.Columns.ID}=staff.${EmployerStaffContract.Columns.POST_ID})\n" +
                " WHERE staff.${EmployerStaffContract.Columns.EMPLOYER_ID} = :emplId"
    )
    suspend fun getEmployerStaffsWithNamesByEmplId(emplId: Long): List<EmployerStaffWithNames>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployerStaffs(emplStaffs: List<EmployerStaff>)

    @Update
    suspend fun updateEmployerStaff(emplStaff: EmployerStaff)

    @Query("DELETE FROM ${EmployerStaffContract.TABLE_NAME} WHERE ${EmployerStaffContract.Columns.ID} = :emplStaffId")
    suspend fun removeEmployerStaff(emplStaffId: Long)

    @Query("SELECT * FROM ${EmployerStaffContract.TABLE_NAME} WHERE ${EmployerStaffContract.Columns.ID} = :emplStaffId")
    suspend fun getEmployerStaffById(emplStaffId: Long): EmployerStaff?

}