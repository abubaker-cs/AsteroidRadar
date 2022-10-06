package com.udacity.asteroidradar.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * 01
 *
 * Asteroid entity to be stored in the table_asteroid.
 */

// Kotlin "data class" with properties that match the JSON response fields

// Android Extensions - Dynamically updating Particles
// When you annotate a class with @Parcelize, a Parcelable implementation is automatically generated
// Ref: https://developer.android.com/kotlin/parcelize

@Parcelize
@Entity(tableName = "table_asteroid")
data class Asteroid(

    // ID
    @PrimaryKey
    @ColumnInfo(name = "asteroid_id")
    val id: Long,

    // Code Name
    @ColumnInfo(name = "code_name")
    val codeName: String,

    // Close Approach Date
    @ColumnInfo(name = "close_approach_date")
    val closeApproachDate: String,

    // Absolute Magnitude
    @ColumnInfo(name = "absolute_magnitude")
    val absoluteMagnitude: Double,

    // Estimated Diameter
    @ColumnInfo(name = "estimate_diameter")
    val estimatedDiameter: Double,

    // Relative Velocity
    @ColumnInfo(name = "relative_velocity")
    val relativeVelocity: Double,

    // Distance from Earth
    @ColumnInfo(name = "distance_from_earth")
    val distanceFromEarth: Double,

    // Hazardous?
    @ColumnInfo(name = "danger_status")
    val isPotentiallyHazardous: Boolean

) : Parcelable