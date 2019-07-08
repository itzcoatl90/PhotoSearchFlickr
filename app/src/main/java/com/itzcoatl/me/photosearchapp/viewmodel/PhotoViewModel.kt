package com.itzcoatl.me.photosearchapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itzcoatl.me.photosearchapp.model.JsonFlickrApi
import com.itzcoatl.me.photosearchapp.model.Photos
import com.itzcoatl.me.photosearchapp.network.PhotoProviderService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotosViewModel : ViewModel() {

    val photoProviderService by lazy {
        PhotoProviderService.create()
    }

    val photos: MutableLiveData<Photos> by lazy {
        MutableLiveData<Photos>()
    }

    val error: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun search(apiKey: String, query: String, page: Int?) {
        val call = photoProviderService.providePhotoSearch(apiKey, query, page)
        call.enqueue(
                object: Callback<JsonFlickrApi> {

                    override fun onResponse(call: Call<JsonFlickrApi>?,
                                            response: Response<JsonFlickrApi>?) {
                        val jsonFlickrApi = response?.body()
                        if (jsonFlickrApi?.stat == "ok") {
                            photos.value = jsonFlickrApi.photos
                            photos.value?.search = query
                        } else if (jsonFlickrApi?.stat == "fail") {
                            error.value = jsonFlickrApi.message
                        }
                    }

                    override fun onFailure(call: Call<JsonFlickrApi>?, t: Throwable?) {
                        error.value = t.toString()
                    }
                }
        )
    }

}
