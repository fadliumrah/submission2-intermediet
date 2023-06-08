package com.fadli.pleaselulus.data

import com.google.gson.annotations.SerializedName

data class LoginStoriesResponse(

    @field:SerializedName("loginResult")
	val loginUserResult: LoginUserResult? = null,

    @field:SerializedName("error")
	val error: Boolean,

    @field:SerializedName("message")
	val message: String
)

