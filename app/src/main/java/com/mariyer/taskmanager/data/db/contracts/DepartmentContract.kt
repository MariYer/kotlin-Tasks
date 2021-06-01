package com.mariyer.taskmanager.data.db.contracts

object DepartmentContract {
    const val TABLE_NAME = "departments"

    object Columns {
        const val ID = "id"
        const val ORGANIZATION_ID = "organization_id"
        const val PARENT_ID = "parent_id"
        const val TITLE = "title"
        const val LEVEL = "level"
        const val PARENT_TITLE = "parent_title"
        const val HIER_TITLE = "hier_title"
        const val FULL_TITLE = "full_title"
    }
}