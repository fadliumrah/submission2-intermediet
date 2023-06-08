package com.fadli.pleaselulus.dataTest
import com.fadli.pleaselulus.data.DataStory

fun successAllStoriesTest(): List<DataStory>{
    val listDataStory: MutableList<DataStory> = arrayListOf()
    for (i in 1..5){
        val dataStory = DataStory(
            id = "id++$i",
            name = "name++$i",
            description = "description+=$i",
            photoUrl = "photo++$i.jpg",
            createdAt = "created++$i"
        )
        listDataStory.add(dataStory)
    }
    return listDataStory
}