package it.marvel.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

fun View.isVisible(value: Boolean) =
    if (value) {
        visibility = View.VISIBLE
    } else
        visibility = View.GONE


fun ImageView.loadImage(context: Context, pathUrl: String, @DrawableRes placeHolder: Int) {
    Glide.with(context)
        .load(pathUrl)
        .centerCrop()
        .placeholder(placeHolder)
        .override(200, 200)
        .into(this)
}