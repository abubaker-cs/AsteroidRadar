package com.udacity.asteroidradar.network

import android.annotation.SuppressLint
import com.udacity.asteroidradar.data.model.Asteroid
import com.udacity.asteroidradar.utils.Constants
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

/**
 * Being used in AsteroidsWorker.kt file
 */
fun parseAsteroidsJsonResult(jsonResult: JSONObject): ArrayList<Asteroid> {

    // JSON Object: near_earth_objects
    val nearEarthObjectsJson = jsonResult.getJSONObject("near_earth_objects")

    // Asteroid List
    val asteroidList = ArrayList<Asteroid>()

    // Formatted Week (Calendar)
    val nextSevenDaysFormattedDates = getNextSevenDaysFormattedDates()

    /**
     * Loop for conversion
     */
    for (formattedDate in nextSevenDaysFormattedDates) {

        //
        if (nearEarthObjectsJson.has(formattedDate)) {

            //
            val dateAsteroidJsonArray = nearEarthObjectsJson.getJSONArray(formattedDate)

            for (i in 0 until dateAsteroidJsonArray.length()) {

                // JSON Object
                val asteroidJson = dateAsteroidJsonArray.getJSONObject(i)

                // ID
                val id = asteroidJson.getLong("id")

                // Code Name
                val codename = asteroidJson.getString("name")

                // Absolute Magnitude
                val absoluteMagnitude = asteroidJson.getDouble("absolute_magnitude_h")

                // Estimated Diameter
                val estimatedDiameter = asteroidJson.getJSONObject("estimated_diameter")
                    .getJSONObject("kilometers").getDouble("estimated_diameter_max")

                // Close Approach Date
                val closeApproachData = asteroidJson
                    .getJSONArray("close_approach_data").getJSONObject(0)

                // Relative Velocity
                val relativeVelocity = closeApproachData.getJSONObject("relative_velocity")
                    .getDouble("kilometers_per_second")

                // Distance from the Earth, measured in the Astronomical Unit (au)
                val distanceFromEarth = closeApproachData.getJSONObject("miss_distance")
                    .getDouble("astronomical")

                // Hazard Status
                val isPotentiallyHazardous = asteroidJson
                    .getBoolean("is_potentially_hazardous_asteroid")

                // Prepare request format using the @Entity = table_asteroid (data/model/Asteroid.kt)
                val asteroid = Asteroid(
                    id,
                    codename,
                    formattedDate,
                    absoluteMagnitude,
                    estimatedDiameter,
                    relativeVelocity,
                    distanceFromEarth,
                    isPotentiallyHazardous
                )

                // Add Data
                asteroidList.add(asteroid)
            }
        }
    }

    return asteroidList
}

/**
 *
 */
@SuppressLint("WeekBasedYear")
private fun getNextSevenDaysFormattedDates(): ArrayList<String> {

    // Prepare a blank ArrayList
    val formattedDateList = ArrayList<String>()

    // This method is used with calendar object to get the instance of calendar according to current time zone
    // =======================================================================================================
    // It will return a calendar of a type appropriate to the locale, whose time fields have been initialized
    // with the current date and time
    //
    // Reference: https://developer.android.com/reference/kotlin/android/icu/util/Calendar
    val calendar = Calendar.getInstance()

    // Loop until the last day of the week
    for (i in 0..Constants.DEFAULT_END_DATE_DAYS) {

        // Get the Current Time
        val currentTime = calendar.time

        // Selected Pattern: YYYY-MM-dd (Defined in Constants.kt)
        // ======================================================
        // SimpleDateFormat() is used for formatting and parsing dates in a locale-sensitive manner.
        // It allows for:
        // 1. Formatting (date → text),
        // 2. Parsing (text → date), and
        // 3. Normalization
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())

        // Apply the selected pattern (YYYY-MM-dd) to the current date/time
        formattedDateList.add(dateFormat.format(currentTime))

        // Adds or subtracts the specified amount of time to the given calendar field, based on the calendar's rules.
        calendar.add(Calendar.DAY_OF_YEAR, 1)

    }

    // Return the Formatted Date List
    return formattedDateList

}
