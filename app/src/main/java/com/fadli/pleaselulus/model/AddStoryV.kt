
package com.fadli.pleaselulus.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fadli.pleaselulus.repository.AddStoriesR
import com.fadli.pleaselulus.data.ResultData
import com.fadli.pleaselulus.data.UploadStoriesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
@Suppress("DEPRECATION")
class AddStoryV @Inject constructor(private val repository: AddStoriesR) : ViewModel(){

    fun addStory(desc: String, file: File): LiveData<ResultData<UploadStoriesResponse>> = repository.addStory(desc, file)

}