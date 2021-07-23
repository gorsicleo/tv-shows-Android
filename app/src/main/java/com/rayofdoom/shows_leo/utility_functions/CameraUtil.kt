package com.rayofdoom.shows_leo.utility_functions

import android.content.Context
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.rayofdoom.shows_leo.R

/**
 * Function that encapsulates displaying user taken avatar to any ImageView, if context is provided
 * returns true is avatar file exists, false otherwise
 */
fun ImageView.displayAvatar(context: Context): Boolean {
    return if (FileUtil.getImageFile(context) != null) {
        Glide.with(context).load(FileUtil.getImageFile(context)).circleCrop().diskCacheStrategy(
            DiskCacheStrategy.NONE
        )
            .skipMemoryCache(true).placeholder(R.drawable.ic_profile_placeholder)
            .into(this)
        true

    } else {
        Log.e("CameraUtil", "cannot fetch avatar picture!")
        false
    }
}
