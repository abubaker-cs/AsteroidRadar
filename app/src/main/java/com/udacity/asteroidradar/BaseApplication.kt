package com.udacity.asteroidradar

import android.app.Application

/**
 * File 05
 *
 * An application class that inherits from [Application], allows for the creation of a singleton
 * instance of the [Databases]
 *
 */

// We will instantiate the database instance in the Application class
class BaseApplication : Application()