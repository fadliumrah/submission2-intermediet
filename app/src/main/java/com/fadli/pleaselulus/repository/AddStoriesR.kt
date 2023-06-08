package com.fadli.pleaselulus.repository

import android.content.Context
import android.preference.PreferenceManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fadli.pleaselulus.constanta.TOKEN
import com.fadli.pleaselulus.api.ApiService
import com.fadli.pleaselulus.util.convertErrorData
import com.fadli.pleaselulus.util.createPartFromString
import com.fadli.pleaselulus.data.ResultData
import com.fadli.pleaselulus.data.UploadStoriesResponse
import com.fadli.pleaselulus.util.prepareFilePart
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class AddStoriesR @Inject constructor(private val apiService: ApiService, @ApplicationContext private val context: Context){

    private val token = "Bearer ${
        PreferenceManager.getDefaultSharedPreferences(context).getString(
            TOKEN, "")}"

    fun addStory(desc: String, file: File) : LiveData<ResultData<UploadStoriesResponse>>{
        val addData: MutableLiveData<ResultData<UploadStoriesResponse>> = MutableLiveData()

        val description = createPartFromString(desc)
        val storiesFile = prepareFilePart("photo", file)
        apiService.uploadStories(token, description, storiesFile).enqueue(object : Callback<UploadStoriesResponse>{
            override fun onResponse(
                call: Call<UploadStoriesResponse>,
                response: Response<UploadStoriesResponse>
            ) {
                if (response.isSuccessful){
                   addData.postValue(ResultData.Success(response.body() as UploadStoriesResponse))
                }
                else{
                    val errorData = response.errorBody()?.string()?.let { convertErrorData(it) }
                    addData.postValue(ResultData.Error(errorData?.message ?: "Failure Get Data", response.code(), null))
                }
            }

            override fun onFailure(call: Call<UploadStoriesResponse>, t: Throwable) {
                addData.postValue(ResultData.Error(t.message.toString(), null, null))
            }
        })
        return addData
    }

}