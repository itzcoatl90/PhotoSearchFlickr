package com.itzcoatl.me.photosearchapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.itzcoatl.me.photosearchapp.adapter.PaginatorView
import com.itzcoatl.me.photosearchapp.adapter.PhotoAdapter
import com.itzcoatl.me.photosearchapp.model.Photos
import kotlinx.android.synthetic.main.activity_photo_search.*
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.itzcoatl.me.photosearchapp.viewmodel.PhotosViewModel
import android.content.Intent
import com.itzcoatl.me.photosearchapp.model.Photo

class PhotoSearchActivity : AppCompatActivity() {

    companion object {
        val TAG = PhotoSearchActivity::class.java.canonicalName
    }

    lateinit var photosViewModel: PhotosViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_search)

        // Set up UI Elements
        photo_search_recycler.layoutManager = GridLayoutManager(this, 3)
        photo_search_recycler.adapter = PhotoAdapter(this, onImageClickListener)
        search_pagination.customListener = paginatorCaller

        // From the search float action button cannot specify page
        search_fab.setOnClickListener { search(search_edit_text.text.toString(), null) }

        // Set up the ViewModel
        photosViewModel = ViewModelProviders.of(this).get(PhotosViewModel::class.java)
        photosViewModel.photos.observe(this, object : Observer<Photos> {
            override fun onChanged(@Nullable photos: Photos) {
                showResult(photos)
            }
        })
        photosViewModel.error.observe(this, object : Observer<String> {
            override fun onChanged(@Nullable error: String) {
                showError(error)
            }
        })

    }

    private fun search(query: String, page: Int?) {
        // Validate it is a valid search
        if (query == "" && page == null) {
            Toast.makeText(this, R.string.search_error, Toast.LENGTH_LONG).show()
            return
        }

        photosViewModel.photos.value?.let {
            // Validate the search is different
            if (it.search == query && (page == null || it.page == page)) {
                Toast.makeText(this, R.string.search_again_error, Toast.LENGTH_LONG).show()
            } else {
                photosViewModel.search(getString(R.string.api_key), query, page)
            }
        } ?: run {
            // If there is no cached search, trigger one
            photosViewModel.search(getString(R.string.api_key), query, page)
        }
    }

    private fun showResult(photoList: Photos) {
        photoList.let {
            (photo_search_recycler?.adapter as PhotoAdapter).photos = it.photo
            photo_search_recycler?.adapter?.notifyDataSetChanged()
            if (it.page != null && it.pages != null && it.pages > 1) {
                search_pagination.visibility = View.VISIBLE
                search_pagination.setData(it.page, it.pages)
            } else {
                search_pagination.visibility = View.INVISIBLE
            }
        }
    }

    private fun showError(message: String) {
        Log.e(TAG, message)
        Toast.makeText(
                this,
                R.string.backend_error,
                Toast.LENGTH_LONG
        ).show()
    }

    // This should come from the paginator, meaning there is already a cached search
    val paginatorCaller = object : PaginatorView.CustomListener {
        override fun switchToPage(page: Int) {
            photosViewModel.photos.value?.let {
                search(it.search, page)
            }
        }
    }

    val onImageClickListener = object : PhotoAdapter.ImageClickListener {
        override fun onImageClicked(photo: Photo) {
            val photoIntent = Intent(
                    this@PhotoSearchActivity,
                    PhotoActivity::class.java
            )
            photoIntent.putExtra(PhotoActivity.PHOTO_PARCELABLE_KEY, photo)
            startActivity(photoIntent)
        }
    }

}
