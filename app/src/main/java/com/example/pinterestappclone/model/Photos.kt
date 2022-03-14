package com.example.pinterestappclone.model

class PhotoList : ArrayList<PhotoItem>()

data class PhotoItem(
    val alt_description: String? = null,
    val blur_hash: String? = null,
    val categories: List<Any>? = null,
    val color: String? = null,
    val created_at: String? = null,
    val current_user_collections: List<Any>? = null,
    val description: String? = null,
    val height: Int? = null,
    val id: String? = null,
    val liked_by_user: Boolean? = null,
    val likes: Int? = null,
    val links: Links? = null,
    val promoted_at: String? = null,
    val sponsorship: Sponsorship? = null,
    val topic_submissions: TopicSubmissions? = null,
    val updated_at: String? = null,
    val urls: Urls? = null,
    val user: User? = null,
    val width: Int? = null
)

data class Links(
    val download: String? = null,
    val download_location: String? = null,
    val html: String? = null,
    val self: String? = null
)

data class LinksX(
    val followers: String? = null,
    val following: String? = null,
    val html: String? = null,
    val likes: String? = null,
    val photos: String? = null,
    val portfolio: String? = null,
    val self: String? = null
)

data class LinksXX(
    val followers: String? = null,
    val following: String? = null,
    val html: String? = null,
    val likes: String? = null,
    val photos: String? = null,
    val portfolio: String? = null,
    val self: String? = null
)

data class ProfileImage(
    val large: String? = null,
    val medium: String? = null,
    val small: String? = null
)

data class ProfileImageX(
    val large: String? = null,
    val medium: String? = null,
    val small: String? = null
)

data class Social(
    val instagram_username: String? = null,
    val paypal_email: Any? = null,
    val portfolio_url: String? = null,
    val twitter_username: String? = null
)

data class SocialX(
    val instagram_username: String? = null,
    val paypal_email: Any? = null,
    val portfolio_url: Any? = null,
    val twitter_username: Any? = null
)

data class Sponsor(
    val accepted_tos: Boolean? = null,
    val bio: String? = null,
    val first_name: String? = null,
    val for_hire: Boolean? = null,
    val id: String? = null,
    val instagram_username: String? = null,
    val last_name: Any? = null,
    val links: LinksX? = null,
    val location: Any? = null,
    val name: String? = null,
    val portfolio_url: String? = null,
    val profile_image: ProfileImage? = null,
    val social: Social? = null,
    val total_collections: Int? = null,
    val total_likes: Int? = null,
    val total_photos: Int? = null,
    val twitter_username: String? = null,
    val updated_at: String? = null,
    val username: String? = null
)

data class Sponsorship(
    val impression_urls: List<String>? = null,
    val sponsor: Sponsor? = null,
    val tagline: String? = null,
    val tagline_url: String? = null
)

data class TopicSubmissions(
    val architecture: Architecture? = null,
    val wallpapers: Wallpapers? = null
)

data class Urls(
    val full: String? = null,
    val raw: String? = null,
    val regular: String? = null,
    val small: String? = null,
    val small_s3: String? = null,
    val thumb: String? = null
)

data class User(
    val accepted_tos: Boolean? = null,
    val bio: String? = null,
    val first_name: String? = null,
    val for_hire: Boolean? = null,
    val id: String? = null,
    val instagram_username: String? = null,
    val last_name: Any? = null,
    val links: LinksXX? = null,
    val location: String? = null,
    val name: String? = null,
    val portfolio_url: Any? = null,
    val profile_image: ProfileImageX? = null,
    val social: SocialX? = null,
    val total_collections: Int? = null,
    val total_likes: Int? = null,
    val total_photos: Int? = null,
    val twitter_username: Any? = null,
    val updated_at: String? = null,
    val username: String? = null
)

data class Architecture(
    val status: String? = null
)

data class Wallpapers(
    val status: String? = null
)
