package com.udacity.asteroidradar.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

/**
 * 01
 *
 * Asteroid entity to be stored in the table_asteroid.
 */
@Parcelize
@Entity(tableName = "table_asteroid")
@JsonClass(generateAdapter = true)
data class Asteroid(

    // 1. ID
    @PrimaryKey
    @Json(name = "asteroid_id")
    @ColumnInfo(name = "asteroid_id")
    val id: Long,

    // 2. Code Name
    @Json(name = "code_name")
    @ColumnInfo(name = "code_name")
    val codeName: String,

    // 3. Close Approach Date
    @Json(name = "close_approach_date")
    @ColumnInfo(name = "close_approach_date")
    val closeApproachDate: String,

    // 4. Absolute Magnitude
    @Json(name = "absolute_magnitude")
    @ColumnInfo(name = "absolute_magnitude")
    val absoluteMagnitude: Double,

    // 5. Estimated Diameter
    @Json(name = "estimate_diameter")
    @ColumnInfo(name = "estimate_diameter")
    val estimatedDiameter: Double,

    // 6. Relative Velocity
    @Json(name = "relative_velocity")
    @ColumnInfo(name = "relative_velocity")
    val relativeVelocity: Double,

    // 7. Distance from Earth
    @Json(name = "distance_from_earth")
    @ColumnInfo(name = "distance_from_earth")
    val distanceFromEarth: Double,

    // 8. Hazardous?
    @Json(name = "danger_status")
    @ColumnInfo(name = "danger_status")
    val isPotentiallyHazardous: Boolean

) : Parcelable


/**
 * Notes:
 *
 * Kotlin "data class" with properties that match the JSON response fields
 *
 * Android Extensions - Dynamically updating Particles
 * When you annotate a class with @Parcelize, a Parcelable implementation is automatically generated
 * Ref: https://developer.android.com/kotlin/parcelize
 *
 * @JsonClass(generateAdapter = true)
 * It will generate a JsonAdapter to handle serializing/deserializing to and from JSON of the specified type
 *
 * The@Json(name = “***”) annotation defines the JSON key name for serialisation and the property
 * to set the value on with deserialization.
 *
 */