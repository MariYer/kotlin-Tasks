package com.mariyer.taskmanager.data.db.dao

import androidx.room.*
import com.mariyer.taskmanager.data.db.contracts.DepartmentContract
import com.mariyer.taskmanager.data.db.contracts.PostContract
import com.mariyer.taskmanager.data.db.contracts.StaffTableContract
import com.mariyer.taskmanager.data.db.model.entyties.StaffTable
import com.mariyer.taskmanager.data.db.model.StaffTableWithNames
import kotlinx.coroutines.flow.Flow

@Dao
interface StaffTableDao {

    @Query("SELECT * FROM ${StaffTableContract.TABLE_NAME}")
    suspend fun getAllStaffTables(): List<StaffTable>

    @Query(
        "SELECT dep.${DepartmentContract.Columns.TITLE} AS ${StaffTableContract.Columns.DEPARTMENT_NAME}, " +
                " post.${PostContract.Columns.TITLE} AS ${StaffTableContract.Columns.POST_NAME}, staff.*\n" +
                " FROM staff_table AS staff\n" +
                " JOIN departments AS dep ON (dep.id=staff.department_id)\n" +
                " JOIN posts AS post ON (post.id=staff.post_id)\n" +
                " WHERE dep.${DepartmentContract.Columns.ORGANIZATION_ID}=:orgId"
    )
    fun getStaffTablesWithNames(orgId: Long): Flow<List<StaffTableWithNames>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStaffTables(staffs: List<StaffTable>)

    @Update
    suspend fun updateStaffTable(staff: StaffTable)

    @Query(
        "DELETE FROM ${StaffTableContract.TABLE_NAME} " +
                "WHERE ${StaffTableContract.Columns.DEPARTMENT_ID} = :depId " +
                "AND ${StaffTableContract.Columns.POST_ID} = :postId"
    )
    suspend fun removeStaffTable(depId: Long, postId: Long)

    @Query(
        "SELECT * FROM ${StaffTableContract.TABLE_NAME} " +
                "WHERE ${StaffTableContract.Columns.DEPARTMENT_ID} = :depId " +
                "AND ${StaffTableContract.Columns.POST_ID} = :postId"
    )
    suspend fun getStaffTableById(depId: Long, postId: Long): StaffTable?

}