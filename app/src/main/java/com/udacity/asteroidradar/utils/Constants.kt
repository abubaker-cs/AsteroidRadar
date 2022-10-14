package com.udacity.asteroidradar.utils

import com.udacity.asteroidradar.BuildConfig

object Constants {

    //
    const val BASE_URL = "https://api.nasa.gov/"

    //
    const val API_KEY = BuildConfig.nasaApiKey

    // Issue:
    // YYYY-MM-dd causes @SuppressLint("WeekBasedYear") ERROR, so I am replacing it with MM/dd/yyyy
    //
    // Week Based Year:
    // https://googlesamples.github.io/android-custom-lint-rules/checks/WeekBasedYear.md.html
    const val API_QUERY_DATE_FORMAT = "MM/dd/yyyy"

    //
    const val DEFAULT_END_DATE_DAYS = 7

}

