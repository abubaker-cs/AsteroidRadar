package com.udacity.asteroidradar.main.enums

/**
 * It defines constants to match the query values which our web asteroidAPI expects.
 */
enum class AsteroidApiFilter(val value: String) {

    // 7 Days Record
    SHOW_CURRENT_WEEK_DATA("week"),

    // Today's Record
    SHOW_TODAY_DATA("today"),

    // Offline Saved Data
    SHOW_OFFLINE_SAVED_DATA("saved")
}