package com.mariyer.taskmanager.data.db.dao

import androidx.room.*
import com.mariyer.taskmanager.data.db.contracts.*
import com.mariyer.taskmanager.data.db.model.TaskWithNames
import com.mariyer.taskmanager.data.db.model.entyties.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM ${TaskContract.TABLE_NAME}")
    suspend fun getAllTasks(): List<Task>

    @Query(
        "SELECT task.*, proj.${ProjectContract.Columns.TITLE} AS ${TaskContract.Columns.PROJECT_TITLE},\n" +
                "       req.${EmployerContract.Columns.SURNAME} ||' '|| req.${EmployerContract.Columns.NAME} ||' '|| req.${EmployerContract.Columns.MIDDLE_NAME} AS ${TaskContract.Columns.REQUESTER_NAME},\n" +
                "       ex.${EmployerContract.Columns.SURNAME} ||' '|| ex.${EmployerContract.Columns.NAME} ||' '|| ex.${EmployerContract.Columns.MIDDLE_NAME} AS ${TaskContract.Columns.EXECUTOR_NAME},\n" +
                "       dep.${DepartmentContract.Columns.TITLE} AS ${TaskContract.Columns.DEPARTMENT_FOR_TITLE}, st.${TaskStateContract.Columns.TITLE} AS ${TaskContract.Columns.STATE_TITLE},\n" +
                "       dep.${DepartmentContract.Columns.ORGANIZATION_ID} AS ${TaskContract.Columns.ORGANIZATION_ID}\n" +
                "FROM ${ProjectContract.TABLE_NAME} AS proj JOIN ${TaskContract.TABLE_NAME} AS task ON (proj.${ProjectContract.Columns.ID} = task.${TaskContract.Columns.PROJECT_ID})\n" +
                "JOIN ${TaskStateContract.TABLE_NAME} AS st ON (task.${TaskContract.Columns.STATE_ID} = st.${TaskStateContract.Columns.ID})\n" +
                "JOIN ${EmployerStaffContract.TABLE_NAME} AS reqst ON (task.${TaskContract.Columns.REQUESTER_ID} = reqst.${EmployerStaffContract.Columns.ID})\n" +
                "JOIN ${EmployerContract.TABLE_NAME} AS req ON (reqst.${EmployerStaffContract.Columns.EMPLOYER_ID} = req.${EmployerContract.Columns.ID})\n" +
                "LEFT JOIN ${EmployerStaffContract.TABLE_NAME} AS exst ON (task.${TaskContract.Columns.EXECUTOR_ID} = exst.${EmployerStaffContract.Columns.ID})\n" +
                "LEFT JOIN ${EmployerContract.TABLE_NAME} AS ex ON (exst.${EmployerStaffContract.Columns.EMPLOYER_ID} = ex.${EmployerContract.Columns.ID})\n" +
                "LEFT JOIN ${DepartmentContract.TABLE_NAME} AS dep ON (task.${TaskContract.Columns.DEPARTMENT_FOR_ID} = dep.${DepartmentContract.Columns.ID})\n" +
                "WHERE proj.${ProjectContract.Columns.ID}=:projId"
    )
    fun getTasksByProjectId(projId: Long): Flow<List<TaskWithNames>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(tasks: List<Task>)

    @Update
    suspend fun updateTask(task: Task)

    @Query("DELETE FROM ${TaskContract.TABLE_NAME} WHERE ${TaskContract.Columns.ID} = :taskId")
    suspend fun removeTask(taskId: Long)

    @Query(
        "SELECT task.*, proj.${ProjectContract.Columns.TITLE} AS ${TaskContract.Columns.PROJECT_TITLE},\n" +
                "       req.${EmployerContract.Columns.SURNAME} ||' '|| req.${EmployerContract.Columns.NAME} ||' '|| req.${EmployerContract.Columns.MIDDLE_NAME} AS ${TaskContract.Columns.REQUESTER_NAME},\n" +
                "       ex.${EmployerContract.Columns.SURNAME} ||' '|| ex.${EmployerContract.Columns.NAME} ||' '|| ex.${EmployerContract.Columns.MIDDLE_NAME} AS ${TaskContract.Columns.EXECUTOR_NAME},\n" +
                "       dep.${DepartmentContract.Columns.TITLE} AS ${TaskContract.Columns.DEPARTMENT_FOR_TITLE}, st.${TaskStateContract.Columns.TITLE} AS ${TaskContract.Columns.STATE_TITLE},\n" +
                "       dep.${DepartmentContract.Columns.ORGANIZATION_ID} AS ${TaskContract.Columns.ORGANIZATION_ID}\n" +
                "FROM ${ProjectContract.TABLE_NAME} AS proj JOIN ${TaskContract.TABLE_NAME} AS task ON (proj.${ProjectContract.Columns.ID} = task.${TaskContract.Columns.PROJECT_ID})\n" +
                "JOIN ${TaskStateContract.TABLE_NAME} AS st ON (task.${TaskContract.Columns.STATE_ID} = st.${TaskStateContract.Columns.ID})\n" +
                "JOIN ${EmployerStaffContract.TABLE_NAME} AS reqst ON (task.${TaskContract.Columns.REQUESTER_ID} = reqst.${EmployerStaffContract.Columns.ID})\n" +
                "JOIN ${EmployerContract.TABLE_NAME} AS req ON (reqst.${EmployerStaffContract.Columns.EMPLOYER_ID} = req.${EmployerContract.Columns.ID})\n" +
                "LEFT JOIN ${EmployerStaffContract.TABLE_NAME} AS exst ON (task.${TaskContract.Columns.EXECUTOR_ID} = exst.${EmployerStaffContract.Columns.ID})\n" +
                "LEFT JOIN ${EmployerContract.TABLE_NAME} AS ex ON (exst.${EmployerStaffContract.Columns.EMPLOYER_ID} = ex.${EmployerContract.Columns.ID})\n" +
                "LEFT JOIN ${DepartmentContract.TABLE_NAME} AS dep ON (task.${TaskContract.Columns.DEPARTMENT_FOR_ID} = dep.${DepartmentContract.Columns.ID})\n" +
                "WHERE task.${TaskContract.Columns.ID}=:taskId"
    )
    suspend fun getTaskById(taskId: Long): TaskWithNames?

}