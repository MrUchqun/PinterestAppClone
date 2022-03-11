package com.example.pinterestappclone.network.service

import com.example.pinterestappclone.model.PhotoItem
import com.example.pinterestappclone.model.PhotoList
import com.example.pinterestappclone.model.RelatedPhotos
import com.example.pinterestappclone.model.ResultPhotos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotoService {

    companion object {
        private const val ACCESS_KEY = "MIW2KFmCuEMWQUgnJtM1dwQCsg3kioPEhzvY8R6aIPw"
        private const val ACCESS_KEY_2 = "ulB8B9XZs0HXeqBw-jFqKWvtZFLMgkBl5g1IZpvhR4o"
        private const val ACCESS_KEY_3 = "mjMOfuO1VyckFN0RxYfKeJzFiyOSDQuOtf3UJnDgJvY"

        const val client_id = "Client-ID"
    }

    @Headers("Authorization:$client_id $ACCESS_KEY_3")
    @GET("photos")
    fun getPhotos(@Query("page") page: Int, @Query("per_page") perPage: Int): Call<PhotoList>

    @Headers("Authorization:$client_id $ACCESS_KEY_3")
    @GET("photos/{id}/related")
    fun getRelatedPhotos(@Path("id") id: String): Call<RelatedPhotos>

    @Headers("Authorization:$client_id $ACCESS_KEY_3")
    @GET("search/photos")
    fun getSearchPhoto(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("per_page") perPage: Int
    ): Call<ResultPhotos>

    @Headers("Authorization:$client_id $ACCESS_KEY_3")
    @GET("photos/random")
    fun getRandomPhotos(
        @Query("query") query: String,
        @Query("orientation") orientation: String,
        @Query("count") count: Int
    ): Call<PhotoList>


}