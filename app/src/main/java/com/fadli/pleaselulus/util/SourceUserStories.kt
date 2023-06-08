package com.fadli.pleaselulus.util

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fadli.pleaselulus.api.ApiService
import com.fadli.pleaselulus.data.DataStory

class SourceUserStories (private val apiService: ApiService, private val token: String): PagingSource<Int, DataStory>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataStory> {
        return try{
            val page = params.key ?: PAGE_INDEX
            val responseData = apiService.getAllStories(token, page, params.loadSize)
            val prevkey = if (page == PAGE_INDEX) null else page -1
            val nextkey = if (responseData.listDataStory.isNullOrEmpty()) null else page + 1

            LoadResult.Page(
                data = responseData.listDataStory as List,
                prevKey = prevkey,
                nextKey = nextkey
            )
        }
        catch (e: Exception){
            e.printStackTrace()
            Log.e(SourceUserStories::class.java.simpleName, "Failure get Data ${e.message}")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DataStory>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private companion object{
        const val PAGE_INDEX = 1
    }
}