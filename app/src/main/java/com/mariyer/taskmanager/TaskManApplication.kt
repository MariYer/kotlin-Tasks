package com.mariyer.taskmanager

import android.app.Application
import com.mariyer.taskmanager.data.db.Database

class TaskManApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Database.init(this)
    }
}