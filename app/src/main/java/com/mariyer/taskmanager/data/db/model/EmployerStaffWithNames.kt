package com.mariyer.taskmanager.data.db.model

import androidx.room.ColumnInfo
import com.mariyer.taskmanager.data.db.contracts.EmployerStaffContract
import java.time.Instant

class EmployerStaffWithNames(
    @ColumnInfo(name = EmployerStaffContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = EmployerStaffContract.Columns.DEPARTMENT_ID)
    val departmentId: Long,
    @ColumnInfo(name = EmployerStaffContract.Columns.POST_ID)
    val postId: Long,
    @ColumnInfo(name = EmployerStaffContract.Columns.EMPLOYER_ID)
    val employerId: Long,
    @ColumnInfo(name = EmployerStaffContract.Columns.DATE_START)
    val dateStart: Instant,
    @ColumnInfo(name = EmployerStaffContract.Columns.DATE_END)
    val dateEnd: Instant?,
    @ColumnInfo(name = EmployerStaffContract.Columns.FULL_EMPLOYER_NAME)
    val fullEmployerName: String,
    @ColumnInfo(name = EmployerStaffContract.Columns.DEPARTMENT_NAME)
    val departmentName: String,
    @ColumnInfo(name = EmployerStaffContract.Columns.POST_NAME)
    val postName: String,
    @ColumnInfo(name = EmployerStaffContract.Columns.ORGANIZATION_ID)
    val organizationId: Long?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EmployerStaffWithNames

        if (id != other.id) return false
        if (departmentId != other.departmentId) return false
        if (postId != other.postId) return false
        if (employerId != other.employerId) return false
        if (dateStart != other.dateStart) return false
        if (dateEnd != other.dateEnd) return false
        if (fullEmployerName != other.fullEmployerName) return false
        if (departmentName != other.departmentName) return false
        if (postName != other.postName) return false
        if (organizationId != other.organizationId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + departmentId.hashCode()
        result = 31 * result + postId.hashCode()
        result = 31 * result + employerId.hashCode()
        result = 31 * result + dateStart.hashCode()
        result = 31 * result + (dateEnd?.hashCode() ?: 0)
        result = 31 * result + fullEmployerName.hashCode()
        result = 31 * result + departmentName.hashCode()
        result = 31 * result + postName.hashCode()
        result = 31 * result + (organizationId?.hashCode() ?: 0)
        return result
    }
}