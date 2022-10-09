package com.udacity.asteroidradar.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.JsonParser
import com.udacity.asteroidradar.data.dailyRecords
import com.udacity.asteroidradar.data.database.AsteroidDatabase
import com.udacity.asteroidradar.data.database.dao.AsteroidDao
import com.udacity.asteroidradar.data.database.dao.ImageOfDayDao
import com.udacity.asteroidradar.data.weeklyRecords
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import com.udacity.asteroidradar.repositories.AsteroidsRepository
import org.json.JSONObject

class AsteroidWorker(ctx: Context, params: WorkerParameters) :
    CoroutineWorker(ctx, params) {

    // 01 Asteroid Repository
    private val repository: AsteroidsRepository by lazy { AsteroidsRepository() }

    // 02 List of Asteroids
    private val asteroidDao: AsteroidDao by lazy {
        AsteroidDatabase.getDatabase(applicationContext).asteroidDaoReference()
    }

    // 03 Image of the Day
    private val imageDao: ImageOfDayDao by lazy {
        AsteroidDatabase.getDatabase(applicationContext).imageOfDayReference()
    }

    /**
     * doWork()
     * This function initializes the suspending work.
     */
    override suspend fun doWork(): Result {

        val appContext = applicationContext

        return try {

            val response = repository.asteroidAPI.getAsteroids(
                dailyRecords(),
                weeklyRecords()
            )

            // The reflection adapter uses Kotlin’s reflection library to convert your Kotlin classes
            // to and from JSON. Enable it by adding the KotlinJsonAdapterFactory to your Moshi.Builder:
            // Ref: https://github.com/square/moshi#reflection
//            val moshi = Moshi.Builder()
//                .add(response)
//                .add(KotlinJsonAdapterFactory())
//                .build()
//
//            val sampleData = JSONObject(moshi.toString())
//            val asteroids = parseAsteroidsJsonResult(sampleData)
//            asteroidDao.insert(asteroids)

            val gson = JsonParser().parse(response.toString()).asJsonObject

            val responseInString = JSONObject(gson.toString())
            val asteroids = parseAsteroidsJsonResult(responseInString)

            asteroidDao.insert(asteroids)

//            val gson = JsonParser().parse(response.toString()).asJsonObject
//            val gsonData = JSONObject(gson.toString())
//
//            val asteroids = parseAsteroidsJsonResult(gsonData)


            // Update daily image
            val image = repository.asteroidAPI.getImage()
            imageDao.insert(image)

            //
            Result.success()

        } catch (throwable: Throwable) {
            Log.e("AsteroidsWorker", "Error fetching data...")
            Result.failure()
        }

    }

    // I will be using it to store the unique name of the Work
    companion object {
        const val WORK_NAME = "AsteroidWorker"
    }

}