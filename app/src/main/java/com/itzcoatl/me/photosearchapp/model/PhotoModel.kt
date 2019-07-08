package com.itzcoatl.me.photosearchapp.model

import android.content.Context
import android.os.Parcelable
import com.itzcoatl.me.photosearchapp.R
import kotlinx.android.parcel.Parcelize

data class JsonFlickrApi(
        val photos: Photos,
        val stat: String?,
        val message: String?
)

data class Photos(
        val page: Int?,
        val pages: Int?,
        val photo: List<Photo>?,
        var search: String = ""
        )

@Parcelize
data class Photo(
        val id: String,
        val secret: String,
        val server: String,
        val farm: Int,
        val title: String
): Parcelable {
    fun getThumbnailUrl(context: Context): String {
        return context.getString(R.string.photo_base_url, farm, server, id, secret + "_t")
    }

    fun getPhotoUrl(context: Context): String {
        return context.getString(R.string.photo_base_url, farm, server, id, secret)
    }
}


