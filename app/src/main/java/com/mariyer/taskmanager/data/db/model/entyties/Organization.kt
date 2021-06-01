package com.mariyer.taskmanager.data.db.model.entyties

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mariyer.taskmanager.data.db.contracts.OrganizationContract


@Entity(tableName = OrganizationContract.TABLE_NAME)
data class Organization(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = OrganizationContract.Columns.ID)
        val id: Long,
        @ColumnInfo(name = OrganizationContract.Columns.TITLE)
        val title: String,
        @ColumnInfo(name = OrganizationContract.Columns.FULL_TITLE)
        val fullTitle: String,
        @ColumnInfo(name = OrganizationContract.Columns.ENTITY)
        val entity: Int,
        @ColumnInfo(name = OrganizationContract.Columns.INN)
        val inn: String,
        @ColumnInfo(name = OrganizationContract.Columns.NOTES)
        val notes: String
)