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
 * ImageOfDay entity to be stored in the table_image.
 */
@Parcelize
@Entity(tableName = "table_image")
@JsonClass(generateAdapter = true)
data class ImageOfDay(

    // Media Type
    @Json(name = "media_type")
    @ColumnInfo(name = "media_type")
    val mediaType: String? = "",

    // Title
    @Json(name = "title")
    @ColumnInfo(name = "title")
    val title: String? = "",

    // used to map img_src from the JSON to imgSrcUrl in our class
    @PrimaryKey
    @Json(name = "url")
    @ColumnInfo(name = "url")
    val url: String

) : Parcelable