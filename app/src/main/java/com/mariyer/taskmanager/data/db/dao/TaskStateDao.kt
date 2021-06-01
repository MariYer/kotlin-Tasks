package com.mariyer.taskmanager.data.db.dao

import androidx.room.*
import com.mariyer.taskmanager.data.db.contracts.TaskStateContract
import com.mariyer.taskmanager.data.db.model.entyties.TaskState
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskStateDao {

    @Query("SELECT * FROM ${TaskStateContract.TABLE_NAME}")
    fun getAllTaskStates(): Flow<List<TaskState>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskStates(states: List<TaskState>)

    @Update
    suspend fun updateTaskState(state: TaskState)

    @Query("DELETE FROM ${TaskStateContract.TABLE_NAME} WHERE ${TaskStateContract.Columns.ID} = :stateId")
    suspend fun removeTaskState(stateId: Long)

    @Query("SELECT * FROM ${TaskStateContract.TABLE_NAME} WHERE ${TaskStateContract.Columns.ID} = :stateId")
    suspend fun getTaskStateById(stateId: Long): TaskState?

}