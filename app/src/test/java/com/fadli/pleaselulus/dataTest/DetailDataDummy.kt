package com.fadli.pleaselulus.dataTest

import com.fadli.pleaselulus.data.DetailUserResponse
import com.fadli.pleaselulus.data.DataStory


fun successDetailTest(): DetailUserResponse {
    val dataStory = DataStory(
        id = "id",
        name = "name",
        description = "description",
        photoUrl = "image.jpg",
        createdAt = "created"
    )
    return DetailUserResponse(
        error = false,
        message = "Story fetched successfully",
        dataStory = dataStory
    )
}

fun failedDetailTest(): DetailUserResponse {
    return DetailUserResponse(
        error = true,
        message = "Story not found"
    )
}