package com.udacity.asteroidradar.network

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.JsonParser
import com.udacity.asteroidradar.data.database.AsteroidDatabase
import com.udacity.asteroidradar.data.database.dao.AsteroidDao
import com.udacity.asteroidradar.data.database.dao.ImageOfDayDao
import com.udacity.asteroidradar.utils.dailyRecords
import com.udacity.asteroidradar.utils.weeklyRecords
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

            //
            val response = repository.asteroidAPI.getAsteroids(
                dailyRecords(),
                weeklyRecords()
            )

            //
            val gson = JsonParser().parse(response.toString()).asJsonObject
            val jo2 = JSONObject(gson.toString())

            // Update Asteroids List
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