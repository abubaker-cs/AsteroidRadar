package com.udacity.asteroidradar.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.model.PictureOfDay

/**
 * 02
 *
 * Data Access Object for database interaction.
 */
@Dao
interface PictureOfDayDao {

    //
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pictureOfDay: PictureOfDay)

    //
    @Query("SELECT * from table_picture WHERE url=:url")
    suspend fun getPicture(url: String): PictureOfDay

}