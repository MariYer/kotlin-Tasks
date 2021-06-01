package com.mariyer.taskmanager.data.db.model

import androidx.room.ColumnInfo
import com.mariyer.taskmanager.data.db.contracts.StaffTableContract
import java.math.BigDecimal

class StaffTableWithNames (
    @ColumnInfo(name = StaffTableContract.Columns.DEPARTMENT_NAME)
    val departmentTitle: String,
    @ColumnInfo(name = StaffTableContract.Columns.POST_NAME)
    val postTitle: String,
    @ColumnInfo(name = StaffTableContract.Columns.DEPARTMENT_ID)
    val departmentId: Long,
    @ColumnInfo(name = StaffTableContract.Columns.POST_ID)
    val postId: Long,
    @ColumnInfo(name = StaffTableContract.Columns.IS_CHIEF)
    val isChief: Boolean,
    @ColumnInfo(name = StaffTableContract.Columns.BASIC_SALARY)
    val basicSalary: BigDecimal?,
    @ColumnInfo(name = StaffTableContract.Columns.MAX_COUNT, defaultValue = "1")
    val maxCount: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StaffTableWithNames

        if (departmentTitle != other.departmentTitle) return false
        if (postTitle != other.postTitle) return false
        if (departmentId != other.departmentId) return false
        if (postId != other.postId) return false
        if (isChief != other.isChief) return false
        if (basicSalary != other.basicSalary) return false
        if (maxCount != other.maxCount) return false

        return true
    }

    override fun hashCode(): Int {
        var result = departmentTitle.hashCode()
        result = 31 * result + postTitle.hashCode()
        result = 31 * result + departmentId.hashCode()
        result = 31 * result + postId.hashCode()
        result = 31 * result + isChief.hashCode()
        result = 31 * result + (basicSalary?.hashCode() ?: 0)
        result = 31 * result + maxCount
        return result
    }
}