package com.itzcoatl.me.photosearchapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.itzcoatl.me.photosearchapp.model.Photo
import com.itzcoatl.me.photosearchapp.R
import com.itzcoatl.me.photosearchapp.network.ImageProvider
import kotlinx.android.synthetic.main.photo_list_item.view.*

class PhotoAdapter(val context: Context, val onImageClickListener: ImageClickListener)
    : RecyclerView.Adapter<PhotoViewHolder>() {

    var photos : List<Photo>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
                LayoutInflater
                        .from(context)
                        .inflate(
                                R.layout.photo_list_item,
                                parent,
                                false
                        )
        )
    }

    override fun getItemCount(): Int {
        return photos?.size ?: 0
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        photos?.let {
            holder.setData(it[position], onImageClickListener)
        }
    }

    interface ImageClickListener {
        fun onImageClicked(photo: Photo)
    }

}

class PhotoViewHolder (var view: View) : RecyclerView.ViewHolder(view) {

    fun setData(photo: Photo, imageClickListener: PhotoAdapter.ImageClickListener) {
        ImageProvider.setImage(view.photo_thumbnail, photo.getThumbnailUrl(view.context))
        view.photo_thumbnail.setOnClickListener {
            imageClickListener.onImageClicked(photo)
        }
    }
}
