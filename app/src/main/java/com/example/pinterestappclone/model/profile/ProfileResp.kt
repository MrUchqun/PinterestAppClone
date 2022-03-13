package com.example.pinterestappclone.model.profile

import com.example.pinterestappclone.model.Photo

data class ProfileResp(
    val accepted_tos: Boolean,
    val allow_messages: Boolean,
    val badge: Any,
    val bio: Any,
    val downloads: Int,
    val first_name: String,
    val followed_by_user: Boolean,
    val followers_count: Int,
    val following_count: Int,
    val for_hire: Boolean,
    val id: String,
    val instagram_username: Any,
    val last_name: String,
    val links: Links,
    val location: Any,
    val meta: Meta,
    val name: String,
    val numeric_id: Int,
    val photos: List<Photo>,
    val portfolio_url: Any,
    val profile_image: ProfileImage,
    val social: Social,
    val tags: Tags,
    val total_collections: Int,
    val total_likes: Int,
    val total_photos: Int,
    val twitter_username: Any,
    val updated_at: String,
    val username: String
)