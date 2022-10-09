package com.udacity.asteroidradar

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.udacity.asteroidradar.data.model.Asteroid
import com.udacity.asteroidradar.main.data.PictureState

// This file will be used to bind functions() through the XML file. e.g:
// app:imageUrl="@{viewModel.property.imgSrcUrl}"

/**
 * For Main Screen
 */

// 01 Status Image: Main Screen
@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {

    // Is this Asteroid harmful for earth?
    if (isHazardous) {

        // ICON: Potentially Hazardous
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)

    } else {

        // ICON: Normal
        imageView.setImageResource(R.drawable.ic_status_normal)

    }

}

// 02 Image of the Day: Main Screen
@BindingAdapter("imageOfTheDay")
fun bindImageOfDay(imageView: ImageView, imgUrl: LiveData<PictureState>) {

    imgUrl.let {

        // Converting the imgReference to a URI with the Https scheme
        val imgUri =
            imgUrl.value?.picture?.url?.toUri()?.buildUpon()?.scheme("https")?.build()

        GlideApp.with(imageView.context)

            // Attempts to always load the resource as a Bitmap, even if it could actually be animated.s
            .asBitmap()

            // Returns a request builder to load the given Uri.
            .load(imgUri)

            // Loading and Fallback images
            .apply(

                // Fallback images for Placeholder and Error states
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                // .error(R.drawable.ic_image)

            )
            .into(imageView)

    }

}

// 03 Asteroid Code/Name: Main Screen
@BindingAdapter("codeName")
fun bindTextViewToCodeName(textView: TextView, asteroid: Asteroid?) {

    // Assign the Asteroid Code/Name to the TextView
    textView.text = asteroid?.codeName

}

/**
 * For Details Screen
 */

// 04 Status Image: Details Screen
@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {

    // Is this Asteroid harmful for earth?
    if (isHazardous) {

        // IMAGE: Potentially Hazardous
        imageView.setImageResource(R.drawable.asteroid_hazardous)

    } else {

        // IMAGE: Safe
        imageView.setImageResource(R.drawable.asteroid_safe)

    }
}

// 05 Astronomical Unit: Absolute Magnitude + Distance from Earth
@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

// 06 Estimated Diameter
@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

// 07 Relative Velocity
@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}