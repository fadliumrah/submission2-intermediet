package com.fadli.pleaselulus.data

import com.google.gson.annotations.SerializedName

data class DataStory(

    @field:SerializedName("photoUrl")
    val photoUrl: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("lon")
    val lon: Double? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("lat")
    val lat: Double? = null,

    )