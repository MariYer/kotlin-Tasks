package com.mariyer.taskmanager.data.db.contracts

object PostContract {
    const val TABLE_NAME = "posts"

    object Columns {
        const val ID = "id"
        const val ORGANIZATION_ID = "organization_id"
        const val TITLE = "title"
    }
}