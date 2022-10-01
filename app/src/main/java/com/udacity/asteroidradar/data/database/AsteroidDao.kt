package com.udacity.asteroidradar.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.model.Asteroid

/**
 * 02
 *
 * Data Access Object for database interaction.
 */
@Dao
interface AsteroidDao {

    //
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asteroid: List<Asteroid>)

    //
    @Query("SELECT * from table_asteroid")
    suspend fun getAsteroids(): List<Asteroid>

    //
    @Query("SELECT * FROM table_asteroid WHERE closeApproachDate >= :startDay AND closeApproachDate <= :endDay ORDER BY closeApproachDate")
    suspend fun getAsteroidsFromThisWeek(startDay: String, endDay: String): List<Asteroid>

    //
    @Query("SELECT * FROM table_asteroid WHERE closeApproachDate = :today ")
    suspend fun getAsteroidToday(today: String): List<Asteroid>

}