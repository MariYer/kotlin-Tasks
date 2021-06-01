package com.mariyer.taskmanager.data.db.contracts

object ProjectContract {
    const val TABLE_NAME = "projects"

    object Columns {
        const val ID = "id"
        const val DEPARTMENT_ID = "department_id"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val DATE_START = "date_start"
        const val DATE_END = "date_end"
        const val ORGANIZATION_ID = "organization_id"
        const val ORGANIZATION_TITLE = "organization_title"
        const val DEPARTMENT_TITLE = "department_title"
    }
}