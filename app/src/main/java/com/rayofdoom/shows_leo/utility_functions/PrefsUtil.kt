package com.rayofdoom.shows_leo.utility_functions

import android.app.Activity
import android.content.Context
import com.rayofdoom.shows_leo.model.User


private const val ACCESS_TOKEN = "access-token"
private const val CLIENT = "client"
private const val UID = "uid"
private const val USER_EMAIL = "user_email"
private const val USER_ID = "user_id"
private const val USER_IMAGE = "user_image"

object PrefsUtil {

    /**
     *Returns header parameters ("access-token","client","uid") in form of
     *List in order described above
     */
    fun loadHeadersFromPrefs(activity: Activity): List<String?> {
        val sharedPrefs = activity.getPreferences(Context.MODE_PRIVATE)
        return listOf(
            sharedPrefs.getString(ACCESS_TOKEN, null),
            sharedPrefs.getString(CLIENT, null),
            sharedPrefs.getString(
                UID, null
            )
        )
    }

    /**
     *Puts header parameters ("access-token","client","uid") to
     *List in order described above
     */
    fun putHeadersToPrefs(headers: List<String>, activity: Activity) {
        val sharedPrefs =
            activity.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPrefs.edit()) {
            putString(ACCESS_TOKEN, headers[0])
            putString(CLIENT, headers[1])
            putString(UID, headers[2])
            apply()
        }
    }

    fun putUserToPrefs(user: User?, activity: Activity) {
        val sharedPrefs = activity.getPreferences(Context.MODE_PRIVATE)
        if (user != null) {
            with(sharedPrefs.edit()) {
                putString(USER_IMAGE, user.imageUrl)
                putString(USER_EMAIL, user.email)
                putString(USER_ID, user.id.toString())
                apply()
            }
        }
    }

    fun loadUserFromPrefs(activity: Activity): User {
        val sharedPrefs = activity.getPreferences(Context.MODE_PRIVATE)
        return User(
            sharedPrefs.getString(USER_ID, "-1")!!.toInt(),
            sharedPrefs.getString(USER_EMAIL, "null")!!,
            sharedPrefs.getString(USER_IMAGE, "null")

        )

    }

    fun updateUserImageUrl(activity: Activity, url: String?) {
        val sharedPrefs = activity.getPreferences(Context.MODE_PRIVATE)
        with(sharedPrefs.edit()) {
            putString(USER_IMAGE, url)
            apply()
        }
    }
}