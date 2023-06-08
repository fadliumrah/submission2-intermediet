package com.fadli.pleaselulus.dataTest


import com.fadli.pleaselulus.data.StoriesUserResponse
import com.fadli.pleaselulus.data.DataStory
import java.util.*

fun successLocationTest(): StoriesUserResponse {
    val stories = LinkedList<DataStory>()

    for (i in 1..10){
        val dataStory = DataStory(
            id = "id++$i",
            name = "name++$i",
            description = "description++$i",
            photoUrl = "photo++$i.jpg",
            createdAt = "created++$i",
            lat = -6.97583 + i,
            lon = 108.48306 + i
        )
        stories.add(dataStory)
    }
    return StoriesUserResponse(
        error = false,
        message = "Stories fetched successfully",
        listDataStory = stories
    )
}

fun failedLocationTest(): StoriesUserResponse{
    return StoriesUserResponse(
        error = true,
        message = "Missing authentication",
        listDataStory = null
    )
}
