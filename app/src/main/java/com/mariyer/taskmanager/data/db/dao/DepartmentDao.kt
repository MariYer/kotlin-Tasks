package com.mariyer.taskmanager.data.db.dao

import androidx.room.*
import com.mariyer.taskmanager.data.db.contracts.DepartmentContract
import com.mariyer.taskmanager.data.db.model.entyties.Department
import com.mariyer.taskmanager.data.db.model.DepartmentWithHierarchy
import kotlinx.coroutines.flow.Flow

@Dao
interface DepartmentDao {

    @Query("SELECT * FROM ${DepartmentContract.TABLE_NAME}")
    suspend fun getAllDepartments(): List<Department>

    @Query("SELECT * FROM ${DepartmentContract.TABLE_NAME} WHERE ${DepartmentContract.Columns.ORGANIZATION_ID}=:orgId")
    suspend fun getDepartmentsByOrgId(orgId: Long): List<Department>


    @Query("SELECT * FROM ${DepartmentContract.TABLE_NAME} WHERE ${DepartmentContract.Columns.ID} = :depId")
    suspend fun getDepartmentById(depId: Long): Department?

    @Query("WITH RECURSIVE\n" +
            "  enterprise_structure(id,organization_id,parent_id,title,level,parent_title,full_title) AS (\n" +
            "    SELECT top.${DepartmentContract.Columns.ID}, top.${DepartmentContract.Columns.ORGANIZATION_ID}, top.${DepartmentContract.Columns.PARENT_ID}, top.${DepartmentContract.Columns.TITLE}, 0, '', top.${DepartmentContract.Columns.TITLE} \n" +
            "    FROM ${DepartmentContract.TABLE_NAME} AS top WHERE top.${DepartmentContract.Columns.PARENT_ID} IS NULL\n" +
            "    UNION ALL\n" +
            "    SELECT dep.${DepartmentContract.Columns.ID}, dep.${DepartmentContract.Columns.ORGANIZATION_ID}, dep.${DepartmentContract.Columns.PARENT_ID}, dep.${DepartmentContract.Columns.TITLE}, enterpr.level+1, enterpr.title, enterpr.title || '/' || dep.${DepartmentContract.Columns.TITLE} \n" +
            "      FROM ${DepartmentContract.TABLE_NAME} AS dep JOIN enterprise_structure AS enterpr ON dep.${DepartmentContract.Columns.PARENT_ID}=enterpr.${DepartmentContract.Columns.ID}\n" +
            "     ORDER BY 7\n" +
            "  )" +
            "SELECT id, organization_id, parent_id, title, level, parent_title, full_title, substr('..........',1,level*3) || title AS hier_title FROM enterprise_structure\n" +
            "WHERE organization_id = :orgId")
    fun getDepartmentsWithHier(orgId: Long): Flow<List<DepartmentWithHierarchy>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDepartments(departs: List<Department>)

    @Update
    suspend fun updateDepartment(department: Department)

    @Query("DELETE FROM ${DepartmentContract.TABLE_NAME} WHERE ${DepartmentContract.Columns.ID} = :depId")
    suspend fun removeDepartment(depId: Long)

}