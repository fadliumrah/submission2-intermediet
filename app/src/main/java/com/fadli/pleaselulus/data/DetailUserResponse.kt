package com.fadli.pleaselulus.data

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(

	@field:SerializedName("story")
	val dataStory: DataStory? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String

)

