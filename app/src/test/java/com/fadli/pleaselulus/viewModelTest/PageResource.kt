package com.fadli.pleaselulus.viewModelTest

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fadli.pleaselulus.data.DataStory

class PageResource: PagingSource<Int, LiveData<List<DataStory>>>(){
    companion object{
        fun snapshot(item: List<DataStory>): PagingData<DataStory> {
            return PagingData.from(item)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<DataStory>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<DataStory>>>): Int {
        return 0
    }
}