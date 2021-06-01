package com.mariyer.taskmanager.data.db.model

import androidx.room.ColumnInfo
import com.mariyer.taskmanager.data.db.contracts.TaskContract
import java.time.Instant

class TaskWithNames (
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
    val startedAt: Instant,
    @ColumnInfo(name = TaskContract.Columns.ENDED_AT)
    val endedAt: Instant?,
    @ColumnInfo(name = TaskContract.Columns.DEPARTMENT_FOR_ID)
    val departmentForId: Long?,
    @ColumnInfo(name = TaskContract.Columns.PROJECT_TITLE)
    val projectTitle: String,
    @ColumnInfo(name = TaskContract.Columns.REQUESTER_NAME)
    val requesterName: String,
    @ColumnInfo(name = TaskContract.Columns.EXECUTOR_NAME)
    val executorName: String,
    @ColumnInfo(name = TaskContract.Columns.DEPARTMENT_FOR_TITLE)
    val departmentForTitle: String?,
    @ColumnInfo(name = TaskContract.Columns.STATE_TITLE)
    val stateTitle: String,
    @ColumnInfo(name = TaskContract.Columns.ORGANIZATION_ID)
    val organizationId: Long
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TaskWithNames

        if (id != other.id) return false
        if (projectId != other.projectId) return false
        if (requesterId != other.requesterId) return false
        if (executorId != other.executorId) return false
        if (stateId != other.stateId) return false
        if (title != other.title) return false
        if (priority != other.priority) return false
        if (description != other.description) return false
        if (createdAt != other.createdAt) return false
        if (deadline != other.deadline) return false
        if (startedAt != other.startedAt) return false
        if (endedAt != other.endedAt) return false
        if (departmentForId != other.departmentForId) return false
        if (projectTitle != other.projectTitle) return false
        if (requesterName != other.requesterName) return false
        if (executorName != other.executorName) return false
        if (departmentForTitle != other.departmentForTitle) return false
        if (stateTitle != other.stateTitle) return false
        if (organizationId != other.organizationId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + projectId.hashCode()
        result = 31 * result + requesterId.hashCode()
        result = 31 * result + (executorId?.hashCode() ?: 0)
        result = 31 * result + stateId.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + priority
        result = 31 * result + description.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + deadline.hashCode()
        result = 31 * result + startedAt.hashCode()
        result = 31 * result + (endedAt?.hashCode() ?: 0)
        result = 31 * result + (departmentForId?.hashCode() ?: 0)
        result = 31 * result + projectTitle.hashCode()
        result = 31 * result + requesterName.hashCode()
        result = 31 * result + executorName.hashCode()
        result = 31 * result + (departmentForTitle?.hashCode() ?: 0)
        result = 31 * result + stateTitle.hashCode()
        result = 31 * result + organizationId.hashCode()
        return result
    }
}