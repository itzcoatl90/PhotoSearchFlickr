package com.itzcoatl.me.photosearchapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.itzcoatl.me.photosearchapp.model.Photo
import com.itzcoatl.me.photosearchapp.network.ImageProvider
import kotlinx.android.synthetic.main.activity_photo.*

class PhotoActivity : AppCompatActivity() {

    companion object {
        const val PHOTO_PARCELABLE_KEY = "PHOTO_PARCELABLE_KEY"
    }

    var photo: Photo? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        photo_close.setOnClickListener {
            this@PhotoActivity.finish()
        }
        photo = intent.getParcelableExtra(PHOTO_PARCELABLE_KEY)
        photo?.let {
            ImageProvider.setImage(photo_image, it.getPhotoUrl(this))
        }
    }
}
