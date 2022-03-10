package com.example.pinterestappclone.network.service

import com.example.pinterestappclone.model.PhotoItem
import com.example.pinterestappclone.model.PhotoList
import com.example.pinterestappclone.model.RelatedPhotos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotoService {

    companion object {
        private const val ACCESS_KEY = "your access key"
        const val client_id = "Client-ID"
    }

    @Headers("Authorization:$client_id $ACCESS_KEY")
    @GET("photos")
    fun getPhotos(@Query("page") page: Int, @Query("per_page") perPage: Int): Call<PhotoList>

    @Headers("Authorization:$client_id $ACCESS_KEY")
    @GET("photos/{id}/related")
    fun getRelatedPhotos(@Path("id") id: String): Call<RelatedPhotos>


}