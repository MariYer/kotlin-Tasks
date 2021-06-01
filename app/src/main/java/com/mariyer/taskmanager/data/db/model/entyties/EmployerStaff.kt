package com.mariyer.taskmanager.data.db.model.entyties

import androidx.room.*
import com.mariyer.taskmanager.data.db.contracts.EmployerContract
import com.mariyer.taskmanager.data.db.contracts.EmployerStaffContract
import com.mariyer.taskmanager.data.db.contracts.StaffTableContract
import java.time.Instant

@Entity(
    tableName = EmployerStaffContract.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = StaffTable::class,
            parentColumns = [StaffTableContract.Columns.DEPARTMENT_ID, StaffTableContract.Columns.POST_ID],
            childColumns = [EmployerStaffContract.Columns.DEPARTMENT_ID, EmployerStaffContract.Columns.POST_ID],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = Employer::class,
            parentColumns = [EmployerContract.Columns.ID],
            childColumns = [EmployerStaffContract.Columns.EMPLOYER_ID],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(EmployerStaffContract.Columns.EMPLOYER_ID),
        Index(EmployerStaffContract.Columns.DEPARTMENT_ID),
        Index(EmployerStaffContract.Columns.POST_ID)
    ]
)
data class EmployerStaff(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = EmployerStaffContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = EmployerStaffContract.Columns.DEPARTMENT_ID)
    val departmentId: Long,
    @ColumnInfo(name = EmployerStaffContract.Columns.POST_ID)
    val postId: Long,
    @ColumnInfo(name = EmployerStaffContract.Columns.EMPLOYER_ID)
    var employerId: Long,
    @ColumnInfo(name = EmployerStaffContract.Columns.DATE_START)
    val dateStart: Instant,
    @ColumnInfo(name = EmployerStaffContract.Columns.DATE_END)
    val dateEnd: Instant?
)