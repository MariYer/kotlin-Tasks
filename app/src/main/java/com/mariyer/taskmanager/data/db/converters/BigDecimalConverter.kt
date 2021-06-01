package com.mariyer.taskmanager.data.db.converters

import androidx.room.TypeConverter
import java.math.BigDecimal

class BigDecimalConverter {

    @TypeConverter
    fun bigDecimalToString(value:BigDecimal?): String? {
        return if (value == null) {
            null
        } else {
            value.toString()
        }
    }

    @TypeConverter
    fun stringToBigDecimal(valueStr: String?): BigDecimal? {
        return if (valueStr == null) {
            null
        } else {
            valueStr.toBigDecimal()
        }
    }
}