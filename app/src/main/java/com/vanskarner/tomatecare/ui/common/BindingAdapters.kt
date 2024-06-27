package com.vanskarner.tomatecare.ui.common

import android.graphics.Bitmap
import android.util.Base64
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.vanskarner.tomatecare.R

@BindingAdapter("android:imageBase64")
fun loadImageBase64(imageView: ImageView, imageBase64: String) {
    Glide.with(imageView.context)
        .asBitmap()
        .load(Base64.decode(imageBase64, Base64.DEFAULT))
        .placeholder(R.drawable.baseline_image_24)
        .error(R.drawable.baseline_image_24)
        .into(imageView)
}

@BindingAdapter("android:imageBitmap")
fun loadBitmap(imageView: ImageView, imgBitmap: Bitmap?) {
    Glide.with(imageView.context)
        .load(imgBitmap)
        .error(R.drawable.plant_96)
        .into(imageView)
}

@BindingAdapter("android:imagePath")
fun loadImagePath(imageView: ImageView, imgPath:String) {
    Glide.with(imageView.context)
        .load(imgPath)
        .error(R.drawable.baseline_image_24)
        .into(imageView)
}