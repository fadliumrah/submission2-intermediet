package com.fadli.pleaselulus.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fadli.pleaselulus.api.ApiService
import com.fadli.pleaselulus.util.convertErrorData
import com.fadli.pleaselulus.data.RegisterUserResponse
import com.fadli.pleaselulus.data.ResultData
import com.fadli.pleaselulus.data.DataUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RegisterR @Inject constructor(private val apiService: ApiService){


    fun userRegister(name: String, email: String, password: String) : LiveData<ResultData<RegisterUserResponse>>{

        val registData: MutableLiveData<ResultData<RegisterUserResponse>> = MutableLiveData()
        registData.postValue(ResultData.Loading())
        val dataUserRegistData = DataUser(name = name, email = email, password = password)

        apiService.userRegister(dataUserRegistData).enqueue(object : Callback<RegisterUserResponse>{
            override fun onResponse(
                call: Call<RegisterUserResponse>,
                response: Response<RegisterUserResponse>
            ) {
                if (response.isSuccessful){
                    registData.postValue(ResultData.Success(response.body() as RegisterUserResponse))
                }
                else{
                    val errorData = response.errorBody()?.let { convertErrorData(it.toString()) }
                    registData.postValue(ResultData.Error(errorData?.message, response.code(), null))
                }
            }

            override fun onFailure(call: Call<RegisterUserResponse>, t: Throwable) {
                registData.postValue(ResultData.Error(t.message.toString(), null, null))
            }
        })
        return registData
    }
}