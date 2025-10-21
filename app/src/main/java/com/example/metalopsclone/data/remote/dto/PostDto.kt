package com.example.metalopsclone.data.remote.dto

data class PostDto(
    val id: Int? = null,
    val title: String,
    val body: String,
    val userId: Int = 1
)
