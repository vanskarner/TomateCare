package com.vanskarner.tomatecare.ui.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.vanskarner.tomatecare.R
import java.io.File
import java.io.IOException

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
fun loadImagePath(imageView: ImageView, imgPath: String) {
    Glide.with(imageView.context)
        .load(imgPath)
        .error(R.drawable.baseline_image_24)
        .into(imageView)
}

@BindingAdapter("android:storedImage")
fun loadStoredImage(imageView: ImageView, imgPath: String?) {
    val imgBitmap = imgPath
        ?.takeIf { File(it).exists() }
        ?.let { BitmapFactory.decodeFile(it) }
    imgBitmap?.let {
        Glide.with(imageView.context)
            .load(imgBitmap)
            .error(R.drawable.baseline_image_24)
            .into(imageView)
    }
}

@BindingAdapter("android:boundingBox")
fun setBoundingBox(overlayView: OverlayView, boundingBoxes: List<BoundingBoxModel>?) {
    if (boundingBoxes.isNullOrEmpty()) overlayView.clear()
    else overlayView.setResults(boundingBoxes)
}

@BindingAdapter("android:assetImage")
fun loadAssetImage(imageView: ImageView, imageName: String?) {
    imageName?.let { name ->
        try {
            imageView.context.assets.open(name).use { inputStream ->
                val bitmap = BitmapFactory.decodeStream(inputStream)
                Glide.with(imageView.context)
                    .load(bitmap)
                    .error(R.drawable.baseline_image_24)
                    .into(imageView)
            }
        } catch (e: IOException) {
            imageView.setImageResource(R.drawable.baseline_image_24)
        }
    }
}
