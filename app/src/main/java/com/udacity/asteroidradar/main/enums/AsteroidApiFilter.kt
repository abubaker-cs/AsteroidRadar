package com.udacity.asteroidradar.main.enums

/**
 * It defines constants to match the query values our web asteroidAPI expects.
 */
enum class AsteroidApiFilter(val value: String) {
    SHOW_WEEK("week"),
    SHOW_TODAY("today"),
    SHOW_SAVED("saved")
}