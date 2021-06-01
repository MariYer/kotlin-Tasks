package com.mariyer.taskmanager.data.db.model.entyties

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mariyer.taskmanager.data.db.contracts.TaskStateContract

@Entity(tableName = TaskStateContract.TABLE_NAME)
data class TaskState(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TaskStateContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = TaskStateContract.Columns.TITLE)
    val title: String
)