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
    @Query("SELECT * FROM table_asteroid WHERE close_approach_date >= :startDay AND close_approach_date <= :endDay ORDER BY close_approach_date")
    suspend fun getAsteroidsFromThisWeek(startDay: String, endDay: String): List<Asteroid>

    //
    @Query("SELECT * FROM table_asteroid WHERE close_approach_date = :today ")
    suspend fun getAsteroidToday(today: String): List<Asteroid>

}