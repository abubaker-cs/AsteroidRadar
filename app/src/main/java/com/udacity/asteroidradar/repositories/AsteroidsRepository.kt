package com.udacity.asteroidradar.repositories

import com.google.gson.JsonObject
import com.udacity.asteroidradar.data.model.ImageOfDay
import com.udacity.asteroidradar.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class AsteroidsRepository {

    val asteroidAPI: AsteroidAPI

    init {

        /**
         * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
         * object.
         */
        val retrofit: Retrofit = Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        asteroidAPI = retrofit.create(AsteroidAPI::class.java)

    }

    /**
     * Returns a Coroutine [List] of [AsteroidAPI] which can be fetched with await() if in a Coroutine scope.
     * The @GET annotation indicates that the "Asteroid" endpoint will be requested with the GET
     * HTTP method
     */
    interface AsteroidAPI {

        // Get the updated record of Asteroids
        // Example: https://api.nasa.gov/neo/rest/v1/feed?start_date=2015-09-07&end_date=2015-09-08&api_key=Constants.API_KEY
        @GET("neo/rest/v1/feed/")
        suspend fun getAsteroids(
            @Query("start_date") startDate: String,
            @Query("end_date") endDate: String,
            @Query("api_key") apiKey: String = Constants.API_KEY
        ): JsonObject


        // Get the Daily Image
        // Example: https://api.nasa.gov/planetary/apod/?api_key=Constants.API_KEY
        /**
         * Received JSON Format:
         * ====================
         * 1. date
         * 2. explanation
         * 3. hdurl
         * 4. media_type
         * 5. service_version
         * 6. title
         * 7. url
         */
        @GET("planetary/apod/")
        suspend fun getImage(
            @Query("api_key") apiKey: String = Constants.API_KEY
        ): ImageOfDay

        // TODO Since more than one network API has api_key as a query parameter, you can create an API interceptor and chain it with your network requests to avoid repetitively passing the API key as a parameter.

    }

}

