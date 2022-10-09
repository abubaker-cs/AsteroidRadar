package com.udacity.asteroidradar.main.data

import com.udacity.asteroidradar.data.model.ImageOfDay

/**
 * This class will "HOLD DATA" for:
 * 1. Image of the Day
 */
data class ImageState(
    val image: ImageOfDay?
)

/**
 * Notes:
 * 1. It is not unusual to create classes whose main purpose is to "hold data".
 * 2. In such classes, some standard functionality and some utility functions are often mechanically
 *    derivable from the data.
 * 3. In Kotlin, these are called data classes and are marked with data:
 *
 * data class User(
 *      val name: String,
 *      val age: Int
 * )
 *
 * Reference: https://kotlinlang.org/docs/data-classes.html
 */

