package com.mariyer.taskmanager.data.db.model.entyties

import androidx.room.*
import com.mariyer.taskmanager.data.db.contracts.OrganizationContract
import com.mariyer.taskmanager.data.db.contracts.PostContract

@Entity(
    tableName = PostContract.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = Organization::class,
            parentColumns = [OrganizationContract.Columns.ID],
            childColumns = [PostContract.Columns.ORGANIZATION_ID],
            onDelete = ForeignKey.CASCADE
        )],
    indices = [
        Index(PostContract.Columns.ORGANIZATION_ID)
    ]
)
data class Post(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PostContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = PostContract.Columns.ORGANIZATION_ID)
    val organizationId: Long,
    @ColumnInfo(name = PostContract.Columns.TITLE)
    var title: String
)