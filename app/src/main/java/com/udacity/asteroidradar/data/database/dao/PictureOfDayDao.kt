package com.udacity.asteroidradar.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.data.model.PictureOfDay

/**
 * 02
 *
 * Data Access Object for database interaction.
 */
@Dao
interface PictureOfDayDao {

    // Insert a picture into the database by using the OnConflictStrategy.REPLACE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pictureOfDay: PictureOfDay)

    // Get today's picture
    @Query("SELECT * from table_picture WHERE url=:url")
    suspend fun getPicture(url: String): PictureOfDay

}