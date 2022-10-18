package com.udacity.asteroidradar.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

/**
 * 01 - Table's Structure: table_image
 *
 * ImageOfDay entity to be stored in the table_image.
 */
@Parcelize
@Entity(tableName = "table_image")
@JsonClass(generateAdapter = true)
data class ImageOfDay(

    /**
     * 1. Media Type:
     * The image of the day could be an image or a video, we are using only the image, to know what
     * media type is you have to check the media_type field, if this value is “image” you are going
     * to download and use the image, if it’s video you are going to ignore it.
     */
    @Json(name = "media_type")
    @ColumnInfo(name = "media_type")
    val mediaType: String? = "",

    /**
     * 2. Title:
     * The title of the picture, this is going to be used as content description of the image for Talk back.
     */
    @Json(name = "title")
    @ColumnInfo(name = "title")
    val title: String? = "",

    /**
     * 1. URL
     * Path of the image
     */
    @PrimaryKey
    @Json(name = "url")
    @ColumnInfo(name = "url")
    val url: String

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