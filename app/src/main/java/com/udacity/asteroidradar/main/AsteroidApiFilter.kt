package com.udacity.asteroidradar.main

/**
 * It defines constants to match the query values our web service expects.
 */
enum class AsteroidApiFilter(val value: String) {
    SHOW_WEEK("week"),
    SHOW_TODAY("today"),
    SHOW_SAVED("saved")
}