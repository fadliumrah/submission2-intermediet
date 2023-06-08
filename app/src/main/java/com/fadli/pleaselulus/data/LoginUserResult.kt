package com.fadli.pleaselulus.data

import com.google.gson.annotations.SerializedName

data class LoginUserResult(
    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("token")
    val token: String
)