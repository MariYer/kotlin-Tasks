package com.mariyer.taskmanager.data.db.contracts

object TaskContract {
    const val TABLE_NAME = "tasks"

    object Columns {
        const val ID = "id"
        const val PROJECT_ID = "project_id"
        const val REQUESTER_ID = "requester_id"
        const val EXECUTOR_ID = "executor_id"
        const val STATE_ID = "state_id"
        const val TITLE = "title"
        const val PRIORITY = "priority"
        const val DESCRIPTION = "description"
        const val CREATED_AT = "created_at"
        const val DEADLINE = "deadline"
        const val STARTED_AT = "started_at"
        const val ENDED_AT = "ended_at"
        const val DEPARTMENT_FOR_ID = "department_for_id"
        const val PROJECT_TITLE = "project_title"
        const val REQUESTER_NAME = "requester_name"
        const val EXECUTOR_NAME = "executor_name"
        const val DEPARTMENT_FOR_TITLE = "department_for_title"
        const val STATE_TITLE = "state_title"
        const val ORGANIZATION_ID = "organization_id"
    }
}