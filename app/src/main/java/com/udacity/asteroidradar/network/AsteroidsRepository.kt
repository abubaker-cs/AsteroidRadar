package com.udacity.asteroidradar.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.data.model.ImageOfDay
import com.udacity.asteroidradar.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class AsteroidsRepository {

    val asteroidAPI: AsteroidAPI

    init {

        /**
         * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
         * full Kotlin compatibility.
         */
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        /**
         * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
         * object.
         */
        val retrofit: Retrofit = Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
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

