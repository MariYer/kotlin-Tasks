package com.mariyer.taskmanager.data.db.model

import androidx.room.ColumnInfo
import com.mariyer.taskmanager.data.db.contracts.DepartmentContract

data class DepartmentWithHierarchy(
    @ColumnInfo(name = DepartmentContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = DepartmentContract.Columns.ORGANIZATION_ID)
    val organizationId: Long,
    @ColumnInfo(name = DepartmentContract.Columns.PARENT_ID)
    val parentId: Long?,
    @ColumnInfo(name = DepartmentContract.Columns.TITLE)
    val title: String,
    @ColumnInfo(name = DepartmentContract.Columns.LEVEL)
    val level: Int,
    @ColumnInfo(name = DepartmentContract.Columns.PARENT_TITLE)
    val parentTitle: String?,
    @ColumnInfo(name = DepartmentContract.Columns.FULL_TITLE)
    val fullTitle: String?,
    @ColumnInfo(name = DepartmentContract.Columns.HIER_TITLE)
    val hierTitle: String?
) {
}