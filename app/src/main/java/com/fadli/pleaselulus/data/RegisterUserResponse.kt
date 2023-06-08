package com.fadli.pleaselulus.data

import com.google.gson.annotations.SerializedName

data class RegisterUserResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

