package com.udacity.asteroidradar

import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coil.load
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.data.model.Asteroid
import com.udacity.asteroidradar.main.data.PictureState
import kotlinx.coroutines.flow.Flow

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

// --------------------- CUstom

// 06 namecode
@BindingAdapter("namecode")
fun bindTextViewToNamecode(textView: TextView, asteroid: Asteroid?) {
    textView.text = asteroid?.codename
}

// 07 date
@BindingAdapter("date")
fun bindTextViewToDate(textView: TextView, asteroid: Asteroid) {
    textView.text = asteroid.closeApproachDate
}

// 08 pictureOfDayImage
// LiveData<PictureState>
@BindingAdapter("pictureOfDayImage")
fun bindPictureOfDay(imageView: ImageView, pictureOfDay: LiveData<PictureState>) {


    // imgView.load(imgUrl.value?.picture?.url)
    imageView.load(pictureOfDay.value?.picture?.url)


    // val imgUri = imgUrl.value?.picture?.url?.toUri()?.buildUpon()?.scheme("https")?.build()
    // val imgUri = imgUrl.value?.picture?.url
    // val imageParse = Uri.parse(imgUrl.value?.picture?.url.toString())


//    imgUrl.let {
////        Picasso.get().load(imageParse).fit().centerCrop()
////            .placeholder(R.drawable.loading_animation)
////            .error(R.drawable.ic_broken_image).into(imgView)
//
//
//        // Source
//        // Converting the imgUrl to a URI with the Https scheme
//        // val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
//        // val imgUri = imgUrl.value?.picture?.url?.toUri()?.buildUpon()?.scheme("https")?.build()
//        // Log.v("PICTURE", "Path 1: $imgUri");
//        // Log.v("PICTURE", "Path 2: $imgUrl");
//        // val imgUri = "http://via.placeholder.com/300.png"
//
//        // Load
////        Glide.with(imgView.context)
////            .load(imgUri)
////
////            // Loading and Fallback images
////            .apply(
////                RequestOptions()
////                    .placeholder(R.drawable.loading_animation)
////                    .error(R.drawable.ic_broken_image)
////            )
////            .into(imgView)
//
//    }

}