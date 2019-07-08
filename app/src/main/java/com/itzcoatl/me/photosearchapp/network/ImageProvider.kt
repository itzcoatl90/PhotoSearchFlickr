package com.itzcoatl.me.photosearchapp.network

import android.widget.ImageView
import com.itzcoatl.me.photosearchapp.R
import com.squareup.picasso.Picasso

class ImageProvider {
    companion object {

        fun setImage(container: ImageView, url: String) {
            Picasso
                    .get()
                    .load(url)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.imagenotfound)
                    .into(container)
        }
    }

}
