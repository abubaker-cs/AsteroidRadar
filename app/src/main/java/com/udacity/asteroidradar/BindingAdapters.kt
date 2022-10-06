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

// 01
@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

// 02
@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

// 03
@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

// 04
@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

// 05
@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

// --------------------- Custom Binding functions

// 06 nameCode
@BindingAdapter("codeName")
fun bindTextViewToCodeName(textView: TextView, asteroid: Asteroid?) {
    textView.text = asteroid?.codeName
}

// 07 date
@BindingAdapter("date")
fun bindTextViewToDate(textView: TextView, asteroid: Asteroid) {
    textView.text = asteroid.closeApproachDate
}

// 08 pictureOfDayImage
@BindingAdapter("imageOfTheDay")
fun bindImageOfDay(imageView: ImageView, imgReference: LiveData<PictureState>) {

    val thumbnail = imgReference.value?.picture?.url?.toUri()?.buildUpon()?.scheme("https")?.build()

    Glide.with(imageView.context)
        .asBitmap()
        .load(thumbnail)

        // Loading and Fallback images
        .apply(
            RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_image)
        )
        .into(imageView)

}