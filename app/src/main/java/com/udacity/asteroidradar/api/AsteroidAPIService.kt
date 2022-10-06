package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.data.model.Asteroid

/**
 * Network Layer:
 * The API that the ViewModel uses to communicate with the web service.
 *
 * Retrofit Basics:
 * What retrofit needs to build our Network API
 * 1. Base URL of our web service
 * 2. Convertor factory that allows Retrofit to return the server response in a useful format
 */

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
//private val moshi = Moshi.Builder()
//    .add(KotlinJsonAdapterFactory())
//    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
//private val retrofit = Retrofit.Builder()
//    .addConverterFactory(MoshiConverterFactory.create(moshi))
//    .baseUrl(BASE_URL)
//    .build()

/**
 * Returns a Coroutine [List] of [Asteroid] which can be fetched with await() if in a Coroutine scope.
 * The @GET annotation indicates that the "Asteroid" endpoint will be requested with the GET
 * HTTP method
 */
//interface AsteroidAPIService {
//
//    @GET("neo/rest/v1/feed/")
//    suspend fun getAsteroids(
//        @Query("start_date") startDate: String,
//        @Query("end_date") endDate: String,
//        @Query("api_key") apiKey: String = Constants.API_KEY
//    ): JsonObject
//
//    @GET("planetary/apod/")
//    suspend fun getPicture(
//        @Query("api_key") apiKey: String = Constants.API_KEY
//    ): PictureOfDay
//
//}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
//object AsteroidApi {
//    val retrofitService: AsteroidAPIService by lazy {
//        retrofit.create(AsteroidAPIService::class.java)
//    }
//}