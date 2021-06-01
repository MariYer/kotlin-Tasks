package com.mariyer.taskmanager.data.db.contracts

object EmployerStaffContract {
    const val TABLE_NAME ="employers_staff"

    object Columns {
        const val ID = "id"
        const val DEPARTMENT_ID = "department_id"
        const val POST_ID = "post_id"
        const val EMPLOYER_ID = "employer_id"
        const val DATE_START = "date_start"
        const val DATE_END = "date_end"
        const val FULL_EMPLOYER_NAME = "full_employer_name"
        const val DEPARTMENT_NAME = "department_name"
        const val POST_NAME = "post_name"
        const val ORGANIZATION_ID = "organization_id"
    }
}