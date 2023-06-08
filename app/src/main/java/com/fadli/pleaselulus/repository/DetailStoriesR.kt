package com.fadli.pleaselulus.repository

import android.content.Context
import android.preference.PreferenceManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fadli.pleaselulus.constanta.TOKEN
import com.fadli.pleaselulus.api.ApiService
import com.fadli.pleaselulus.util.convertErrorData
import com.fadli.pleaselulus.data.DetailUserResponse
import com.fadli.pleaselulus.data.ResultData
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class DetailStoriesR @Inject constructor(private val apiService: ApiService, @ApplicationContext private val context: Context){

    @Suppress("DEPRECATION")
    private val token = "Bearer ${PreferenceManager.getDefaultSharedPreferences(context).getString(
        TOKEN, "")}"

    fun getDetailStories(id: String) : LiveData<ResultData<DetailUserResponse>>{
        val detailData: MutableLiveData<ResultData<DetailUserResponse>> = MutableLiveData()

        apiService.getDetailStories(token, id).enqueue(object : Callback<DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful){
                    detailData.postValue(ResultData.Success(response.body() as DetailUserResponse))
                }
                else{
                    val errorData = response.errorBody()?.string()?.let { convertErrorData(it) }
                    detailData.postValue(ResultData.Error(errorData?.message ?: "Failure get Data", response.code(), null))
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                detailData.postValue(ResultData.Error("Failure Convert Data", null, null))
            }
        })
        return detailData
    }

}