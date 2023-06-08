package com.fadli.pleaselulus.data

import com.google.gson.annotations.SerializedName

data class StoriesUserResponse(

	@field:SerializedName("listStory")
	val listDataStory: List<DataStory>?,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
