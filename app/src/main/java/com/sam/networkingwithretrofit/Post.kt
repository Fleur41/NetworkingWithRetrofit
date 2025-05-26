package com.sam.networkingwithretrofit

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Post (
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int
)