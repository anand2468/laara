package com.example.laara.screens.homepage

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST


data class post(
    val pid:Int,
    val rollno:String,
    val name:String,
    val message:String
)

private const val BASE_URL = "http://192.168.198.147:8080"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface postService {
    @POST("get-posts")
    suspend fun getPosts():List<post>
}

object postServiceApi{
    val fetchPhotos: postService by lazy{
        retrofit.create(postService::class.java)
    }
}