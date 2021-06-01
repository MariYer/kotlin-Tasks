package com.mariyer.taskmanager.data.db.model

import androidx.room.ColumnInfo
import com.mariyer.taskmanager.data.db.contracts.ProjectContract
import java.time.Instant

class ProjectWithOrgDepartment(
    @ColumnInfo(name = ProjectContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = ProjectContract.Columns.DEPARTMENT_ID)
    val departmentId: Long,
    @ColumnInfo(name = ProjectContract.Columns.TITLE)
    val title: String,
    @ColumnInfo(name = ProjectContract.Columns.DESCRIPTION)
    val description: String,
    @ColumnInfo(name = ProjectContract.Columns.DATE_START)
    val dateStart: Instant,
    @ColumnInfo(name = ProjectContract.Columns.DATE_END)
    val dateEnd: Instant?,
    @ColumnInfo(name = ProjectContract.Columns.ORGANIZATION_ID)
    val organizationId: Long,
    @ColumnInfo(name = ProjectContract.Columns.ORGANIZATION_TITLE)
    val organizationTitle: String,
    @ColumnInfo(name = ProjectContract.Columns.DEPARTMENT_TITLE)
    val departamentTitle: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProjectWithOrgDepartment

        if (id != other.id) return false
        if (departmentId != other.departmentId) return false
        if (title != other.title) return false
        if (description != other.description) return false
        if (dateStart != other.dateStart) return false
        if (dateEnd != other.dateEnd) return false
        if (organizationId != other.organizationId) return false
        if (organizationTitle != other.organizationTitle) return false
        if (departamentTitle != other.departamentTitle) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + departmentId.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + dateStart.hashCode()
        result = 31 * result + (dateEnd?.hashCode() ?: 0)
        result = 31 * result + organizationId.hashCode()
        result = 31 * result + organizationTitle.hashCode()
        result = 31 * result + departamentTitle.hashCode()
        return result
    }
}