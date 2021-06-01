package com.mariyer.taskmanager.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mariyer.taskmanager.data.db.dao.*
import com.mariyer.taskmanager.data.db.model.entyties.*
import com.mariyer.taskmanager.data.db.converters.BigDecimalConverter
import com.mariyer.taskmanager.data.db.converters.BooleanConverter
import com.mariyer.taskmanager.data.db.converters.InstantConverter

@Database(
    entities = [Organization::class, Department::class, Post::class, StaffTable::class, Employer::class,
               EmployerStaff::class, Project::class, TaskState::class, Task::class],
    version = EnterpriseDatabase.DB_VERSION
)
@TypeConverters(InstantConverter::class, BigDecimalConverter::class, BooleanConverter::class)
abstract class EnterpriseDatabase: RoomDatabase() {

    abstract fun organizationDao(): OrganizationDao
    abstract fun departmentDao(): DepartmentDao
    abstract fun postDao(): PostDao
    abstract fun staffTableDao(): StaffTableDao
    abstract fun employerDao(): EmployerDao
    abstract fun employerStaffDao(): EmployerStaffDao
    abstract fun projectDao(): ProjectDao
    abstract fun taskStateDao(): TaskStateDao
    abstract fun taskDao(): TaskDao

    companion object {
        const val DB_VERSION = 3
        const val DB_NAME = "enterprise-database"
    }

}