package com.example.metalopsclone.data.remote

import com.example.metalopsclone.data.remote.dto.PostDto
import retrofit2.Response
import retrofit2.http.*

interface OtApi {
    @GET("posts")
    suspend fun list(): Response<List<PostDto>>

    @GET("posts/{id}")
    suspend fun get(@Path("id") id: Int): Response<PostDto>

    @POST("posts")
    suspend fun create(@Body post: PostDto): Response<PostDto>

    @PUT("posts/{id}")
    suspend fun update(@Path("id") id: Int, @Body post: PostDto): Response<PostDto>

    @DELETE("posts/{id}")
    suspend fun delete(@Path("id") id: Int): Response<Unit>
}
