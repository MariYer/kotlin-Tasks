package com.mariyer.taskmanager.data.db.converters

import androidx.room.TypeConverter

class BooleanConverter {

    @TypeConverter
    fun booleanToInt(value: Boolean): Int {
        return if (value) 1 else 0
    }

    @TypeConverter
    fun intToBoolean(value: Int): Boolean {
        return (value == 1)
    }

}