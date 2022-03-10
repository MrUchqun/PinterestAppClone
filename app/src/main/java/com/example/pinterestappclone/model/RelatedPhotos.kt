package com.example.pinterestappclone.model

import com.google.gson.annotations.SerializedName

data class RelatedPhotos(
    var total: Int? = null,
    var results: ArrayList<PhotoItem>? = null
)