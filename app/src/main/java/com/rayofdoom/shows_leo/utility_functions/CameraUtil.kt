package com.rayofdoom.shows_leo.utility_functions

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.rayofdoom.shows_leo.R


/**
 * Function that encapsulates displaying user avatar that is cropped to fit circular imageview to any ImageView,
 * if context is provided returns true is avatar image exists, false otherwise
 */
fun ImageView.displayAvatar(context: Context, url: String?): Boolean {
    if (url == null || url == "null") {
        this.setImageResource(R.drawable.ic_profile_placeholder)
        return false
    } else {
        Glide.with(context).load(url).circleCrop().into(this)
        return true
    }
}

/**
 * Function that encapsulates displaying show image to any ImageView, if context is provided
 * if image does not exist displays placeholder
 */
fun ImageView.displayShowImage(context: Context, url: String?) {
    if (url == null) {
        this.setImageResource(R.drawable.ic_profile_placeholder)
    } else {
        Glide.with(context).load(url).centerCrop().into(this)
    }
}
