package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.PictureOfDay

/**
 * FILE 04
 *
 * Room database to persist data for the Asteroid Table.
 * This database stores a [Asteroid] entity
 */

@Database(entities = [Asteroid::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {

    // Reference to the @database/AsteroidDao.kt file
    abstract fun asteroidDao(): AsteroidDao
    abstract fun pictureOfDay(): PictureOfDayDao

    //
    companion object {

        // This will help in maintaining a single instance of the database opened at a given time.
        @Volatile
        private var INSTANCE: AsteroidDatabase? = null

        // Action: get the reference to the database
        fun getDatabase(context: Context): AsteroidDatabase {

            /**
             * Multiple threads can potentially run into a race condition and ask for a database
             * instance at the same time, resulting in two databases instead of one. Wrapping the
             * code to get the database inside a synchronized block means that only one thread of
             * execution at a time can enter this block of code, which makes sure the database only
             * gets initialized once.
             */
            return INSTANCE ?: synchronized(this) {

                // Inside the synchronized block, we will initialize the instance variable, and
                // Use Room's Room.databaseBuilder to create asteroid_database only if it doesn't exist.
                // Otherwise, return the reference of existing database.

                // Pass in:
                // 1. application context
                // 2. database class
                // 3. name for the database
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AsteroidDatabase::class.java,
                    "asteroid_database"
                )
                    // Migration Strategy
                    .fallbackToDestructiveMigration()
                    .build()

                // Initialize the instance based on the:
                // 1. Application context,
                // 2. Database class and
                // 3. Database name
                INSTANCE = instance

                return instance
            }

        }

    }

}