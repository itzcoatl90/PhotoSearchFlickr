package com.itzcoatl.me.photosearchapp.network

import com.itzcoatl.me.photosearchapp.model.JsonFlickrApi
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoProviderService {

    @GET("rest/?format=json&method=flickr.photos.search&nojsoncallback=1")
    fun providePhotoSearch(
            @Query("api_key") apiKey: String,
            @Query("text") query: String,
            @Query("page") page: Int?
    ): Call<JsonFlickrApi>

    companion object {
        const val BASE_URL = "https://www.flickr.com/services/"

        fun create(): PhotoProviderService {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
            return retrofit.create(PhotoProviderService::class.java)
        }

    }

}
