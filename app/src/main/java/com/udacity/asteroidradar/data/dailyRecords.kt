package com.udacity.asteroidradar.data

import com.udacity.asteroidradar.utils.Constants
import java.text.SimpleDateFormat
import java.util.*

fun dailyRecords(): String {
    val calendar = Calendar.getInstance()
    val currentTime = calendar.time
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    return dateFormat.format(currentTime)
}