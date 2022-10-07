package com.udacity.asteroidradar.data.repositories

import com.google.gson.JsonObject
import com.udacity.asteroidradar.data.model.ImageOfDay
import com.udacity.asteroidradar.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class AsteroidRepository {

    val service: Service

    init {

        val retrofit: Retrofit = Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(Service::class.java)

    }

    /**
     * Returns a Coroutine [List] of [Service] which can be fetched with await() if in a Coroutine scope.
     * The @GET annotation indicates that the "Asteroid" endpoint will be requested with the GET
     * HTTP method
     */
    interface Service {

        // Get the updated record of Asteroids
        @GET("neo/rest/v1/feed/")
        suspend fun getAsteroids(
            @Query("start_date") startDate: String,
            @Query("end_date") endDate: String,
            @Query("api_key") apiKey: String = Constants.API_KEY
        )

        // : JsonObject


        // Get the Daily Image
        @GET("planetary/apod/")
        suspend fun getPicture(
            @Query("api_key") apiKey: String = Constants.API_KEY
        ): ImageOfDay
    }

}

