package com.fadli.pleaselulus.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fadli.pleaselulus.repository.MapsR
import com.fadli.pleaselulus.data.ResultData
import com.fadli.pleaselulus.data.StoriesUserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MapsV @Inject constructor(private val repository: MapsR) : ViewModel(){

    fun getStoriesLocation() : LiveData<ResultData<StoriesUserResponse>> = repository.getStoriesLocation()

}