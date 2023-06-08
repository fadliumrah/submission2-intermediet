package com.fadli.pleaselulus.model

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fadli.pleaselulus.repository.AllStoryR
import com.fadli.pleaselulus.data.DataStory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
@Suppress("DEPRECATION")
class StoriesV @Inject constructor(private val repository: AllStoryR) : ViewModel(){

    fun showAllStories(): LiveData<PagingData<DataStory>> = repository.showAllStories().cachedIn(viewModelScope)

}


