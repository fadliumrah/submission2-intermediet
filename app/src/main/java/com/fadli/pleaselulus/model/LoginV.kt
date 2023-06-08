package com.fadli.pleaselulus.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fadli.pleaselulus.repository.LoginR
import com.fadli.pleaselulus.data.LoginStoriesResponse
import com.fadli.pleaselulus.data.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class LoginV @Inject constructor(private val loginR: LoginR) : ViewModel(){

    fun userLogin(email: String, password: String): LiveData<ResultData<LoginStoriesResponse>> =
        loginR.userLogin(email, password)
}