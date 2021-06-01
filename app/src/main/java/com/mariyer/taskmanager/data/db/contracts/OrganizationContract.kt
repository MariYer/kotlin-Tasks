package com.mariyer.taskmanager.data.db.contracts

object OrganizationContract {
    const val TABLE_NAME = "organizations"

    object Columns {
        const val ID = "id"
        const val TITLE = "title"
        const val FULL_TITLE = "full_title"
        const val ENTITY = "entity"
        const val INN = "inn"
        const val NOTES = "notes"
    }
}