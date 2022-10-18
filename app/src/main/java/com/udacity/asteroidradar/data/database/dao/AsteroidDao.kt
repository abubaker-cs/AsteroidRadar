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

    // I am inserting an asteroid's record into the database using the OnConflictStrategy.REPLACE strategy
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asteroid: List<Asteroid>)

    // List of Weekly Asteroids
    @Query("SELECT * FROM table_asteroid WHERE close_approach_date >= :startDay AND close_approach_date <= :endDay ORDER BY close_approach_date")
    suspend fun getAsteroidsFromThisWeek(startDay: String, endDay: String): List<Asteroid>

    // List of Today's Asteroids
    @Query("SELECT * FROM table_asteroid WHERE close_approach_date = :today ")
    suspend fun getAsteroidToday(today: String): List<Asteroid>

    // List of all saved Asteroids
    @Query("SELECT * from table_asteroid")
    suspend fun getAsteroids(): List<Asteroid>

}