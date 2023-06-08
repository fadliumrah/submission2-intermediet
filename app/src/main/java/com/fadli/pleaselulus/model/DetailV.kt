package com.fadli.pleaselulus.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fadli.pleaselulus.repository.DetailStoriesR
import com.fadli.pleaselulus.data.DetailUserResponse
import com.fadli.pleaselulus.data.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailV @Inject constructor(private val repository: DetailStoriesR) : ViewModel(){

    fun getDetailStories(id: String) : LiveData<ResultData<DetailUserResponse>> = repository.getDetailStories(id)

}