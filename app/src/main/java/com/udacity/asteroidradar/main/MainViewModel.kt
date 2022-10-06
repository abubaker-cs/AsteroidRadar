package com.udacity.asteroidradar.main

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import com.google.gson.JsonParser
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.database.dao.AsteroidDao
import com.udacity.asteroidradar.data.database.AsteroidDatabase
import com.udacity.asteroidradar.data.database.dao.PictureOfDayDao
import com.udacity.asteroidradar.data.model.Asteroid
import com.udacity.asteroidradar.data.model.PictureOfDay
import com.udacity.asteroidradar.data.repositories.AsteroidRepository
import com.udacity.asteroidradar.main.data.AsteroidState
import com.udacity.asteroidradar.main.data.PictureState
import com.udacity.asteroidradar.main.enums.AsteroidApiFilter
import com.udacity.asteroidradar.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(app: Application) : AndroidViewModel(app) {

    // Repository
    private val repository: AsteroidRepository by lazy { AsteroidRepository() }

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _state: MutableStateFlow<AsteroidState> =
        MutableStateFlow(AsteroidState(true, emptyList()))

    // The external immutable LiveData for the request status String
    val state = _state.asStateFlow()


    // LiveData Asteroid with an internal Mutable and external LiveData
    private val _picture: MutableLiveData<PictureState> =
        MutableLiveData(PictureState(null))

    val picture: LiveData<PictureState> = _picture

    //
    val loadingState = state.map { value -> value.loading }


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
    private val pictureDao: PictureOfDayDao by lazy {
        AsteroidDatabase.getDatabase(app).pictureOfDayReference()
    }


    /**
     * Call getAsteroid() on init so we can display status immediately.
     */
    init {
        viewModelScope.launch {
            val asteroids = getAsteroids()
            _state.value = AsteroidState(false, asteroids)
            cachedAsteroids = asteroids

            val pictureOfDay = getPicture()
            _picture.value = PictureState(pictureOfDay)
        }
    }

    /**
     * Sets the value of the response LiveData to the Mars API status or the successful number of
     * Mars properties retrieved.
     */
//    private fun getMarsRealEstateProperties(filter: AsteroidApiFilter) {
//
//        // Coroutine
//        viewModelScope.launch {
//            _status.value = AsteroidApiStatus.LOADING
//            try {
//                // _asteroid.value = AsteroidApi.retrofitService.getAsteroids(filter.value)
//                _status.value = AsteroidApiStatus.DONE
//            } catch (e: Exception) {
//                _status.value = AsteroidApiStatus.ERROR
//                _asteroid.value = ArrayList()
//            }
//        }
//
//    }

    /**
     * An encapsulated LiveData variable for navigating to the selectedProperty detail screen
     */
    // private val _navigateToSelectedProperty = MutableLiveData<Asteroid>()

//    val navigateToSelectedProperty: LiveData<Asteroid>
//        get() = _navigateToSelectedProperty
//
//    fun displayPropertyDetails(marsProperty: Asteroid) {
//        _navigateToSelectedProperty.value = marsProperty
//    }

    // Add displayPropertyDetailsComplete() to set _navigateToSelectedProperty to false once
    // navigation is completed to prevent unwanted extra navigations
//    fun displayPropertyDetailsComplete() {
//        _navigateToSelectedProperty.value = null
//    }

    /**
     * To re-query the data by calling getMarsRealEstateProperties with the new filter
     */
    fun updateFilter(filter: AsteroidApiFilter) {
        viewModelScope.launch {
            when (filter) {

                // Week
                AsteroidApiFilter.SHOW_WEEK -> {
                    val asteroids =
                        asteroidDao.getAsteroidsFromThisWeek(
                            getToday(),
                            getSevenDaysLater()
                        )
                    _state.value = AsteroidState(false, asteroids)
                }

                // Today
                AsteroidApiFilter.SHOW_TODAY -> {
                    val asteroids = asteroidDao.getAsteroidToday(getToday())
                    _state.value = AsteroidState(false, asteroids)
                }
                else -> {
                    val asteroids = asteroidDao.getAsteroids()
                    _state.value = AsteroidState(false, asteroids)
                }
            }
        }
    }

    // getAsteroids() - List of Asteroids
    private suspend fun getAsteroids(): List<Asteroid> = withContext(Dispatchers.IO) {
        try {
            val response = repository.service.getAsteroids(
                getToday(),
                getSevenDaysLater()
            )

            // TODO Replace GSON
            val gson = JsonParser().parse(response.toString()).asJsonObject

            val jo2 = JSONObject(gson.toString())
            val asteroids = parseAsteroidsJsonResult(jo2)

            asteroidDao.insert(asteroids)
            asteroidDao.getAsteroids()
        } catch (e: Exception) {
            e.printStackTrace()
            asteroidDao.getAsteroids()
        }
    }

    // getPicture() - Daily Picture
    private suspend fun getPicture(): PictureOfDay? = withContext(Dispatchers.IO) {
        try {
            val picture = repository.service.getPicture()
            pictureDao.insert(picture)
            pictureDao.getPicture(picture.url)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    private fun getToday(): String {
        val calendar = Calendar.getInstance()
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(currentTime)
    }

    @SuppressLint("WeekBasedYear")
    private fun getSevenDaysLater(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 7)
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(currentTime)
    }


}