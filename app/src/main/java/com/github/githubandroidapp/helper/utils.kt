package com.github.githubandroidapp.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.github.githubandroidapp.R

/**
 * Loads the image to the imageview using Glide.
 */
fun ImageView.loadImage(uri: String?) {

    val options = RequestOptions()
        .placeholder(R.drawable.baseline_person_black_24dp)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .error(R.drawable.baseline_person_black_24dp)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .asBitmap()
        .load(uri)
        .listener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap>?,
                isFirstResource: Boolean
            ): Boolean {
                Log.d("glideError", "${e!!.logRootCauses("GLIDE")}")
                return false
            }

            override fun onResourceReady(
                resource: Bitmap?,
                model: Any?,
                target: Target<Bitmap>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

        })
        .into(this)

    this.setBackgroundColor(Color.TRANSPARENT)

}

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}
