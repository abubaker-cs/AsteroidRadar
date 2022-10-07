package com.udacity.asteroidradar.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

/**
 * 01
 *
 * ImageOfDay entity to be stored in the table_image.
 */
@Parcelize
@Entity(tableName = "table_image")
data class ImageOfDay(

    // Media Type
    @ColumnInfo(name = "media_type")
    val mediaType: String? = "",

    // Title
    @ColumnInfo(name = "title")
    val title: String? = "",

    // used to map img_src from the JSON to imgSrcUrl in our class
    @Json(name = "img_src")
    @PrimaryKey
    val imgSrcUrl: String

) : Parcelable