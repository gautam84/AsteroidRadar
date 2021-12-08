package com.udacity.asteroidradar

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

fun getToday(): String {
    val calendar = Calendar.getInstance()
    return formatDate(calendar.time)
}

fun getSeventhDay(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, 7)
    return formatDate(calendar.time)
}

@SuppressLint("NewApi", "WeekBasedYear")
private fun formatDate(date: Date): String {
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    return dateFormat.format(date)
}