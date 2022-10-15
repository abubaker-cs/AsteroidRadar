package com.udacity.asteroidradar.data

import android.annotation.SuppressLint
import com.udacity.asteroidradar.utils.Constants
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("WeekBasedYear")
fun weeklyRecords(): String {

    //
    val calendar = Calendar.getInstance()

    //
    calendar.add(Calendar.DAY_OF_YEAR, 7)

    //
    val currentTime = calendar.time

    // TODO Bug found: related with SimpleDateFormat()
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())

    //
    return dateFormat.format(currentTime)

}