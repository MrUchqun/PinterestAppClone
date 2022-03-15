package com.example.pinterestappclone.network.service

import com.example.pinterestappclone.model.*
import com.example.pinterestappclone.model.profile.ProfileResp
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

    @Headers("Authorization:$client_id $ACCESS_KEY")
    @GET("search/photos")
    fun getSearchPhoto(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("per_page") perPage: Int
    ): Call<ResultPhotos>

    @Headers("Authorization:$client_id $ACCESS_KEY")
    @GET("search/users")
    fun getSearchProfile(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("per_page") perPage: Int
    ): Call<ResultProfiles>

    @Headers("Authorization:$client_id $ACCESS_KEY")
    @GET("photos/random")
    fun getRandomPhotos(
        @Query("query") query: String,
        @Query("orientation") orientation: String,
        @Query("count") count: Int
    ): Call<PhotoList>

    @Headers("Authorization:$client_id $ACCESS_KEY")
    @GET("topics")
    fun getTopics(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<ArrayList<Topic>>

    @Headers("Authorization:$client_id $ACCESS_KEY")
    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Call<ProfileResp>

}