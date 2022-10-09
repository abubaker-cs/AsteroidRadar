package com.udacity.asteroidradar

import android.app.Application
import androidx.work.*
import com.udacity.asteroidradar.data.database.AsteroidDatabase
import com.udacity.asteroidradar.work.AsteroidWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

/**
 * File 05
 *
 * An application class that inherits from [Application], allows for the creation of a singleton
 * instance of the [AsteroidDatabase]
 *
 */

// We will instantiate the database instance in the Application class
class BaseApplication : Application() {

    /**
     * Define the CoroutineScope and initialize the background task
     */
    override fun onCreate() {

        super.onCreate()

        // Creates a CoroutineScope that wraps the given coroutine context.
        val applicationScope = CoroutineScope(Dispatchers.Default)

        // Initialize the WorkRequest using Coroutines
        applicationScope.launch {
            periodicWork()
        }

    }

    /**
     * Custom configurations for the Work
     */
    private fun periodicWork() {

        // Creating Constrains for the WorkManager so it downloads and saves today's asteroids
        // in background once a day when the device is charging and wifi is enabled.
        val constraints = Constraints.Builder()

            // Ensure tha the wifi is enabled
            .setRequiredNetworkType(NetworkType.UNMETERED)

            // Sets whether device battery should be at an acceptable level for the WorkRequest to run.
            .setRequiresBatteryNotLow(true)

            // Sets whether device should be charging for the WorkRequest to run.
            .setRequiresCharging(true)

            // Sets whether device should be idle for the WorkRequest to run.
            .apply {
                setRequiresDeviceIdle(true)
            }.build()

        // Add WorkRequest to save the image to the filesystem using the AsteroidWorker.kt file
        val save = PeriodicWorkRequestBuilder<AsteroidWorker>(

            // We are asking the device to perform the background operation, once a day
            1, TimeUnit.DAYS

        )

            // Add our custom constraints to the WorkRequest.
            .setConstraints(constraints)

            // Builds a WorkRequest based on this WorkRequest.Builder.
            .build()

        // This method allows you to enqueue a uniquely-named PeriodicWorkRequest,
        // where only one PeriodicWorkRequest of a particular name can be active at a time.
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(

            // Get the unique worker name from the companion object defined in the AsteroidsWorker.kt
            AsteroidWorker.WORK_NAME,

            //
            ExistingPeriodicWorkPolicy.KEEP,

            // Pass parameters for our WorkRequest
            save

        )

    }
}