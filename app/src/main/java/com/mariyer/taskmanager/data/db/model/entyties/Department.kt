package com.mariyer.taskmanager.data.db.model.entyties

import androidx.room.*
import com.mariyer.taskmanager.data.db.contracts.DepartmentContract
import com.mariyer.taskmanager.data.db.contracts.OrganizationContract

@Entity(
    tableName = DepartmentContract.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = Organization::class,
            parentColumns = [OrganizationContract.Columns.ID],
            childColumns = [DepartmentContract.Columns.ORGANIZATION_ID],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Department::class,
            parentColumns = [DepartmentContract.Columns.ID],
            childColumns = [DepartmentContract.Columns.PARENT_ID],
            onDelete = ForeignKey.CASCADE
        )
    ],
      indices = [
          Index(DepartmentContract.Columns.ORGANIZATION_ID)
      ]
)
data class Department(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DepartmentContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = DepartmentContract.Columns.ORGANIZATION_ID)
    val organizationId: Long,
    @ColumnInfo(name = DepartmentContract.Columns.PARENT_ID)
    var parentId: Long?,
    @ColumnInfo(name = DepartmentContract.Columns.TITLE)
    var title: String
)