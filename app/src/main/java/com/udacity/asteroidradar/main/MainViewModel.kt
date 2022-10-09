package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParser
import com.udacity.asteroidradar.data.dailyRecords
import com.udacity.asteroidradar.data.database.AsteroidDatabase
import com.udacity.asteroidradar.data.database.dao.AsteroidDao
import com.udacity.asteroidradar.data.database.dao.ImageOfDayDao
import com.udacity.asteroidradar.data.model.Asteroid
import com.udacity.asteroidradar.data.model.ImageOfDay
import com.udacity.asteroidradar.data.weeklyRecords
import com.udacity.asteroidradar.main.data.AsteroidState
import com.udacity.asteroidradar.main.data.ImageState
import com.udacity.asteroidradar.main.enums.AsteroidApiFilter
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import com.udacity.asteroidradar.repositories.AsteroidsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MainViewModel(app: Application) : AndroidViewModel(app) {

    // Repository
    private val repository: AsteroidsRepository by lazy { AsteroidsRepository() }

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _status: MutableStateFlow<AsteroidState> =
        MutableStateFlow(AsteroidState(true, emptyList()))

    // The external immutable LiveData for the request status String
    val status = _status.asStateFlow()

    // LiveData Asteroid with an internal Mutable and external LiveData
    private val _picture: MutableLiveData<ImageState> =
        MutableLiveData(ImageState(null))

    val picture: LiveData<ImageState> = _picture

    //
    val downloadingState = status.map { value -> value.downloading }


    // LiveData Asteroid with an internal Mutable and external LiveData
    private val _asteroid = MutableLiveData<List<Asteroid>>()

    val asteroid: LiveData<List<Asteroid>>
        get() = _asteroid

    //
    private lateinit var cachedAsteroids: List<Asteroid>

    //
    private val asteroidDao: AsteroidDao by lazy {
        AsteroidDatabase.getDatabase(app).asteroidDaoReference()
    }

    //
    private val imageDao: ImageOfDayDao by lazy {
        AsteroidDatabase.getDatabase(app).imageOfDayReference()
    }


    /**
     * Call getAsteroid() on init so we can display status immediately.
     */
    init {

        viewModelScope.launch {
            val asteroids = getAsteroids()
            _status.value = AsteroidState(false, asteroids)
            cachedAsteroids = asteroids

            val pictureOfDay = getImage()
            _picture.value = ImageState(pictureOfDay)
        }

    }


    /**
     * To re-query the data by calling getMarsRealEstateProperties with the new filter
     */
    fun updateFilter(filter: AsteroidApiFilter) {
        viewModelScope.launch {

            //
            when (filter) {

                // Week: Display full list of all Asteroids for the week
                AsteroidApiFilter.SHOW_WEEK -> {
                    val asteroids =
                        asteroidDao.getAsteroidsFromThisWeek(
                            dailyRecords(),
                            weeklyRecords()
                        )
                    _status.value = AsteroidState(false, asteroids)
                }

                // Today: Display only the Asteroids related to today
                AsteroidApiFilter.SHOW_TODAY -> {
                    val asteroids = asteroidDao.getAsteroidToday(dailyRecords())
                    _status.value = AsteroidState(false, asteroids)
                }
                else -> {
                    val asteroids = asteroidDao.getAsteroids()
                    _status.value = AsteroidState(false, asteroids)
                }

            }
        }
    }


    /**
     * getAsteroids() - List of Asteroids
     *
     * Issue:
     * When the UI thread of an Android app is blocked for too long, an "Application Not Responding" (ANR) error is triggered.
     *
     * Solution:
     * Instead of running the block in the main-thread, I am using withContext(Dispatchers.IO)
     * to create a block that runs on the IO thread pool. Moreover since withContext() is itself
     * a suspend function, the function get will also a suspend function.
     *
     */
    private suspend fun getAsteroids(): List<Asteroid> = withContext(Dispatchers.IO) {

        // Download and store updated data through the Network, otherwise read information from Room Database
        try {

            val response = repository.asteroidAPI.getAsteroids(

                // Filtered List for only today's records
                dailyRecords(),

                // Detailed list of all Asteroids for the week
                weeklyRecords()

            )

            //
            val gson = JsonParser().parse(response.toString()).asJsonObject
            val responseInString = JSONObject(gson.toString())
            val asteroids = parseAsteroidsJsonResult(responseInString)

            asteroidDao.insert(asteroids)

            //
            asteroidDao.getAsteroids()

            // LOG


        } catch (e: Exception) {

            //
            e.printStackTrace()

            //
            asteroidDao.getAsteroids()

        }

    }

    /**
     * getImage() - Daily Picture
     *
     * Issue:
     * When the UI thread of an Android app is blocked for too long, an "Application Not Responding" (ANR) error is triggered.
     *
     * Solution:
     * Instead of running the block in the main-thread, I am using withContext(Dispatchers.IO)
     * to create a block that runs on the IO thread pool. Moreover since withContext() is itself
     * a suspend function, the function get will also a suspend function.
     *
     */
    private suspend fun getImage(): ImageOfDay? = withContext(Dispatchers.IO) {

        // Download and store updated data through the Network, otherwise read information from Room Database
        try {

            // Download latest today's picture through the Network
            val image = repository.asteroidAPI.getImage()

            // Store latest today's picture into the Database
            imageDao.insert(image)

            //
            imageDao.getImage(image.url)

        } catch (e: Exception) {

            //
            e.printStackTrace()

            //
            null

        }

    }

}