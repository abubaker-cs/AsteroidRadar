package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.data.dailyRecords
import com.udacity.asteroidradar.data.database.AsteroidDatabase
import com.udacity.asteroidradar.data.database.dao.AsteroidDao
import com.udacity.asteroidradar.data.database.dao.ImageOfDayDao
import com.udacity.asteroidradar.data.weeklyRecords
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import com.udacity.asteroidradar.repositories.AsteroidsRepository
import org.json.JSONObject
import retrofit2.HttpException

class BackgroundWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

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
     *
     */
    override suspend fun doWork(): Result {

        return try {

            val response = repository.asteroidAPI.getAsteroids(
                dailyRecords(),
                weeklyRecords()
            )

            // The reflection adapter uses Kotlin’s reflection library to convert your Kotlin classes
            // to and from JSON. Enable it by adding the KotlinJsonAdapterFactory to your Moshi.Builder:
            // Ref: https://github.com/square/moshi#reflection
            val moshi = Moshi.Builder().add(response).add(KotlinJsonAdapterFactory()).build()

            val jo2 = JSONObject(moshi.toString())
            val asteroids = parseAsteroidsJsonResult(jo2)

            asteroidDao.insert(asteroids)

            // Update daily image
            val picture = repository.asteroidAPI.getPicture()
            imageDao.insert(picture)

            //
            Result.success()

        } catch (e: HttpException) {

            //
            Result.retry()

        }

    }

    //
    companion object {
        const val WORK_NAME = "BackgroundWorker"
    }

}