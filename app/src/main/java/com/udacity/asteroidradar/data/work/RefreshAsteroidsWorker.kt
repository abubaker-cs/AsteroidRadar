package com.udacity.asteroidradar.data.work

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.JsonParser
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.database.AsteroidDatabase
import com.udacity.asteroidradar.data.database.dao.AsteroidDao
import com.udacity.asteroidradar.data.database.dao.ImageOfDayDao
import com.udacity.asteroidradar.data.repositories.AsteroidRepository
import com.udacity.asteroidradar.utils.Constants
import org.json.JSONObject
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.*

class RefreshAsteroidsWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    //
    private val asteroidDao: AsteroidDao by lazy {
        AsteroidDatabase.getDatabase(applicationContext).asteroidDaoReference()
    }

    //
    private val imageDao: ImageOfDayDao by lazy {
        AsteroidDatabase.getDatabase(applicationContext).imageOfDayReference()
    }

    //
    private val repository: AsteroidRepository by lazy { AsteroidRepository() }

    //
    companion object {
        const val WORK_NAME = "RefreshAsteroidsWorker"
    }

    /**
     *
     */
    override suspend fun doWork(): Result {

        return try {

            //
            val response = repository.service.getAsteroids(
                getToday(),
                getSevenDaysLater()
            )

            //
            val gson = JsonParser().parse(response.toString()).asJsonObject

            //
            val jo2 = JSONObject(gson.toString())

            //
            val asteroids = parseAsteroidsJsonResult(jo2)

            //
            asteroidDao.insert(asteroids)

            //
            val picture = repository.service.getPicture()

            //
            imageDao.insert(picture)

            //
            Result.success()

        } catch (e: HttpException) {

            //
            Result.retry()

        }

    }
}

@SuppressLint("WeekBasedYear")
fun getToday(): String {
    val calendar = Calendar.getInstance()
    val currentTime = calendar.time
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    return dateFormat.format(currentTime)
}

@SuppressLint("WeekBasedYear")
fun getSevenDaysLater(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, 7)
    val currentTime = calendar.time
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    return dateFormat.format(currentTime)
}