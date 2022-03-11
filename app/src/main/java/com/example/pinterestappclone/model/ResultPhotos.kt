package com.example.pinterestappclone.model

data class ResultPhotos(
    var total: Int? = null,
    var total_pages: Int? = null,
    var results: ArrayList<PhotoItem>? = null
)