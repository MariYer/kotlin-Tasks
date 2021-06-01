package com.mariyer.taskmanager.data.db.model.entyties

import androidx.room.*
import com.mariyer.taskmanager.data.db.contracts.*
import java.time.Instant

@Entity(
    tableName = TaskContract.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = Project::class,
            parentColumns = [ProjectContract.Columns.ID],
            childColumns = [TaskContract.Columns.PROJECT_ID],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = EmployerStaff::class,
            parentColumns = [EmployerStaffContract.Columns.ID],
            childColumns = [TaskContract.Columns.REQUESTER_ID],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = EmployerStaff::class,
            parentColumns = [EmployerStaffContract.Columns.ID],
            childColumns = [TaskContract.Columns.EXECUTOR_ID],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = TaskState::class,
            parentColumns = [TaskStateContract.Columns.ID],
            childColumns = [TaskContract.Columns.STATE_ID],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = Department::class,
            parentColumns = [DepartmentContract.Columns.ID],
            childColumns = [TaskContract.Columns.DEPARTMENT_FOR_ID],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(TaskContract.Columns.PROJECT_ID),
        Index(TaskContract.Columns.REQUESTER_ID),
        Index(TaskContract.Columns.EXECUTOR_ID),
        Index(TaskContract.Columns.STATE_ID),
        Index(TaskContract.Columns.DEPARTMENT_FOR_ID)
    ]
)
data class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TaskContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = TaskContract.Columns.PROJECT_ID)
    val projectId: Long,
    @ColumnInfo(name = TaskContract.Columns.REQUESTER_ID)
    val requesterId: Long,
    @ColumnInfo(name = TaskContract.Columns.EXECUTOR_ID)
    val executorId: Long?,
    @ColumnInfo(name = TaskContract.Columns.STATE_ID)
    val stateId: Long,
    @ColumnInfo(name = TaskContract.Columns.TITLE)
    val title: String,
    @ColumnInfo(name = TaskContract.Columns.PRIORITY)
    val priority: Int,
    @ColumnInfo(name = TaskContract.Columns.DESCRIPTION)
    val description: String,
    @ColumnInfo(name = TaskContract.Columns.CREATED_AT)
    val createdAt: Instant,
    @ColumnInfo(name = TaskContract.Columns.DEADLINE)
    val deadline: Instant,
    @ColumnInfo(name = TaskContract.Columns.STARTED_AT)
    val startedAt: Instant?,
    @ColumnInfo(name = TaskContract.Columns.ENDED_AT)
    val endedAt: Instant?,
    @ColumnInfo(name = TaskContract.Columns.DEPARTMENT_FOR_ID)
    val departmentForId: Long?
)