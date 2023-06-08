package com.fadli.pleaselulus.api

import com.fadli.pleaselulus.data.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("register")
    fun userRegister(
        @Body dataUser: DataUser
    ): Call<RegisterUserResponse>

    @Headers("Content-Type: application/json", "X-Requested-With: XMLHttpRequest")
    @POST("login")
    fun userLogin(
        @Body dataUser: DataUser
    ): Call<LoginStoriesResponse>

    @GET("stories")
    suspend fun getAllStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): StoriesUserResponse

    @GET("stories/{id}")
    fun getDetailStories(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<DetailUserResponse>

    @Multipart
    @POST("stories")
    fun uploadStories(
        @Header("Authorization") token: String,
        @Part("description") description: RequestBody,
        @Part file: MultipartBody.Part
    ): Call<UploadStoriesResponse>

    @GET("stories")
    fun getLocationStories(
        @Header("Authorization") token: String,
        @Query("location") location : Int
    ): Call<StoriesUserResponse>
}