package com.udacity.asteroidradar.utils

import com.udacity.asteroidradar.BuildConfig

object Constants {

    //
    const val BASE_URL = "https://api.nasa.gov/"

    //
    const val API_KEY = BuildConfig.nasaApiKey

    /**
     * Issue: Week Based Year
     * The DateTimeFormatter pattern YYYY returns the week based year, not the era-based year.
     * This means that 12/29/2019 will format to 2019, but 12/30/2019 will format to 2020!
     *
     * Reference:
     * https://googlesamples.github.io/android-custom-lint-rules/checks/WeekBasedYear.md.html
     *
     * Solution:
     * YYYY-MM-dd causes @SuppressLint("WeekBasedYear") ERROR, so I am replacing it with MM/dd/yyyy
     */
    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"

    //
    const val DEFAULT_END_DATE_DAYS = 7

}

