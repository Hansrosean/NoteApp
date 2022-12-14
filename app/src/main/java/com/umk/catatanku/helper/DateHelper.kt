package com.umk.catatanku.helper

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
}