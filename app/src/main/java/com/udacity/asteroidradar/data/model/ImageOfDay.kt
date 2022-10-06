package com.udacity.asteroidradar.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
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

    // URL
    @PrimaryKey
    val url: String

) : Parcelable