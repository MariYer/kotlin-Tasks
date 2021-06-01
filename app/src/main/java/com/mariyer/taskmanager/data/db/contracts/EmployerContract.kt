package com.mariyer.taskmanager.data.db.contracts

object EmployerContract {
    const val TABLE_NAME ="employers"

    object Columns {
        const val ID = "id"
        const val ORGANIZATION_ID = "organization_id"
        const val PHOTO_URL = "photo_url"
        const val SURNAME = "surname"
        const val NAME = "name"
        const val MIDDLE_NAME = "middle_name"
        const val BIRTHDAY = "birthday"
        const val SEX = "sex"
        const val PHONE = "phone"
        const val EMAIL = "email"
        const val ADDRESS = "address"
    }
}