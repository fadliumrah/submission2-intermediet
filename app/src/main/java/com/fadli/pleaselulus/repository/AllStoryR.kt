package com.fadli.pleaselulus.repository

import android.content.Context
import android.preference.PreferenceManager
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.fadli.pleaselulus.util.SourceUserStories
import com.fadli.pleaselulus.constanta.TOKEN
import com.fadli.pleaselulus.api.ApiService
import com.fadli.pleaselulus.data.DataStory
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AllStoryR @Inject
constructor(private val apiService: ApiService, @ApplicationContext private val context: Context){

    private val token = "Bearer ${PreferenceManager.getDefaultSharedPreferences(context).getString(
        TOKEN, "")}"

    fun showAllStories() : LiveData<PagingData<DataStory>>{
        return Pager(
            config = PagingConfig(
                pageSize = 6
            ),
            pagingSourceFactory = {
                SourceUserStories(apiService, token)
        }
        ).liveData
    }
}