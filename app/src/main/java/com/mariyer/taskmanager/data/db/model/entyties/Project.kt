package com.mariyer.taskmanager.data.db.model.entyties

import androidx.room.*
import com.mariyer.taskmanager.data.db.contracts.DepartmentContract
import com.mariyer.taskmanager.data.db.contracts.ProjectContract
import java.time.Instant

@Entity(
    tableName = ProjectContract.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = Department::class,
            parentColumns = [DepartmentContract.Columns.ID],
            childColumns = [ProjectContract.Columns.DEPARTMENT_ID],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(ProjectContract.Columns.DEPARTMENT_ID)
    ]
)
data class Project(
    @PrimaryKey(autoGenerate = true)
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
    val dateEnd: Instant?
)