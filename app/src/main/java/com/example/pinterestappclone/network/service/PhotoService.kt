package com.example.pinterestappclone.network.service

import com.example.pinterestappclone.model.PhotoList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotoService {

    companion object {
        private const val ACCESS_KEY = "ulB8B9XZs0HXeqBw-jFqKWvtZFLMgkBl5g1IZpvhR4o"
        const val client_id = "Client-ID"
    }

    @Headers("Authorization:$client_id $ACCESS_KEY")

    @GET("photos")
    fun getPhotos(@Query("page") page: Int, @Query("per_page") perPage: Int): Call<PhotoList>


}