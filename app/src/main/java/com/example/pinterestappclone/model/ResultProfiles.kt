package com.example.pinterestappclone.model

import com.google.gson.annotations.SerializedName

data class ResultProfiles(
    var total: Int? = null,
    var total_pages: Int? = null,
    var results: ArrayList<Profile>? = null
)

data class Profile(
    val id: String,
    val updated_at: String,
    val username: String,
    val name: String,
    val first_name: String,
    val last_name: String? = null,
    val twitter_username: Any? = null,
    val portfolio_url: String,
    val bio: String,
    val location: String,
    val links: LinksX,
    val profile_image: ProfileImage,
    val instagram_username: String,
    val total_collections: Long,
    val total_likes: Long,
    val total_photos: Long,
    val accepted_tos: Boolean,
    val for_hire: Boolean,
    val social: Social,
    val followed_by_user: Boolean,
    val photos: ArrayList<Photo>
)

data class Photo(
    val id: String,
    val created_at: String,
    val updated_at: String,
    val blur_hash: String,
    val urls: Urls
)

