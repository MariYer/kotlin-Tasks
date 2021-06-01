package com.mariyer.taskmanager.data.db

import android.content.Context
import androidx.room.Room

object Database {
    lateinit var instance: EnterpriseDatabase
        private set

    fun init(context: Context) {
        instance = Room.databaseBuilder(
            context,
            EnterpriseDatabase::class.java,
            EnterpriseDatabase.DB_NAME
        )
            .addMigrations(MIGRATION_1_2)
            .addMigrations(MIGRATION_2_3)
            .build()

    }
}