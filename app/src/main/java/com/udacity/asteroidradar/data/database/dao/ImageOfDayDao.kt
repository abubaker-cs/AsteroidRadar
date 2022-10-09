package com.udacity.asteroidradar.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.data.model.ImageOfDay

/**
 * 02
 *
 * Data Access Object for database interaction.
 */
@Dao
interface ImageOfDayDao {

    // Insert a picture into the database by using the OnConflictStrategy.REPLACE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(imageOfDay: ImageOfDay)

    // Get today's picture
    @Query("SELECT * from table_image WHERE url=:url")
    suspend fun getImage(url: String): ImageOfDay

}