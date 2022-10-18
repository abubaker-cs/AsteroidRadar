package com.udacity.asteroidradar.data

import android.annotation.SuppressLint
import com.udacity.asteroidradar.utils.Constants
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("WeekBasedYear")
fun weeklyRecords(): String {

    // This method is used with calendar object to get the instance of calendar according to current time zone
    // =======================================================================================================
    // It will return a calendar of a type appropriate to the locale, whose time fields have been initialized
    // with the current date and time
    //
    // Reference: https://developer.android.com/reference/kotlin/android/icu/util/Calendar
    val rightNow = Calendar.getInstance()

    // We are assigning a parameter and asking the system to provide us Weekly Calendar (7-Days)
    rightNow.add(Calendar.DAY_OF_YEAR, 7)

    // Get the Current Time
    val currentTime = rightNow.time

    // Selected Pattern: YYYY-MM-dd (Defined in Constants.kt)
    // ======================================================
    // SimpleDateFormat() is used for formatting and parsing dates in a locale-sensitive manner.
    // It allows for:
    // 1. Formatting (date → text),
    // 2. Parsing (text → date), and
    // 3. Normalization
    val dateFormattingPattern =
        SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())

    // Apply the selected pattern (YYYY-MM-dd) to the current date/time
    return dateFormattingPattern.format(currentTime)

}

/**
 * Linting Issue
 * =============
 * Week Based Year The DateTimeFormatter pattern YYYY returns the week based year, not the era-based year.
 * This means that 12/29/2019 will format to 2019, but 12/30/2019 will format to 2020!
 *
 * Solution: YYYY-MM-dd causes @SuppressLint("WeekBasedYear") ERROR, so I am replacing it with MM/dd/yyyy
 *
 * Reference: https://googlesamples.github.io/android-custom-lint-rules/checks/WeekBasedYear.md.html
 */