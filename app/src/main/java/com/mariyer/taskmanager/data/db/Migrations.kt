package com.mariyer.taskmanager.data.db

import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        Log.d("migrations", "migration_1_2")
        database.execSQL("ALTER TABLE employers ADD COLUMN organization_id INTEGER")
    }
}
val MIGRATION_2_3: Migration = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        Log.d("migrations", "migration_2_3")
        database.execSQL("ALTER TABLE tasks ADD COLUMN department_for_id INTEGER")
    }
}
//val MIGRATION_3_4: Migration = object : Migration(3, 4) {
//    override fun migrate(database: SupportSQLiteDatabase) {
//        Log.d("migrations", "migration_3_4")
//        database.execSQL("INSERT INTO tasks_states VALUES(1,'Подготовлена')")
//        database.execSQL("INSERT INTO tasks_states VALUES(2,'Согласована')")
//        database.execSQL("INSERT INTO tasks_states VALUES(3,'На исполнении')")
//        database.execSQL("INSERT INTO tasks_states VALUES(4,'На тестировании')")
//        database.execSQL("INSERT INTO tasks_states VALUES(5,'Завершена')")
//    }
//}
