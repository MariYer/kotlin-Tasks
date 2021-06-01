package com.mariyer.taskmanager.data.db.model

import androidx.room.Embedded
import androidx.room.Relation
import com.mariyer.taskmanager.data.db.contracts.EmployerContract
import com.mariyer.taskmanager.data.db.contracts.EmployerStaffContract
import com.mariyer.taskmanager.data.db.model.entyties.Employer
import com.mariyer.taskmanager.data.db.model.entyties.EmployerStaff

data class EmployerWithStaff(
    @Embedded
    val employer: Employer,
    @Relation(
        parentColumn = EmployerContract.Columns.ID,
        entityColumn = EmployerStaffContract.Columns.EMPLOYER_ID
    )
    val staffs: List<EmployerStaff>
)