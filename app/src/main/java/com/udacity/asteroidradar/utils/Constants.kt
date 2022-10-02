package com.udacity.asteroidradar.utils

import com.udacity.asteroidradar.BuildConfig

object Constants {
    const val BASE_URL = "https://api.nasa.gov/"
    const val API_KEY = BuildConfig.nasaApiKey
    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
}
