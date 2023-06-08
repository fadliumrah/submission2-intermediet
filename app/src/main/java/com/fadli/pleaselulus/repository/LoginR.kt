package com.fadli.pleaselulus.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fadli.pleaselulus.api.ApiService
import com.fadli.pleaselulus.util.convertErrorData
import com.fadli.pleaselulus.data.LoginStoriesResponse
import com.fadli.pleaselulus.data.ResultData
import com.fadli.pleaselulus.data.DataUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginR @Inject constructor(private val apiService: ApiService){

    fun userLogin(email: String, password: String) : LiveData<ResultData<LoginStoriesResponse>>{
        val loginData: MutableLiveData<ResultData<LoginStoriesResponse>> = MutableLiveData()
        loginData.postValue(ResultData.Loading())

        val dataUserLoginData = DataUser(email = email, password = password)

        apiService.userLogin(dataUserLoginData).enqueue(object : Callback<LoginStoriesResponse>{
            override fun onResponse(
                call: Call<LoginStoriesResponse>,
                response: Response<LoginStoriesResponse>
            ) {
                if (response.isSuccessful){
                    loginData.postValue(ResultData.Success(response.body() as LoginStoriesResponse))
                }
                else{
                    val errorData = response.errorBody()?.string()?.let { convertErrorData(it) }
                    loginData.postValue(ResultData.Error(errorData?.message, response.code(), null))
                }
            }

            override fun onFailure(call: Call<LoginStoriesResponse>, t: Throwable) {
                loginData.postValue(ResultData.Error(t.message.toString(), null, null))
            }

        })
        return loginData
    }

}
