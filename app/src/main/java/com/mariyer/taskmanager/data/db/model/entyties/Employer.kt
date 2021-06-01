package com.mariyer.taskmanager.data.db.model.entyties

import androidx.room.*
import com.mariyer.taskmanager.data.db.contracts.EmployerContract
import com.mariyer.taskmanager.data.db.contracts.OrganizationContract
import java.time.Instant

@Entity(
    tableName = EmployerContract.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = Organization::class,
            parentColumns = [OrganizationContract.Columns.ID],
            childColumns = [EmployerContract.Columns.ORGANIZATION_ID],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(EmployerContract.Columns.ORGANIZATION_ID)
    ]
)
data class Employer(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = EmployerContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = EmployerContract.Columns.ORGANIZATION_ID)
    val organizationId: Long,
    @ColumnInfo(name = EmployerContract.Columns.PHOTO_URL)
    val photoUrl: String?,
    @ColumnInfo(name = EmployerContract.Columns.SURNAME)
    val surname: String,
    @ColumnInfo(name = EmployerContract.Columns.NAME)
    val name: String,
    @ColumnInfo(name = EmployerContract.Columns.MIDDLE_NAME)
    val middleName: String?,
    @ColumnInfo(name = EmployerContract.Columns.BIRTHDAY)
    val birthday: Instant?,
    @ColumnInfo(name = EmployerContract.Columns.SEX)
    val sex: Char,
    @ColumnInfo(name = EmployerContract.Columns.PHONE)
    val phone: String,
    @ColumnInfo(name = EmployerContract.Columns.EMAIL)
    val email: String?,
    @ColumnInfo(name = EmployerContract.Columns.ADDRESS)
    val address: String
)