package com.mariyer.taskmanager.data.db.model.entyties

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.mariyer.taskmanager.data.db.contracts.DepartmentContract
import com.mariyer.taskmanager.data.db.contracts.PostContract
import com.mariyer.taskmanager.data.db.contracts.StaffTableContract
import java.math.BigDecimal

@Entity(
    tableName = StaffTableContract.TABLE_NAME,
    primaryKeys = [StaffTableContract.Columns.DEPARTMENT_ID, StaffTableContract.Columns.POST_ID],
    foreignKeys = [
        ForeignKey(
            entity = Department::class,
            parentColumns = [DepartmentContract.Columns.ID],
            childColumns = [StaffTableContract.Columns.DEPARTMENT_ID]
        ),
        ForeignKey(
            entity = Post::class,
            parentColumns = [PostContract.Columns.ID],
            childColumns = [StaffTableContract.Columns.POST_ID]
        )
    ]
)
data class StaffTable(
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
)