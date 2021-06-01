package com.mariyer.taskmanager.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
val dateFormatter: DateTimeFormatter = DateTimeFormatter
    .ofPattern("dd/MM/yyyy HH:mm:ss")
    .withLocale(Locale.US)

@RequiresApi(Build.VERSION_CODES.O)
fun stringToInstant(stringValue: String): Instant? {
    if (stringValue.isBlank() || stringValue.isEmpty()) {
        return null
    }
    if (stringValue.isEmpty() || stringValue.isBlank()) {
        return null
    }
    val fullDateTime = "${stringValue} 00:00:00"
    val localDateTime = LocalDateTime.parse(fullDateTime, dateFormatter)
    return localDateTime.atZone(ZoneId.systemDefault()).toInstant()
}

fun getIdFromSelected(selectedText: String?): Long? {
    val p1 = selectedText?.indexOf("=") ?: -1
    val p2 = selectedText?.lastIndexOf(")") ?: -1
    var idStr = ""
    if (p1 > 0 && p2 > 0) {
        selectedText?.let {
            idStr = it.substring(p1 + 1, p2)
        }
    }
    return idStr.toLongOrNull()
}
