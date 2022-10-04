package com.udacity.asteroidradar.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.data.model.Asteroid

/**
 * 02
 *
 * Data Access Object for database interaction.
 */
@Dao
interface AsteroidDao {

    // Insert an asteroid's record into the database by using the OnConflictStrategy.REPLACE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asteroid: List<Asteroid>)

    // Asteroids: Week
    @Query("SELECT * FROM table_asteroid WHERE close_approach_date >= :startDay AND close_approach_date <= :endDay ORDER BY close_approach_date")
    suspend fun getAsteroidsFromThisWeek(startDay: String, endDay: String): List<Asteroid>

    // Asteroids: Today
    @Query("SELECT * FROM table_asteroid WHERE close_approach_date = :today ")
    suspend fun getAsteroidToday(today: String): List<Asteroid>

    // Asteroids: All saved records
    @Query("SELECT * from table_asteroid")
    suspend fun getAsteroids(): List<Asteroid>

}