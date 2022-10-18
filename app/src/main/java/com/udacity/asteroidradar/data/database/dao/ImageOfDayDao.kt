package com.udacity.asteroidradar.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.data.model.ImageOfDay

/**
 * 02 ImageOfDayDao (SQL Queries)
 *
 * Data Access Object for database interaction.
 */
@Dao
interface ImageOfDayDao {

    // Get today's picture of the Asteroid
    @Query("SELECT * from table_image WHERE url=:url")
    suspend fun getImage(url: String): ImageOfDay

    // I am inserting the today's picture into the database using the OnConflictStrategy.REPLACE strategy
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(imageOfDay: ImageOfDay)

}