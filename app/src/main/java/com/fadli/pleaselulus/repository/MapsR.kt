package com.fadli.pleaselulus.repository

import android.content.Context
import android.preference.PreferenceManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fadli.pleaselulus.constanta.TOKEN
import com.fadli.pleaselulus.api.ApiService
import com.fadli.pleaselulus.util.convertErrorData
import com.fadli.pleaselulus.data.ResultData
import com.fadli.pleaselulus.data.StoriesUserResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class MapsR @Inject constructor(private val apiService: ApiService, @ApplicationContext private val context: Context) {

    private val token = "Bearer ${
        PreferenceManager.getDefaultSharedPreferences(context).getString(
            TOKEN, "")}"

    fun getStoriesLocation() : LiveData<ResultData<StoriesUserResponse>>{
        val locationData : MutableLiveData<ResultData<StoriesUserResponse>> = MutableLiveData()
        locationData.postValue(ResultData.Loading())

        apiService.getLocationStories(token, 1).enqueue(object : Callback<StoriesUserResponse>{
            override fun onResponse(
                call: Call<StoriesUserResponse>,
                response: Response<StoriesUserResponse>
            ) {
                if (response.isSuccessful){
                    locationData.postValue(ResultData.Success(response.body() as StoriesUserResponse))
                }
                else{
                    val errorData = response.errorBody()?.string()?.let { convertErrorData(it) }
                    locationData.postValue(ResultData.Error(errorData?.message ?: "Failure Get Data", response.code(), null))
                }
            }

            override fun onFailure(call: Call<StoriesUserResponse>, t: Throwable) {
                locationData.postValue(ResultData.Error(t.message.toString(), null, null))
            }
        })
        return locationData
    }
}