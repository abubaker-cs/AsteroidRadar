package com.udacity.asteroidradar

import android.app.Application
import androidx.work.*
import com.udacity.asteroidradar.data.database.AsteroidDatabase
import com.udacity.asteroidradar.data.work.RefreshAsteroidsWorker
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

    // TODO Delete database: from BaseApplication
    // I am using lazy delegate so the instance database is lazily created whenever access is needed
    // to the database (rather than when the app starts) .
    // val database: AsteroidDatabase by lazy {

    // Calling the getDatabase() while passing in the context will instantiate the database.
    // AsteroidDatabase.getDatabase(this)

    // }

    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    /**
     *
     */
    private fun setupRecurringWork() {

        // Creating Constrains for the WorkManager
        val constraints = Constraints.Builder()

            //
            .setRequiredNetworkType(NetworkType.UNMETERED)

            //
            .setRequiresBatteryNotLow(true)

            // Charging constraint
            .setRequiresCharging(true)

            //
            .apply {
                setRequiresDeviceIdle(true)
            }.build()

        // Add WorkRequest to save the image to the filesystem
        val save = PeriodicWorkRequestBuilder<RefreshAsteroidsWorker>(
            1, TimeUnit.DAYS
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshAsteroidsWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            save
        )

    }
}