package com.mariyer.taskmanager.data.db.contracts

object StaffTableContract {
    const val TABLE_NAME = "staff_table"

    object Columns {
        const val DEPARTMENT_ID = "department_id"
        const val  POST_ID = "post_id"
        const val IS_CHIEF = "is_chief"
        const val BASIC_SALARY = "basic_salary"
        const val MAX_COUNT = "max_count"
        const val DEPARTMENT_NAME = "department_name"
        const val  POST_NAME = "post_name"
    }
}